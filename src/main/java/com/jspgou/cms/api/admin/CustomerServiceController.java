package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.entity.CustomerService;
import com.jspgou.cms.manager.CustomerServiceMng;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.web.WebErrors;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class CustomerServiceController {

    @Autowired
    private CustomerServiceMng manager;

    @RequestMapping({"/customerService/list"})
    public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
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
                Pagination pagination = this.manager.getPagination(null, SimplePage.cpn(pageNo), pageSize.intValue());
                List<CustomerService> list = (List<CustomerService>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (CustomerService customerService : list) {
                    jsons.add(customerService.converToJson());
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
    @RequestMapping({"/customerService/priority"})
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

    @SignValidate
    @RequestMapping({"/customerService/save"})
    public void save(CustomerService bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getName(),
                    bean.getContent(), bean.getType(), bean.getPriority(), bean.getDisable()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
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

    @RequestMapping({"/customerService/get"})
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
                CustomerService customerService = new CustomerService();
                if ((id != null) && (id.longValue() != 0L)) {
                    customerService = this.manager.findById(id);
                }
                if (customerService != null) {
                    body = customerService.converToJson().toString();
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
    @RequestMapping({"/customerService/update"})
    public void update(CustomerService bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(), bean.getName(), bean.getPriority(),
                    bean.getContent(), bean.getType(), bean.getDisable()});

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
    @RequestMapping({"/customerService/delete"})
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

