package com.jspgou.plug.weixin.action.front;

import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.plug.weixin.entity.Weixin;
import com.jspgou.plug.weixin.entity.WeixinMessage;
import com.jspgou.plug.weixin.manager.WeixinMessageMng;
import com.jspgou.plug.weixin.manager.WeixinMng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageAct {

    @Autowired
    private WeixinMessageMng weixinMessageMng;

    @Autowired
    private WeixinMng weixinMng;

    @RequestMapping({"/sendMessage.jspx"})
    public void weixin(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IOException {
        Website site = SiteUtils.getWeb(request);
        String token = this.weixinMng.find(site.getId()).getToken();
        Object[] tmpArr = {token, timestamp, nonce};
        Arrays.sort(tmpArr);
        String str = tmpArr[0].toString() + tmpArr[1].toString() + tmpArr[2].toString();
        String tmpStr = DigestUtils.shaHex(str);
        if (tmpStr.equals(signature)) {
            processRequest(echostr, request, response);
        } else System.out.println("fail");
    }

    private String processRequest(String echostr, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String postStr = readStreamParameter(request.getInputStream());
        Document document = null;
        try {
            if ((postStr != null) && (!postStr.trim().equals("")))
                document = DocumentHelper.parseText(postStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (document == null) {
            response.getWriter().write(echostr);
            return null;
        }
        Website site = SiteUtils.getWeb(request);
        Element root = document.getRootElement();
        String fromUsername = root.elementText("FromUserName");
        String toUsername = root.elementText("ToUserName");
        String userMsgType = root.elementText("MsgType");

        String keyword = root.elementTextTrim("Content");
        String time = System.currentTimeMillis() + "";

        String respContent = "no body";
        String welcome = this.weixinMng.find(SiteUtils.getWebId(request)).getWelcome();
        if (userMsgType.equals("event")) {
            String eventType = root.elementText("Event");

            if (eventType.equals("subscribe")) {
                respContent = welcome;
                respContent = text(respContent, fromUsername, toUsername, time);
                send(respContent, response);
                return null;
            }

            if (eventType.equals("unsubscribe")) {
                return null;
            }

            String eventKey = root.elementText("EventKey");

            autoReply(eventKey, fromUsername, toUsername, time, site, request, response);
            return null;
        }

        if (keyword != null) {
            keyword = keyword.trim();
        }
        if ((keyword != null) && (userMsgType.equals("text"))) {
            autoReply(keyword, fromUsername, toUsername, time, site, request, response);
        }
        return null;
    }

    private void autoReply(String keyword, String fromUsername, String toUsername, String time, Website site, HttpServletRequest request, HttpServletResponse response) throws IOException {
        WeixinMessage entity = this.weixinMessageMng.findByNumber(keyword, site.getId());
        if (entity != null) {
            String text = contentWithImgUseMessage(entity, fromUsername, toUsername, time, request);
            send(text, response);
        } else {
            entity = this.weixinMessageMng.getWelcome(site.getId());
            if (entity != null) {
                StringBuffer buffer = new StringBuffer();
                String textTpl = "";

                if (entity.getType().equals(Integer.valueOf(1))) {
                    buffer.append(entity.getContent()).append("\n");
                    List lists = this.weixinMessageMng.getList(site.getId());
                    for (int i = 0; i < lists.size(); i++) {
                        buffer.append("  【" + ((WeixinMessage) lists.get(i)).getNumber() + "】" + ((WeixinMessage) lists.get(i)).getTitle()).append("\n");
                    }
                    textTpl = text(buffer.toString(), fromUsername, toUsername, time);
                } else if (entity.getType().equals(Integer.valueOf(2))) {
                    buffer.append(entity.getContent()).append("\n");
                    textTpl = text(buffer.toString(), fromUsername, toUsername, time);
                } else if (entity.getType().equals(Integer.valueOf(0))) {
                    textTpl = contentWithImgUseMessage(entity, fromUsername, toUsername, time, request);
                }
                send(textTpl, response);
            }
        }
    }

    private String readStreamParameter(ServletInputStream in) {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null)
                buffer.append(line);
        } catch (Exception e) {
            e.printStackTrace();

            if (reader != null)
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }

    private String contentWithImgUseMessage(WeixinMessage entity, String fromUsername, String toUsername, String time, HttpServletRequest request) {
        Website site = SiteUtils.getWeb(request);
        String path = site.getDomain();
        String textTpls = text(fromUsername, toUsername, time, entity.getTitle(), entity.getContent(), "http://" + path + entity.getPath(), entity.getUrl());
        return textTpls;
    }

    private String text(String fromUsername, String toUsername, String time, String title, String desc, String img, String url) {
        String textTpls = "<xml><ToUserName><![CDATA[" +
                fromUsername + "]]></ToUserName>" +
                "<FromUserName><![CDATA[" + toUsername + "]]></FromUserName>" +
                "<CreateTime>" + time + "</CreateTime>" +
                "<MsgType><![CDATA[news]]></MsgType>" +
                "<ArticleCount>1</ArticleCount>" +
                "<Articles>" +
                "<item>" +
                "<Title><![CDATA[" + title + "]]></Title>" +
                "<Description><![CDATA[" + desc + "]]></Description>" +
                "<PicUrl><![CDATA[" + img + "]]></PicUrl>" +
                "<Url><![CDATA[" + url + "]]></Url>" +
                "</item>" +
                "</Articles>" +
                "</xml>";
        return textTpls;
    }

    private String text(String str, String fromUsername, String toUsername, String time) {
        String textTpls = "<xml><ToUserName><![CDATA[" +
                fromUsername + "]]></ToUserName>" +
                "<FromUserName><![CDATA[" + toUsername + "]]></FromUserName>" +
                "<CreateTime>" + time + "</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA[" + str + "]]></Content>" +
                "</xml>";

        return textTpls;
    }

    private void send(String textTpl, HttpServletResponse response) throws IOException {
        String type = "text/xml;charset=UTF-8";
        response.setContentType(type);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.getWriter().write(textTpl);
    }
}

