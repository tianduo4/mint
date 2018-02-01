package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.web.SignValidate;
import com.mint.common.web.RequestUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.ConfigAttr;
import com.mint.core.entity.Website;
import com.mint.core.entity.WebsiteAttr;
import com.mint.core.manager.GlobalMng;
import com.mint.core.manager.WebsiteMng;
import com.mint.core.web.WebErrors;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfigController {

    @Autowired
    private WebsiteMng websiteMng;

    @Autowired
    private GlobalMng globalMng;

    @RequestMapping({"/config/getsso"})
    public void ssoAuthenticateGet(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Website website = this.websiteMng.get();

            if (website != null) {
                List ssoAttr = website.getAuthUrl();
                WebsiteAttr attr = website.getWebsiteAttr();
                JSONObject json = new JSONObject();
                json.put("attr", ssoAttr);
                json.put("attr_ssoEnable", attr.getSsoEnable());
                body = json.toString();
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
    @RequestMapping({"/config/updatesso"})
    public void update(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Map ssoMap = RequestUtils.getRequestMap(request, "attr_");
            this.websiteMng.updateSsoAttr(ssoMap);
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/config/getApi"})
    public void apiGet(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            ConfigAttr configAttr = this.globalMng.findIdkey().getConfigAttr();
            JSONArray jsons = new JSONArray();
            jsons.add(configAttr.converToJson());
            body = jsons.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/config/updateApi"})
    public void updateApi(ConfigAttr bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            if (bean.getQqEnable().booleanValue()) {
                errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getQqID()});
            }
            if (bean.getSinaEnable().booleanValue()) {
                errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getSinaID()});
            }
            if (bean.getWeixinEnable().booleanValue()) {
                errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getWeixinID()});
            }
            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                if (StringUtils.isEmpty(bean.getSinaKey())) {
                    bean.getAttr().remove("sinaKey");
                }

                if (StringUtils.isEmpty(bean.getSinaID())) {
                    bean.getAttr().remove("sinaID");
                }

                if (StringUtils.isEmpty(bean.getWeixinKey())) {
                    bean.getAttr().remove("weixinKey");
                }

                if (StringUtils.isEmpty(bean.getWeixinID())) {
                    bean.getAttr().remove("weixinID");
                }

                if (StringUtils.isEmpty(bean.getQqID())) {
                    bean.getAttr().remove("qqWeboKey");
                }

                if (StringUtils.isEmpty(bean.getQqKey())) {
                    bean.getAttr().remove("qqKey");
                }

                if (StringUtils.isEmpty(bean.getQqWeboID())) {
                    bean.getAttr().remove("qqWeboID");
                }

                if (StringUtils.isEmpty(bean.getQqWeboKey())) {
                    bean.getAttr().remove("qqWeboKey");
                }

                if (bean.getQqWeboEnable() == null) {
                    bean.getAttr().remove("qqWeboEnable");
                }
                this.globalMng.updateConfigAttr(bean);
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

