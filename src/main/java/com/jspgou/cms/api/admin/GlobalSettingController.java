package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.EmailSender;
import com.jspgou.core.entity.Global;
import com.jspgou.core.entity.MessageTemplate;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.GlobalMng;
import com.jspgou.core.manager.WebsiteMng;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalSettingController {

    @Autowired
    private GlobalMng globalMng;

    @Autowired
    private WebsiteMng websiteMng;

    @RequestMapping({"/global/get"})
    public void edit(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (SiteUtils.getWeb(request).getGlobal() != null) {
                Global goobal = SiteUtils.getWeb(request)
                        .getGlobal();
                body = goobal.convertToJson().toString();
            } else {
                code = 206;
                message = "\"object not found\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/global/update"})
    public void update(Global bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(), bean.getPort(), bean.getDefImg(), bean.getActiveScore(), bean.getStockWarning(), bean.getDefImg()});
            if (!errors.hasErrors()) {
                this.globalMng.update(bean);
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/Basic/get"})
    public void getBasic(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Website website = SiteUtils.getWeb(request);
            if (website != null) {
                body = website.convertToJson().toString();
            } else {
                code = 206;
                message = "\"object not found\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/Basic/update"})
    public void updateBase(Website bean, Integer syncPageFtpId, Integer uploadFtpId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(), bean.getName(), bean.getDomain(), bean.getRelativePath(), bean.getSuffix(), bean.getLocaleFront(), bean.getLocaleAdmin(), bean.getPageSync(), bean.getResouceSync()});
            if (!errors.hasErrors()) {
                Website site = SiteUtils.getWeb(request);
                bean.setId(site.getId());
                bean = this.websiteMng.update1(bean, uploadFtpId, syncPageFtpId);
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/email/getEmail"})
    public void getEmail(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Website web = SiteUtils.getWeb(request);
            Map templates = web.getMessages();
            JSONObject json = new JSONObject();
            JSONObject obj = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArray1 = new JSONArray();
            if (templates != null) {
                MessageTemplate template = (MessageTemplate) templates.get("resetPassword");
                obj.put("resetPasswordSubject", CommonUtils.parseStr(template.getSubject()));
                obj.put("text", CommonUtils.parseStr(template.getText()));
                obj.put("activeTitle", CommonUtils.parseStr(template.getActiveTitle()));
                obj.put("activeTxt", CommonUtils.parseStr(template.getActiveTxt()));
            } else {
                obj.put("resetPasswordSubject", "");
                obj.put("text", "");
                obj.put("activeTitle", "");
                obj.put("activeTxt", "");
            }
            jsonArray.put(obj);
            jsonArray1.put(web.getEmailSender().convertToJson());
            json.put("messageMap", jsonArray);
            json.put("emailSender", jsonArray1);
            body = json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/email/update"})
    public void updateEmail(EmailSender emailSender, String resetPasswordSubject, String text, String activeTitle, String activeTxt, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{emailSender.getUsername(), emailSender.getEncoding(), emailSender.getHost(), emailSender.getPersonal(), emailSender.getPassword(), resetPasswordSubject, text, activeTitle, activeTxt});
            if (!errors.hasErrors()) {
                Website web = SiteUtils.getWeb(request);
                web.setEmailSender(emailSender);
                Map messages = web.getMessages();
                MessageTemplate resetPassword = new MessageTemplate();
                resetPassword.setSubject(resetPasswordSubject);
                resetPassword.setText(text);
                resetPassword.setActiveTitle(activeTitle);
                resetPassword.setActiveTxt(activeTxt);
                messages.put("resetPassword", resetPassword);
                this.websiteMng.update(web);
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

