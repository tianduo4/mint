package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.entity.PaymentPlugins;
import com.mint.cms.manager.PaymentPluginsMng;
import com.mint.cms.web.SignValidate;
import com.mint.common.web.ResponseUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PaymentPluginsController {

    @Autowired
    private PaymentPluginsMng manager;

    @RequestMapping({"/paymentPlugins/list"})
    public void list(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            List<PaymentPlugins> list = this.manager.getList();
            JSONArray jsons = new JSONArray();
            for (PaymentPlugins p : list) {
                jsons.add(p.converToJson());
            }
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
    @RequestMapping({"/paymentPlugins/priority"})
    public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if ((!StringUtils.isNotBlank(ids)) || (!StringUtils.isNotBlank(priority))) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str1 = ids.split(",");
                Long[] wids1 = new Long[str1.length];
                for (int i = 0; i < str1.length; i++) {
                    wids1[i] = Long.valueOf(str1[i]);
                }

                String[] str2 = priority.split(",");
                Integer[] priority1 = new Integer[str2.length];
                for (int i = 0; i < str2.length; i++) {
                    priority1[i] = Integer.valueOf(str2[i]);
                }
                this.manager.updatePriority(wids1, priority1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/paymentPlugins/get"})
    public void get(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                PaymentPlugins p = new PaymentPlugins();
                if ((id != null) && (id.longValue() != 0L)) {
                    p = this.manager.findById(id);
                }
                if (p != null) {
                    body = p.converToJson().toString();
                } else {
                    code = 206;
                    message = "\"object not found\"";
                }
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
    @RequestMapping({"/paymentPlugins/update"})
    public void update(PaymentPlugins bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(), bean.getName(), bean.getPriority(),
                    bean.getPartner(), bean.getSellerKey(), bean.getSellerEmail(),
                    bean.getDisabled(), bean.getIsDefault()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                PaymentPlugins plugins = this.manager.findById(bean.getId());
                if (StringUtils.isNotEmpty(bean.getName())) {
                    plugins.setName(bean.getName());
                }
                if (StringUtils.isNotEmpty(bean.getPartner())) {
                    plugins.setPartner(bean.getPartner());
                }
                if (StringUtils.isNotEmpty(bean.getSellerEmail())) {
                    plugins.setSellerEmail(bean.getSellerEmail());
                }
                if (StringUtils.isNotEmpty(bean.getSellerKey())) {
                    plugins.setSellerKey(bean.getSellerKey());
                }
                if (StringUtils.isNotEmpty(bean.getCode())) {
                    plugins.setCode(bean.getCode());
                }
                if (StringUtils.isNotEmpty(bean.getPublicKey())) {
                    plugins.setPublicKey(bean.getPublicKey());
                }
                plugins.setIsDefault(bean.getIsDefault());
                plugins.setDisabled(bean.getDisabled());
                plugins.setPriority(bean.getPriority());
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
    @RequestMapping({"/paymentPlugins/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (!StringUtils.isNotBlank(ids)) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str = ids.split(",");
                Long[] id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(str[i]);
                }
                this.manager.deleteByIds(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

