package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.entity.Webservice;
import com.mint.cms.manager.WebserviceMng;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.web.ResponseUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebserviceController {

    @Autowired
    private WebserviceMng manager;

    @RequestMapping({"/webservice/list"})
    public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (pageNo == null) {
                pageNo = Integer.valueOf(1);
            }
            if (pageSize == null) {
                pageSize = Integer.valueOf(10);
            }
            Pagination page = this.manager.getPage(pageNo.intValue(), pageSize.intValue());
            List list = page.getList();
            int totalCount = page.getTotalCount();
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((Webservice) list.get(i)).convertToJson());
                }
            }
            body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/webservice/get"})
    public void getWeservice(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                Webservice webservice = new Webservice();
                if (id.intValue() != 0) {
                    webservice = this.manager.findById(id);
                }
                if (webservice != null) {
                    body = webservice.convertToJson().toString();
                } else {
                    code = 206;
                    message = "\"object not found\"";
                }
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

    @SignValidate
    @RequestMapping({"/webservice/save"})
    public void save(Webservice bean, String paramName, String defaultValue, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                    bean.getAddress(), bean.getType()});
            if (!errors.hasErrors()) {
                String[] paramNames = null;
                String[] defaultValues = null;
                if (paramName != null) {
                    String[] str = paramName.split(",");
                    paramNames = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        paramNames[i] = str[i];
                    }
                }
                if (defaultValue != null) {
                    String[] str1 = defaultValue.split(",");
                    defaultValues = new String[str1.length];
                    for (int i = 0; i < str1.length; i++) {
                        defaultValues[i] = str1[i];
                    }
                }
                bean = this.manager.save(bean, paramNames, defaultValues);
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

    @SignValidate
    @RequestMapping({"/webservice/update"})
    public void updateWebservice(Webservice bean, String paramName, String defaultValue, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(),
                    bean.getAddress(), bean.getType()});
            if (!errors.hasErrors()) {
                String[] paramNames = null;
                String[] defaultValues = null;
                if (paramName != null) {
                    String[] str = paramName.split(",");
                    paramNames = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        paramNames[i] = str[i];
                    }
                }
                if (defaultValue != null) {
                    String[] str1 = defaultValue.split(",");
                    defaultValues = new String[str1.length];
                    for (int i = 0; i < str1.length; i++) {
                        defaultValues[i] = str1[i];
                    }
                }
                bean = this.manager.update(bean, paramNames, defaultValues);
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

    @SignValidate
    @RequestMapping({"/webservice/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids});
            if (!errors.hasErrors()) {
                Integer[] id = null;
                String[] str = ids.split(",");
                id = new Integer[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Integer.valueOf(Integer.parseInt(str[i]));
                }
                this.manager.deleteByIds(id);
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

