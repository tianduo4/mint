package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.entity.DataBackup;
import com.jspgou.cms.manager.DataBackupMng;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.ConfigAttr;
import com.jspgou.core.entity.EmailSender;
import com.jspgou.core.entity.Global;
import com.jspgou.core.entity.MessageTemplate;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.FtpMng;
import com.jspgou.core.manager.GlobalMng;
import com.jspgou.core.manager.UserMng;
import com.jspgou.core.manager.WebsiteMng;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfigAct {
    private final Logger log = LoggerFactory.getLogger(ConfigAct.class);

    @Autowired
    private WebsiteMng websiteMng;

    @Autowired
    private GlobalMng globalMng;

    @Autowired
    private UserMng userMng;

    @Autowired
    private DataBackupMng dataBackupMng;

    @Autowired
    private FtpMng ftpMng;

    @RequestMapping({"/config/v_global_edit.do"})
    public String globalEdit(HttpServletRequest request, ModelMap model) {
        model.addAttribute("global", SiteUtils.getWeb(request).getGlobal());
        return "config/global_edit";
    }

    @RequestMapping({"/config/o_global_update.do"})
    public String globalUpdate(Global global, HttpServletRequest request, ModelMap model) {
        global.setId(SiteUtils.getWeb(request).getGlobal().getId());
        this.globalMng.update(global);
        this.log.info("update Global success.");
        model.addAttribute("message", "global.success");
        return globalEdit(request, model);
    }

    @RequestMapping({"/config/v_basic_edit.do"})
    public String basicEdit(HttpServletRequest request, ModelMap model) {
        Website website = SiteUtils.getWeb(request);
        List ftpList = this.ftpMng.getList();

        int tplPathLength = website.getTplPath().length();
        model.addAttribute("ftpList", ftpList);
        model.addAttribute("website", website);
        return "config/basic_edit";
    }

    @RequestMapping({"/config/o_basic_update.do"})
    public String basicUpdate(Website bean, Integer uploadFtpId, Integer syncPageFtpId, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateBaseUpdate(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Website site = SiteUtils.getWeb(request);
        bean.setId(site.getId());
        bean = this.websiteMng.update1(bean, uploadFtpId, syncPageFtpId);
        this.log.info("update website success. id={}", site.getId());
        model.addAttribute("message", "global.success");
        return basicEdit(request, model);
    }

    @RequestMapping({"/config/v_sso_edit.do"})
    public String ssoAuthenticateEdit(HttpServletRequest request, ModelMap model) {
        Website website = this.websiteMng.get();
        model.addAttribute("ssoMap", website.getSsoAttr());
        model.addAttribute("configAttr", website.getWebsiteAttr());
        return "config/sso_edit";
    }

    @RequestMapping({"/config/o_sso_update.do"})
    public String ssoAuthenticateUpdate(HttpServletRequest request, ModelMap model) {
        Map ssoMap = RequestUtils.getRequestMap(request, "attr_");
        this.websiteMng.updateSsoAttr(ssoMap);
        model.addAttribute("message", "global.success");
        this.log.info("update attrs of Config.");
        return ssoAuthenticateEdit(request, model);
    }

    @RequestMapping({"/config/v_shop_edit.do"})
    public String shopEdit(HttpServletRequest request, ModelMap model) {
        model.addAttribute("config", SiteUtils.getWeb(request));
        return "config/basic_edit";
    }

    @RequestMapping({"/config/o_shop_update.do"})
    public String shopUpdate(Website website, HttpServletRequest request, ModelMap model) {
        website.setId(SiteUtils.getWebId(request));
        this.websiteMng.update(website);
        this.log.info("update website success. id={}", website.getId());
        model.addAttribute("message", "global.success");
        return basicEdit(request, model);
    }

    @RequestMapping({"/config/v_email_edit.do"})
    public String emailEdit(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        model.addAttribute("emailSender", web.getEmailSender());
        model.addAttribute("messageMap", web.getMessages());
        return "config/email_edit";
    }

    @RequestMapping({"/config/o_email_update.do"})
    public String emailUpdate(EmailSender emailSender, String resetPasswordSubject, String resetPasswordText, String activeTitle, String activeTxt, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEmail(request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        web.setEmailSender(emailSender);
        Map messages = web.getMessages();
        MessageTemplate resetPassword = new MessageTemplate();
        resetPassword.setSubject(resetPasswordSubject);
        resetPassword.setText(resetPasswordText);
        resetPassword.setActiveTitle(activeTitle);
        resetPassword.setActiveTxt(activeTxt);
        messages.put("resetPassword", resetPassword);
        this.websiteMng.update(web);
        this.log.info("update EmailSender success.");
        return emailEdit(request, model);
    }

    @RequestMapping({"/config/o_email_send_test.do"})
    public String sendEmailTest(EmailSender email, String to, MessageTemplate tpl, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        Website web = SiteUtils.getWeb(request);
        try {
            String base = new String(web.getUrlBuff(true));
            this.userMng.senderEmail(Long.valueOf(0L), "Test_Username", base, to, "Test_ResetKey",
                    "Test_ResetPassword", email, tpl);
            ResponseUtils.renderJson(response, new JSONObject().put("success",
                    true).toString());
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("message", e.getMessage());
            ResponseUtils.renderJson(response, json.toString());
        }
        return null;
    }

    @RequestMapping({"/config/v_api_edit.do"})
    public String apiEdit(HttpServletRequest request, ModelMap model) {
        model.addAttribute("configAttr", this.globalMng.findIdkey().getConfigAttr());
        return "config/attr_edit";
    }

    @RequestMapping({"/config/o_aip_update.do"})
    public String apiUpdate(Boolean qqEnable, Boolean sinaEnable, Boolean weixinEnable, ConfigAttr configattr, HttpServletRequest request, ModelMap model) {
        configattr.setQqEnable(qqEnable.booleanValue());
        configattr.setSinaEnable(sinaEnable.booleanValue());
        configattr.setWeixinEnable(weixinEnable.booleanValue());
        this.globalMng.updateConfigAttr(configattr);
        model.addAttribute("message", "global.success");
        this.log.info("update attrs of Config.");
        return apiEdit(request, model);
    }

    @RequestMapping({"/config/v_dataBackup.do"})
    public String v_dataBackup(HttpServletRequest request, ModelMap model) {
        DataBackup dataBackup = this.dataBackupMng.getDataBackup();
        model.addAttribute("dataBackup", dataBackup);
        return "config/dataBackup";
    }

    @RequestMapping({"/config/o_dataBackup.do"})
    public void o_dataBackup(DataBackup bean, HttpServletRequest request, ModelMap model, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        this.dataBackupMng.update(bean);
        response.setContentType("application/x-download;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + bean.getDataBaseName() + new Date().getTime() + ".sql");
        PrintWriter out = response.getWriter();
        out.print(backup(bean));
        out.flush();
        out.close();
    }

    private String backup(DataBackup bean) {
        String outStr = "";
        try {
            Runtime rt = Runtime.getRuntime();
            Process child = rt.exec("cmd /c mysqldump -u" + bean.getUsername() + " -p" + bean.getPassword() +
                    " -h" + bean.getAddress() + " " + bean.getDataBaseName());
            InputStream in = child.getInputStream();
            InputStreamReader xx = new InputStreamReader(in, "utf-8");

            StringBuffer sb = new StringBuffer("");
            BufferedReader br = new BufferedReader(xx);
            String inStr;
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
            child.waitFor();
            in.close();
            xx.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outStr;
    }

    private WebErrors validateEmail(HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        return errors;
    }

    private WebErrors validateBaseUpdate(Website website, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        return errors;
    }
}

