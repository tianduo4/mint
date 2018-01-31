/*     */ package com.jspgou.plug.weixin.action.front;
/*     */ 
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.plug.weixin.entity.Weixin;
/*     */ import com.jspgou.plug.weixin.entity.WeixinMessage;
/*     */ import com.jspgou.plug.weixin.manager.WeixinMessageMng;
/*     */ import com.jspgou.plug.weixin.manager.WeixinMng;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletInputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class MessageAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WeixinMessageMng weixinMessageMng;
/*     */ 
/*     */   @Autowired
/*     */   private WeixinMng weixinMng;
/*     */ 
/*     */   @RequestMapping({"/sendMessage.jspx"})
/*     */   public void weixin(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws IOException
/*     */   {
/*  48 */     Website site = SiteUtils.getWeb(request);
/*  49 */     String token = this.weixinMng.find(site.getId()).getToken();
/*  50 */     Object[] tmpArr = { token, timestamp, nonce };
/*  51 */     Arrays.sort(tmpArr);
/*  52 */     String str = tmpArr[0].toString() + tmpArr[1].toString() + tmpArr[2].toString();
/*  53 */     String tmpStr = DigestUtils.shaHex(str);
/*  54 */     if (tmpStr.equals(signature))
/*     */     {
/*  56 */       processRequest(echostr, request, response);
/*     */     }
/*  58 */     else System.out.println("fail");
/*     */   }
/*     */ 
/*     */   private String processRequest(String echostr, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException
/*     */   {
/*  65 */     request.setCharacterEncoding("UTF-8");
/*  66 */     String postStr = readStreamParameter(request.getInputStream());
/*  67 */     Document document = null;
/*     */     try {
/*  69 */       if ((postStr != null) && (!postStr.trim().equals("")))
/*  70 */         document = DocumentHelper.parseText(postStr);
/*     */     }
/*     */     catch (Exception e) {
/*  73 */       e.printStackTrace();
/*     */     }
/*  75 */     if (document == null) {
/*  76 */       response.getWriter().write(echostr);
/*  77 */       return null;
/*     */     }
/*  79 */     Website site = SiteUtils.getWeb(request);
/*  80 */     Element root = document.getRootElement();
/*  81 */     String fromUsername = root.elementText("FromUserName");
/*  82 */     String toUsername = root.elementText("ToUserName");
/*  83 */     String userMsgType = root.elementText("MsgType");
/*     */ 
/*  85 */     String keyword = root.elementTextTrim("Content");
/*  86 */     String time =System.currentTimeMillis()+"";
/*     */ 
/*  89 */     String respContent = "no body";
/*  90 */     String welcome = this.weixinMng.find(SiteUtils.getWebId(request)).getWelcome();
/*  91 */     if (userMsgType.equals("event"))
/*     */     {
/*  93 */       String eventType = root.elementText("Event");
/*     */ 
/*  95 */       if (eventType.equals("subscribe")) {
/*  96 */         respContent = welcome;
/*  97 */         respContent = text(respContent, fromUsername, toUsername, time);
/*  98 */         send(respContent, response);
/*  99 */         return null;
/*     */       }
/*     */ 
/* 102 */       if (eventType.equals("unsubscribe"))
/*     */       {
/* 104 */         return null;
/*     */       }
/*     */ 
/* 108 */       String eventKey = root.elementText("EventKey");
/*     */ 
/* 110 */       autoReply(eventKey, fromUsername, toUsername, time, site, request, response);
/* 111 */       return null;
/*     */     }
/*     */ 
/* 114 */     if (keyword != null) {
/* 115 */       keyword = keyword.trim();
/*     */     }
/* 117 */     if ((keyword != null) && (userMsgType.equals("text"))) {
/* 118 */       autoReply(keyword, fromUsername, toUsername, time, site, request, response);
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */   private void autoReply(String keyword, String fromUsername, String toUsername, String time, Website site, HttpServletRequest request, HttpServletResponse response) throws IOException {
/* 124 */     WeixinMessage entity = this.weixinMessageMng.findByNumber(keyword, site.getId());
/* 125 */     if (entity != null) {
/* 126 */       String text = contentWithImgUseMessage(entity, fromUsername, toUsername, time, request);
/* 127 */       send(text, response);
/*     */     } else {
/* 129 */       entity = this.weixinMessageMng.getWelcome(site.getId());
/* 130 */       if (entity != null) {
/* 131 */         StringBuffer buffer = new StringBuffer();
/* 132 */         String textTpl = "";
/*     */ 
/* 134 */         if (entity.getType().equals(Integer.valueOf(1))) {
/* 135 */           buffer.append(entity.getContent()).append("\n");
/* 136 */           List lists = this.weixinMessageMng.getList(site.getId());
/* 137 */           for (int i = 0; i < lists.size(); i++) {
/* 138 */             buffer.append("  【" + ((WeixinMessage)lists.get(i)).getNumber() + "】" + ((WeixinMessage)lists.get(i)).getTitle()).append("\n");
/*     */           }
/* 140 */           textTpl = text(buffer.toString(), fromUsername, toUsername, time);
/* 141 */         } else if (entity.getType().equals(Integer.valueOf(2)))
/*     */         {
/* 143 */           buffer.append(entity.getContent()).append("\n");
/* 144 */           textTpl = text(buffer.toString(), fromUsername, toUsername, time);
/* 145 */         } else if (entity.getType().equals(Integer.valueOf(0)))
/*     */         {
/* 147 */           textTpl = contentWithImgUseMessage(entity, fromUsername, toUsername, time, request);
/*     */         }
/* 149 */         send(textTpl, response);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String readStreamParameter(ServletInputStream in)
/*     */   {
/* 157 */     StringBuilder buffer = new StringBuilder();
/* 158 */     BufferedReader reader = null;
/*     */     try {
/* 160 */       reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
/* 161 */       String line = null;
/* 162 */       while ((line = reader.readLine()) != null)
/* 163 */         buffer.append(line);
/*     */     }
/*     */     catch (Exception e) {
/* 166 */       e.printStackTrace();
/*     */ 
/* 168 */       if (reader != null)
/*     */         try {
/* 170 */           reader.close();
/*     */         } catch (IOException e) {
/* 172 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */     finally
/*     */     {
/* 168 */       if (reader != null) {
/*     */         try {
/* 170 */           reader.close();
/*     */         } catch (IOException e) {
/* 172 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 176 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   private String contentWithImgUseMessage(WeixinMessage entity, String fromUsername, String toUsername, String time, HttpServletRequest request) {
/* 180 */     Website site = SiteUtils.getWeb(request);
/* 181 */     String path = site.getDomain();
/* 182 */     String textTpls = text(fromUsername, toUsername, time, entity.getTitle(), entity.getContent(), "http://" + path + entity.getPath(), entity.getUrl());
/* 183 */     return textTpls;
/*     */   }
/*     */ 
/*     */   private String text(String fromUsername, String toUsername, String time, String title, String desc, String img, String url)
/*     */   {
/* 188 */     String textTpls = "<xml><ToUserName><![CDATA[" + 
/* 189 */       fromUsername + "]]></ToUserName>" + 
/* 190 */       "<FromUserName><![CDATA[" + toUsername + "]]></FromUserName>" + 
/* 191 */       "<CreateTime>" + time + "</CreateTime>" + 
/* 192 */       "<MsgType><![CDATA[news]]></MsgType>" + 
/* 193 */       "<ArticleCount>1</ArticleCount>" + 
/* 194 */       "<Articles>" + 
/* 195 */       "<item>" + 
/* 196 */       "<Title><![CDATA[" + title + "]]></Title>" + 
/* 197 */       "<Description><![CDATA[" + desc + "]]></Description>" + 
/* 198 */       "<PicUrl><![CDATA[" + img + "]]></PicUrl>" + 
/* 199 */       "<Url><![CDATA[" + url + "]]></Url>" + 
/* 200 */       "</item>" + 
/* 201 */       "</Articles>" + 
/* 202 */       "</xml>";
/* 203 */     return textTpls;
/*     */   }
/*     */ 
/*     */   private String text(String str, String fromUsername, String toUsername, String time) {
/* 207 */     String textTpls = "<xml><ToUserName><![CDATA[" + 
/* 208 */       fromUsername + "]]></ToUserName>" + 
/* 209 */       "<FromUserName><![CDATA[" + toUsername + "]]></FromUserName>" + 
/* 210 */       "<CreateTime>" + time + "</CreateTime>" + 
/* 211 */       "<MsgType><![CDATA[text]]></MsgType>" + 
/* 212 */       "<Content><![CDATA[" + str + "]]></Content>" + 
/* 213 */       "</xml>";
/*     */ 
/* 215 */     return textTpls;
/*     */   }
/*     */ 
/*     */   private void send(String textTpl, HttpServletResponse response) throws IOException {
/* 219 */     String type = "text/xml;charset=UTF-8";
/* 220 */     response.setContentType(type);
/* 221 */     response.setHeader("Pragma", "No-cache");
/* 222 */     response.setHeader("Cache-Control", "no-cache");
/* 223 */     response.setDateHeader("Expires", 0L);
/* 224 */     response.getWriter().write(textTpl);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.action.front.MessageAct
 * JD-Core Version:    0.6.0
 */