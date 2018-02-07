package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.manager.ConsultMng;
import com.mint.cms.manager.OrderItemMng;
import com.mint.cms.manager.OrderMng;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.common.page.Pagination;
import com.mint.common.util.DateUtils;
import com.mint.common.web.ResponseUtils;

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
public class IndexApiController {

    @Autowired
    private OrderMng orderMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ConsultMng consultMng;

    @Autowired
    private OrderItemMng orderItemMng;

    @RequestMapping({"/index/getSystemMemory"})
    public void getSystemMemory(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            JSONObject json = new JSONObject();
            json.put("lastLoginTime", DateUtils.parseDate(CmsThreadVariable.getApiUserLogin().getLoginTime(), DateUtils.COMMON_FORMAT_STR));
            json.put("maxMemory", Long.valueOf(Runtime.getRuntime().maxMemory() / 1024L / 1024L));
            json.put("freeMemory", Long.valueOf(Runtime.getRuntime().freeMemory() / 1024L / 1024L));
            json.put("useMemory", Long.valueOf(Runtime.getRuntime().totalMemory() / 1024L / 1024L - Runtime.getRuntime().freeMemory() / 1024L / 1024L));
            body = json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/index/statistics"})
    public void statistics(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Date todayDate = new Date();
            JSONObject json = new JSONObject();
            Long siteId = CmsThreadVariable.getSite().getId();
            json.put("orderCount", this.orderMng.getOrderCount(todayDate, siteId));
            json.put("orderSale", this.orderMng.getOrderSale(todayDate, siteId));
            json.put("memberCount", this.shopMemberMng.getMemberCount(siteId));
            json.put("productCount", this.productMng.getProductCount(CmsThreadVariable.getSite().getId()));
            body = json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/index/unDealStatistics"})
    public void unDealStatistics(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            JSONObject json = new JSONObject();
            Long siteId = CmsThreadVariable.getSite().getId();
            json.put("unSendOrderCount", this.orderMng.getUnSendOrderCount(siteId));
            json.put("unPayOrderCount", this.orderMng.getUnPayOrderCount(siteId));
            json.put("retrunCount", this.orderMng.getReturnCount(siteId));
            json.put("overStockProductCount", this.productMng.getOverStock(siteId));
            json.put("productConsultCount", this.consultMng.getProductConsult());
            body = json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/index/saleStatistics"})
    public void saleStatistics(String type, Integer size, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if ((type != null) && (size != null)) {
                Pagination page = this.orderItemMng.getPageProductSaleRank(CmsThreadVariable.getSite().getId(), type, null, 1, size.intValue(), null, null);
                List<Object[]> orderItems = (List<Object[]>) page.getList();
                JSONArray jsons = new JSONArray();
                for (Object[] obj : orderItems) {
                    JSONObject json = new JSONObject();
                    json.put("productName", obj[0] != null ? obj[0] : "");
                    json.put("categoryName", obj[1] != null ? obj[1] : "");
                    json.put("saleCount", obj[2] != null ? obj[2] : "");
                    jsons.add(json);
                }
                body = jsons.toString();
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

    @RequestMapping({"/index/chatStatistics"})
    public void chatStatistics(String type, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (StringUtils.isNotEmpty(type)) {
                body = this.orderMng.getOrderSale(CmsThreadVariable.getSite().getId(), type, null, null).toString();
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
}

