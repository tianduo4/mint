package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.manager.OrderItemMng;
import com.mint.cms.manager.OrderMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.util.DateUtils;
import com.mint.common.web.ResponseUtils;

import java.text.ParseException;
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
public class SaleChatStatisticsApiController {

    @Autowired
    private OrderMng orderMng;

    @Autowired
    private OrderItemMng orderItemMng;

    @RequestMapping({"/statistics/saleChat"})
    public void chatStatistics(String type, String month, String year, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        boolean flage = true;
        try {
            if (StringUtils.isNotEmpty(type)) {
                if ("month".equals(type)) {
                    flage = StringUtils.isNotEmpty(month);
                    DateUtils.pasreToDate(month, DateUtils.COMMON_FORMAT_MONTH);
                } else if ("year".equals(type)) {
                    flage = StringUtils.isNotEmpty(year);
                    DateUtils.pasreToDate(year, DateUtils.COMMON_FORMAT_YEAR);
                }
            } else flage = false;

            if (flage) {
                body = this.orderMng.getOrderSale(CmsThreadVariable.getSite().getId(), type, month, year).toString();
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (ParseException e) {
            code = 202;
            message = "\"param error\"";
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/statistics/saleRank"})
    public void saleStatistics(Integer pageSize, Integer pageNo, Integer categoryId, Date startTime, Date endTime, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if ((pageSize != null) && (pageNo != null)) {
                Pagination page = this.orderItemMng.getPageProductSaleRank(CmsThreadVariable.getSite().getId(), null, categoryId, SimplePage.cpn(pageNo), pageSize.intValue(), startTime, endTime);
                List<Object[]> orderItems = (List<Object[]>) page.getList();
                JSONArray jsons = new JSONArray();
                for (Object[] obj : orderItems) {
                    JSONObject json = new JSONObject();
                    json.put("productName", obj[0] != null ? obj[0] : "");
                    json.put("categoryName", obj[1] != null ? obj[1] : "");
                    json.put("saleCount", obj[2] != null ? obj[2] : "");
                    jsons.add(json);
                }
                body = jsons.toString() + ",\"totalCount\":" + page.getTotalCount();
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

