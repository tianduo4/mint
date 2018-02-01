package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.api.PayUtils;
import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.OrderReturn;
import com.jspgou.cms.entity.PaymentPlugins;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.OrderReturnMng;
import com.jspgou.cms.manager.PaymentPluginsMng;
import com.jspgou.cms.manager.ShopDictionaryMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderReturnController {

    @Autowired
    private OrderReturnMng orederReturnMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private PaymentPluginsMng paymentPluginsMng;

    @Autowired
    private ShopDictionaryMng shopDictionaryMng;

    @RequestMapping({"/orderReturn/list"})
    public void orerReturn(Integer status, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
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
            Pagination page = this.orederReturnMng.getPage(status, SimplePage.cpn(pageNo),
                    CookieUtils.getPageSize(request));
            List list = page.getList();
            int totalCount = page.getTotalCount();
            JSONArray jsonArray = new JSONArray();
            if (((list != null ? 1 : 0) & (list.size() > 0 ? 1 : 0)) != 0) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((OrderReturn) list.get(i)).convertToJson());
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

    @RequestMapping({"/orderReturn/get"})
    public void getOrderReturn(Long id, HttpServletRequest request, HttpServletResponse response) {
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
                OrderReturn orderReturn = this.orederReturnMng.findById(id);
                if (orderReturn.getOrder() != null) {
                    body = orderReturn.getOrder().convertToJson().toString();
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
    @RequestMapping({"/orderReturn/affirm"})
    public void affirm(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                OrderReturn entity = this.orederReturnMng.findById(id);
                entity.setStatus(Integer.valueOf(2));
                this.orederReturnMng.update(entity);
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
    @RequestMapping({"/orderReturn/sendback"})
    public void sendback(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                OrderReturn entity = this.orederReturnMng.findById(id);
                entity.setStatus(Integer.valueOf(3));
                this.orederReturnMng.update(entity);
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
    @RequestMapping({"/orderReturn/receive"})
    public void receive(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                OrderReturn entity = this.orederReturnMng.findById(id);
                entity.setStatus(Integer.valueOf(5));
                this.orederReturnMng.update(entity);
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
    @RequestMapping({"/orderReturn/refund"})
    public void reimburse(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                String status = "退款转账失败！";
                OrderReturn entity = this.orederReturnMng.findById(id);
                PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipayToaccountTransfer");
                if (paymentPlugins != null) {
                    net.sf.json.JSONObject json = PayUtils.alipayReturn(paymentPlugins, entity);
                    if (Boolean.parseBoolean(json.get("status").toString())) {
                        ShopMember shopMember = entity.getOrder().getMember();
                        shopMember.setMoney(Double.valueOf(shopMember.getFreezeScore().intValue() + entity.getMoney().doubleValue()));
                        this.shopMemberMng.update(shopMember);
                        entity.setStatus(Integer.valueOf(6));
                        this.orederReturnMng.update(entity);
                    } else {
                        message = "\"delete error\"";
                        code = 205;
                        status = "退款失败，错误原因：" + json.get("msg").toString();
                        json.put("fail", status);
                    }
                    body = json.toString();
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
    @RequestMapping({"/orderReturn/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (!StringUtils.isNotBlank(ids)) {
                code = 202;
                message = "\"param error\"";
            } else {
                Long[] id = null;
                String[] str = ids.split(",");
                id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                this.orederReturnMng.deleteByIds(id);
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

