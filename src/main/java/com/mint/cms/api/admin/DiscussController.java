package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.entity.Discuss;
import com.mint.cms.manager.DiscussMng;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.web.ResponseUtils;
import com.mint.core.web.WebErrors;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DiscussController {

    @Autowired
    private DiscussMng manager;
    private String product_name = "";

    @RequestMapping({"/discuss/list"})
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
                Pagination pagination = this.manager.getPage(null, null, null, userName, productName, startTime, endTime, pageNo.intValue(), pageSize.intValue(), true);
                List<Discuss> discussList = (List<Discuss>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (Discuss discuss : discussList) {
                    jsons.add(discuss.converToJson());
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

    @RequestMapping({"/discuss/get"})
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
                Discuss entity = new Discuss();
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
    @RequestMapping({"/discuss/update"})
    public void update(Discuss bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId()});
            if (errors.hasErrors()) {
                message = "\"param error\"";
                code = 202;
            } else {
                this.manager.update(bean);
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
    @RequestMapping({"/discuss/delete"})
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

    @RequestMapping({"/discuss/visual/list"})
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
                Pagination pagination = this.manager.getPage(null, null, null, userName, productName, startTime, endTime, pageNo.intValue(), pageSize.intValue(), true);
                List<Discuss> discussList = (List<Discuss>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (Discuss discuss : discussList) {
                    jsons.add(discuss.converToJson());
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
    @RequestMapping({"/discuss/visual/delete"})
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

