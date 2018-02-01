package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import com.jspgou.plug.weixin.entity.Weixin;
import com.jspgou.plug.weixin.manager.WeixinMng;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeixinController {

    @Autowired
    private WeixinMng manager;

    @RequestMapping({"/weixin/get"})
    public void get(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Weixin entity = this.manager.find(SiteUtils.getWebId(request));
            if (entity.getId() == null) {
                entity = new Weixin();
            }
            body = entity.converToJson().toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/weixin/update"})
    public void update(Weixin bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(), bean.getAppKey(),
                    bean.getAppSecret(), bean.getToken(), bean.getWelcome()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                this.manager.update(bean);
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
    @RequestMapping({"/weixin/save"})
    public void save(Weixin bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getWelcome(), bean.getAppKey(),
                    bean.getAppSecret(), bean.getToken()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                bean.setSite(SiteUtils.getWeb(request));
                this.manager.save(bean);
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

