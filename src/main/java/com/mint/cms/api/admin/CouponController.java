package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.entity.Coupon;
import com.mint.cms.manager.CategoryMng;
import com.mint.cms.manager.CouponMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
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
public class CouponController {

    @Autowired
    private CouponMng couponMng;

    @Autowired
    private CategoryMng categoryMng;

    @RequestMapping({"/coupon/list"})
    public void list(Integer pageNo, Integer pageSize, Integer categoryId, HttpServletRequest request, HttpServletResponse response) {
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
                Pagination pagination = this.couponMng.getPage(pageNo.intValue(), pageSize.intValue(), categoryId);
                List<Coupon> coupons = (List<Coupon>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (Coupon coupon : coupons) {
                    jsons.add(coupon.converToJson());
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
    @RequestMapping({"/coupon/save"})
    public void save(Coupon coupon, Integer categoryId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{coupon.getCouponName(), coupon.getCouponTime(),
                    coupon.getCouponEndTime(), coupon.getIsusing(), coupon.getLeastPrice(), coupon.getCouponPrice(), coupon.getCouponCount()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                Website site = CmsThreadVariable.getSite();
                this.couponMng.save(coupon, site, categoryId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/coupon/get"})
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
                Coupon coupon = new Coupon();
                if ((id != null) && (id.longValue() != 0L)) {
                    coupon = this.couponMng.findById(id);
                }
                if (coupon != null) {
                    body = coupon.converToJson().toString();
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
    @RequestMapping({"/coupon/disabled"})
    public void update(String ids, Boolean isUsing, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{isUsing, ids});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str = ids.split(",");
                for (int i = 0; i < str.length; i++) {
                    Coupon coupon = this.couponMng.findById(Long.valueOf(Long.parseLong(str[i])));
                    coupon.setIsusing(isUsing);
                    this.couponMng.update(coupon);
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
    @RequestMapping({"/coupon/delete"})
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
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                Website site = CmsThreadVariable.getSite();
                this.couponMng.deleteByIds(id, site.getUploadUrl());
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

