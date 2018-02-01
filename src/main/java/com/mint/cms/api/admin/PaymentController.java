package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.entity.Payment;
import com.mint.cms.entity.Shipping;
import com.mint.cms.manager.PaymentMng;
import com.mint.cms.manager.ShippingMng;
import com.mint.cms.web.SignValidate;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PaymentController {

    @Autowired
    private PaymentMng manager;

    @Autowired
    private ShippingMng shippingMng;

    @RequestMapping({"/payment/list"})
    public void list(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            List list = this.manager.getList(SiteUtils.getWebId(request),
                    true);
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(((Payment) list.get(i)).converToJson());
                }
                body = jsonArray.toString();
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

    @RequestMapping({"/payment/get"})
    public void getPayment(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                JSONObject json = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                Payment payment = new Payment();
                if (id.longValue() != 0L) {
                    payment = this.manager.findById(id);
                }
                if (payment != null) {
                    List list = this.shippingMng.getList(SiteUtils.getWebId(request), false);
                    if ((list != null) && (list.size() > 0)) {
                        for (int i = 0; i < list.size(); i++) {
                            jsonArray.put(((Shipping) list.get(i)).converToJson());
                        }
                    }
                    json.put("shipping", jsonArray);
                    json.put("payment", payment.converToJson());
                    body = json.toString();
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
    @RequestMapping({"/payment/save"})
    public void save(Payment bean, String shippingIds, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Website web = SiteUtils.getWeb(request);
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getName(), bean.getType(), bean.getPriority(), bean.getIntroduce(), bean.getDisabled(), bean.getIsDefault()});
            if (!errors.hasErrors()) {
                if (bean.getIsDefault().booleanValue()) {
                    List list = this.manager.getList(Long.valueOf(1L), true);
                    for (int i = 0; i < list.size(); i++) {
                        ((Payment) list.get(i)).setIsDefault(Boolean.valueOf(false));
                        this.manager.update((Payment) list.get(i));
                    }
                }

                long[] shippingId = null;
                String[] str = shippingIds.split(",");
                shippingId = new long[str.length];
                for (int i = 0; i < str.length; i++) {
                    shippingId[i] = Long.parseLong(str[i]);
                }
                bean.setWebsite(web);
                bean = this.manager.save(bean);
                this.manager.addShipping(bean, shippingId);
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
    @RequestMapping({"/payment/update"})
    public void update(Payment bean, String shippingIds, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(), bean.getName(), bean.getType(), bean.getPriority(), bean.getIntroduce(), bean.getDisabled(), bean.getIsDefault()});
            if (!errors.hasErrors()) {
                if (bean.getIsDefault().booleanValue()) {
                    List list = this.manager.getList(Long.valueOf(1L), true);
                    for (int i = 0; i < list.size(); i++) {
                        ((Payment) list.get(i)).setIsDefault(Boolean.valueOf(false));
                        this.manager.update((Payment) list.get(i));
                    }
                }
                long[] shippingId = null;
                String[] str = shippingIds.split(",");
                shippingId = new long[str.length];
                for (int i = 0; i < str.length; i++) {
                    shippingId[i] = Long.parseLong(str[i]);
                }
                bean = this.manager.update(bean);
                this.manager.updateShipping(bean, shippingId);
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
    @RequestMapping({"/payment/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids});
            if (!errors.hasErrors()) {
                Long[] id = null;
                String[] str = ids.split(",");
                id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
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

    @SignValidate
    @RequestMapping({"/payment/priority"})
    public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids, priority});
            if (!errors.hasErrors()) {
                Long[] id = null;
                Integer[] prioritys = null;
                String[] str = ids.split(",");
                id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                String[] str1 = priority.split(",");
                prioritys = new Integer[str1.length];
                for (int i = 0; i < str1.length; i++) {
                    prioritys[i] = Integer.valueOf(Integer.parseInt(str1[i]));
                }
                this.manager.updatePriority(id, prioritys);
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

