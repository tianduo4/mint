package com.mint.cms.app;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.mint.cms.entity.ApiAccount;
import com.mint.cms.entity.ApiRecord;
import com.mint.cms.entity.Cart;
import com.mint.cms.entity.Order;
import com.mint.cms.entity.PaymentPlugins;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.ApiAccountMng;
import com.mint.cms.manager.ApiRecordMng;
import com.mint.cms.manager.ApiUtilMng;
import com.mint.cms.manager.OrderMng;
import com.mint.cms.service.ShoppingSvc;
import com.mint.cms.web.SiteUtils;
import com.mint.common.util.AlipayAPIClientFactory;
import com.mint.common.util.Apputil;
import com.mint.common.util.ConvertMapToJson;
import com.mint.common.util.PayUtil;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderAppController {

    @Autowired
    private ApiUtilMng apiUtilMng;

    @Autowired
    private ShoppingSvc shoppingSvc;

    @Autowired
    private OrderMng orderMng;

    @Autowired
    private ApiRecordMng apiRecordMng;

    @Autowired
    private ApiAccountMng apiAccountMng;

    @RequestMapping({"/api/createorders.jspx"})
    public void createorders(HttpServletRequest request, HttpServletResponse response) {
        Website web = SiteUtils.getWeb(request);
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        String cartItemId = request.getParameter("cartItemId");
        String shippingMethodId = request.getParameter("shippingMethodId");
        String deliveryInfo = request.getParameter("deliveryInfo");
        String paymentMethodId = request.getParameter("paymentMethodId");
        String comments = request.getParameter("comments");
        String memberCouponId = request.getParameter("memberCouponId");
        Long[] cartItemIds = null;
        Long shippingMethodIds = Long.valueOf(Long.parseLong(shippingMethodId));
        Long deliveryInfos = Long.valueOf(Long.parseLong(deliveryInfo));
        Long paymentMethodIds = Long.valueOf(Long.parseLong(paymentMethodId));
        if (StringUtils.isNotBlank(cartItemId)) {
            String[] cids = cartItemId.split(",");
            cartItemIds = new Long[cids.length];
            for (int i = 0; i < cids.length; i++) {
                cartItemIds[i] = Long.valueOf(Long.parseLong(cids[i]));
            }

        }

        boolean createOrder = true;
        if ((user != null) &&
                (createOrder)) {
            Order order = null;
            Cart cart = this.shoppingSvc.getCart(user.getId());
            if (cart != null) {
                order = this.orderMng.createOrder(cart, cartItemIds,
                        shippingMethodIds, deliveryInfos, paymentMethodIds,
                        comments, request.getRemoteAddr(), user,
                        web.getId(), memberCouponId);
            }
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(null,
                "/api/createorders.jspx", request));
    }

    @RequestMapping({"/api/myorders.jspx"})
    public void myorders(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Website web = SiteUtils.getWeb(request);
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        JSONObject o = new JSONObject();
        JSONArray arr = new JSONArray();
        if (user != null) {
            String userName = request.getParameter("username");
            Long code = Apputil.getRequestLong(request.getParameter("code"));
            String productName = request.getParameter("productName");
            Long paymentId = Apputil.getRequestLong(request
                    .getParameter("paymentId"));
            Long shippingId = Apputil.getRequestLong(request
                    .getParameter("shippingId"));
            Integer status = Apputil.getRequestInteger(request
                    .getParameter("status"));
            Date startTime = Apputil.getRequestDate(request
                    .getParameter("startTime"));
            Date endTime = Apputil.getRequestDate(request
                    .getParameter("endTime"));
            Double startOrderTotal = Apputil.getRequestDouble(request
                    .getParameter("startOrderTotal"));
            Double endOrderTotal = Apputil.getRequestDouble(request
                    .getParameter("endOrderTotal"));
            Integer firstResult = Apputil.getfirstResult(request
                    .getParameter("firstResult"));
            Integer maxResults = Apputil.getmaxResults(request
                    .getParameter("maxResults"));

            List<Order> list = this.orderMng.getOrderList(web.getId(), user.getId(),
                    productName, userName, paymentId, shippingId, startTime,
                    endTime, startOrderTotal, endOrderTotal, status, code,
                    firstResult.intValue(), maxResults.intValue());
            for (Order order : list) {
                o.put("id", order.getId());
                o.put("code", order.getCode());
                o.put("productName", order.getProductName());
                o.put("productPrice", order.getProductPrice());
                o.put("freight", order.getFreight());
                o.put("createTime", order.getCreateTime());
                o.put("shippingStatus", order.getShippingStatus());
                o.put("paymentStatus", order.getPaymentStatus());
                o.put("paymentId", order.getPayment().getId());
                o.put("total", order.getTotal());
                arr.put(o);
            }
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                arr.toString(), "/api/myorders.jspx", request));
    }

    @RequestMapping({"/api/user/buyorder.jspx"})
    public void buyorder(Long id, String appId, Integer orderType, String outOrderNum, String sign, String sessionKey, HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        String result = "00";
        Website site = SiteUtils.getWeb(request);
        String callback = request.getParameter("callback");
        if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign)) &&
                (StringUtils.isNotBlank(sessionKey))) {
            ApiRecord record = this.apiRecordMng.findBySign(sign, appId);
            if (record != null) {
                result = "04";
            } else {
                ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);

                String validateSign = getValidateSign(apiAccount, request);
                if (sign.equals(validateSign)) {
                    result = "01";
                    contentOrder(id, orderType, outOrderNum);
                } else {
                    result = "03";
                }
            }
        }

        map.put("result", result);
        if (!StringUtils.isBlank(callback)) {
            ResponseUtils.renderJson(response, callback + "(" +
                    ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
        } else
            ResponseUtils.renderJson(response,
                    ConvertMapToJson.buildJsonBody(map, 0, false));
    }

    private void contentOrder(Long orderId, Integer orderType, String outOrderNum) {
        Double orderAmount = Double.valueOf(0.0D);
        if (orderId != null) {
            Order order = this.orderMng.findById(orderId);
            if (order != null) {
                PaymentPlugins payment = new PaymentPlugins();
                orderAmount = getAlipayMobileOrderAmount(outOrderNum, payment);
            }
            if (orderAmount.equals(Double.valueOf(0.0D))) {
                order.setPaymentStatus(Integer.valueOf(2));
                order.setTradeNo(outOrderNum);
                this.orderMng.updateByUpdater(order);
            }
        }
    }

    private Double getAlipayMobileOrderAmount(String outOrderNum, PaymentPlugins payment) {
        AlipayTradeQueryResponse res = query(
                "https://openapi.alipay.com/gateway.do", payment, null,
                outOrderNum);
        Double orderAmount = Double.valueOf(0.0D);
        if ((res.isSuccess()) && (res != null) &&
                (res.getCode().equals("10000")) &&
                ("TRADE_SUCCESS".equalsIgnoreCase(res.getTradeStatus()))) {
            String totalAmout = res.getTotalAmount();
            if (StringUtils.isNotBlank(totalAmout)) {
                orderAmount = Double.valueOf(Double.parseDouble(totalAmout));
            }

        }

        return orderAmount;
    }

    public AlipayTradeQueryResponse query(String serverUrl, PaymentPlugins payment, String out_trade_no, String trade_no) {
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(
                serverUrl, payment.getPublicKey(), payment.getSellerKey(),
                payment.getSellerEmail(), "UTF-8");
        AlipayTradeQueryRequest alipayQueryRequest = new AlipayTradeQueryRequest();
        String biz_content = "{    \"out_trade_no\":\"" +
                out_trade_no + "\"," +
                "    \"trade_no\":\"" + trade_no + "\"" + "  }";

        alipayQueryRequest.setBizContent(biz_content);
        AlipayTradeQueryResponse alipayQueryResponse = null;
        try {
            alipayQueryResponse = (AlipayTradeQueryResponse) alipayClient.execute(alipayQueryRequest);

            if ((alipayQueryResponse != null) && (alipayQueryResponse.isSuccess()) &&
                    (alipayQueryResponse.getCode().equals("10000"))) {
                if ("TRADE_SUCCESS".equalsIgnoreCase(alipayQueryResponse
                        .getTradeStatus())) {
                    List fund_bill_list = alipayQueryResponse
                            .getFundBillList();
                    if (fund_bill_list != null) {
                        doFundBillList(out_trade_no, fund_bill_list);
                    }

                } else if (!"TRADE_CLOSED"
                        .equalsIgnoreCase(alipayQueryResponse
                                .getTradeStatus())) {
                    "TRADE_FINISHED"
                            .equalsIgnoreCase(alipayQueryResponse
                                    .getTradeStatus());
                }

            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return alipayQueryResponse;
    }

    public static void doFundBillList(String out_trade_no, List<TradeFundBill> fund_bill_list) {
        for (TradeFundBill tfb : fund_bill_list)
            System.out.println("付款资金渠道：" + tfb.getFundChannel() + " 付款金额：" +
                    tfb.getAmount());
    }

    private String getValidateSign(ApiAccount apiAccount, HttpServletRequest request) {
        String appKey = apiAccount.getAppKey();
        Enumeration penum = request.getParameterNames();
        Map param = new HashMap();

        while (penum.hasMoreElements()) {
            String pKey = (String) penum.nextElement();
            String value = request.getParameter(pKey);

            if ((StringUtils.isNotBlank(value)) && (!pKey.equals("sign"))) {
                param.put(pKey, value);
            }
        }

        String validateSign = PayUtil.createSign(param, appKey);
        return validateSign;
    }
}

