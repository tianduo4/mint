package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.entity.Consult;
import com.jspgou.cms.manager.ConsultMng;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.web.WebErrors;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConsultController {

    @Autowired
    private ConsultMng manager;
    private String product_name;

    @RequestMapping({"/consult/list"})
    public void list(String userName, String productName, Date startTime, Date endTime, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{pageNo, pageSize});
            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                Pagination pagination = this.manager.getPage(null, userName, productName, startTime, endTime, pageNo.intValue(), pageSize.intValue(), Boolean.valueOf(true));
                List<Consult> consults = (List<Consult>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (Consult consult : consults) {
                    jsons.add(consult.converToJson());
                }
                body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/consult/get"})
    public void get(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (errors.hasErrors()) {
                message = "\"param error\"";
                code = 202;
            } else {
                Consult entity = new Consult();
                if ((id != null) && (id.longValue() != 0L)) {
                    entity = this.manager.findById(id);
                }
                if (entity != null) {
                    body = entity.converToJson().toString();
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
    @RequestMapping({"/consult/update"})
    public void update(Long id, String adminReplay, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id, adminReplay});
            if (errors.hasErrors()) {
                message = "\"param error\"";
                code = 202;
            } else {
                Consult entity = this.manager.findById(id);
                if (entity != null) {
                    entity.setAdminReplay(adminReplay);
                    this.manager.update(entity);
                } else {
                    message = "\"object not found\"";
                    code = 206;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "\"system exception\"";
            code = 100;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/consult/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids});
            if (errors.hasErrors()) {
                message = "\"param error\"";
                code = 202;
            } else {
                String[] str = ids.split(",");
                Long[] id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(str[i]);
                }
                this.manager.deleteByIds(id);
            }
        } catch (Exception e) {
            message = "\"system exception\"";
            code = 100;
        }
        ApiResponse apiRequest = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiRequest);
    }

    @RequestMapping({"/consult/visual/list"})
    public void getVisualList(String userName, String productName, Date startTime, Date endTime, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{pageNo, pageSize});
            if (errors.hasErrors()) {
                message = "\"param error\"";
                code = 202;
            } else {
                if (StringUtils.isNotBlank(productName)) {
                    this.product_name = productName;
                } else {
                    productName = this.product_name;
                }
                Pagination pagination = this.manager.getPage(null, userName, productName, startTime, endTime, pageNo.intValue(), pageSize.intValue(), Boolean.valueOf(true));
                List<Consult> consults = (List<Consult>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (Consult consult : consults) {
                    jsons.add(consult.converToJson());
                }
                body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
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
    @RequestMapping({"/consult/visual/delete"})
    public void visualDelete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids});
            if (errors.hasErrors()) {
                message = "\"param error\"";
                code = 202;
            } else {
                String[] str = ids.split(",");
                Long[] id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(str[i]);
                }
                this.manager.deleteByIds(id);
            }
        } catch (Exception e) {
            message = "\"system exception\"";
            code = 100;
        }
        ApiResponse apiRequest = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiRequest);
    }
}

