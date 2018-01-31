/*     */ package com.jspgou.cms.app;
/*     */ 
/*     */ import com.alipay.api.AlipayApiException;
/*     */ import com.alipay.api.AlipayClient;
/*     */ import com.alipay.api.domain.TradeFundBill;
/*     */ import com.alipay.api.request.AlipayTradeQueryRequest;
/*     */ import com.alipay.api.response.AlipayTradeQueryResponse;
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ApiRecord;
/*     */ import com.jspgou.cms.entity.Cart;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.entity.PaymentPlugins;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ApiAccountMng;
/*     */ import com.jspgou.cms.manager.ApiRecordMng;
/*     */ import com.jspgou.cms.manager.ApiUtilMng;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.service.ShoppingSvc;
/*     */ import com.jspgou.cms.web.SiteUtils;
/*     */ import com.jspgou.common.util.AlipayAPIClientFactory;
/*     */ import com.jspgou.common.util.Apputil;
/*     */ import com.jspgou.common.util.ConvertMapToJson;
/*     */ import com.jspgou.common.util.PayUtil;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class OrderAppAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ApiUtilMng apiUtilMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShoppingSvc shoppingSvc;
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiRecordMng apiRecordMng;
/*     */ 
/*     */   @Autowired
/*     */   private ApiAccountMng apiAccountMng;
/*     */ 
/*     */   @RequestMapping({"/api/createorders.jspx"})
/*     */   public void createorders(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  54 */     Website web = SiteUtils.getWeb(request);
/*  55 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/*  56 */     String cartItemId = request.getParameter("cartItemId");
/*  57 */     String shippingMethodId = request.getParameter("shippingMethodId");
/*  58 */     String deliveryInfo = request.getParameter("deliveryInfo");
/*  59 */     String paymentMethodId = request.getParameter("paymentMethodId");
/*  60 */     String comments = request.getParameter("comments");
/*  61 */     String memberCouponId = request.getParameter("memberCouponId");
/*  62 */     Long[] cartItemIds = null;
/*  63 */     Long shippingMethodIds = Long.valueOf(Long.parseLong(shippingMethodId));
/*  64 */     Long deliveryInfos = Long.valueOf(Long.parseLong(deliveryInfo));
/*  65 */     Long paymentMethodIds = Long.valueOf(Long.parseLong(paymentMethodId));
/*  66 */     if (StringUtils.isNotBlank(cartItemId)) {
/*  67 */       String[] cids = cartItemId.split(",");
/*  68 */       cartItemIds = new Long[cids.length];
/*  69 */       for (int i = 0; i < cids.length; i++) {
/*  70 */         cartItemIds[i] = Long.valueOf(Long.parseLong(cids[i]));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  75 */     boolean createOrder = true;
/*  76 */     if ((user != null) && 
/*  77 */       (createOrder)) {
/*  78 */       Order order = null;
/*  79 */       Cart cart = this.shoppingSvc.getCart(user.getId());
/*  80 */       if (cart != null) {
/*  81 */         order = this.orderMng.createOrder(cart, cartItemIds, 
/*  82 */           shippingMethodIds, deliveryInfos, paymentMethodIds, 
/*  83 */           comments, request.getRemoteAddr(), user, 
/*  84 */           web.getId(), memberCouponId);
/*     */       }
/*     */     }
/*     */ 
/*  88 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(null, 
/*  89 */       "/api/createorders.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/myorders.jspx"})
/*     */   public void myorders(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 101 */     Website web = SiteUtils.getWeb(request);
/* 102 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 103 */     JSONObject o = new JSONObject();
/* 104 */     JSONArray arr = new JSONArray();
/* 105 */     if (user != null) {
/* 106 */       String userName = request.getParameter("username");
/* 107 */       Long code = Apputil.getRequestLong(request.getParameter("code"));
/* 108 */       String productName = request.getParameter("productName");
/* 109 */       Long paymentId = Apputil.getRequestLong(request
/* 110 */         .getParameter("paymentId"));
/* 111 */       Long shippingId = Apputil.getRequestLong(request
/* 112 */         .getParameter("shippingId"));
/* 113 */       Integer status = Apputil.getRequestInteger(request
/* 114 */         .getParameter("status"));
/* 115 */       Date startTime = Apputil.getRequestDate(request
/* 116 */         .getParameter("startTime"));
/* 117 */       Date endTime = Apputil.getRequestDate(request
/* 118 */         .getParameter("endTime"));
/* 119 */       Double startOrderTotal = Apputil.getRequestDouble(request
/* 120 */         .getParameter("startOrderTotal"));
/* 121 */       Double endOrderTotal = Apputil.getRequestDouble(request
/* 122 */         .getParameter("endOrderTotal"));
/* 123 */       Integer firstResult = Apputil.getfirstResult(request
/* 124 */         .getParameter("firstResult"));
/* 125 */       Integer maxResults = Apputil.getmaxResults(request
/* 126 */         .getParameter("maxResults"));
/*     */ 
/* 128 */       List<Order> list = this.orderMng.getOrderList(web.getId(), user.getId(),
/* 129 */         productName, userName, paymentId, shippingId, startTime, 
/* 130 */         endTime, startOrderTotal, endOrderTotal, status, code, 
/* 131 */         firstResult.intValue(), maxResults.intValue());
/* 132 */       for (Order order : list) {
/* 133 */         o.put("id", order.getId());
/* 134 */         o.put("code", order.getCode());
/* 135 */         o.put("productName", order.getProductName());
/* 136 */         o.put("productPrice", order.getProductPrice());
/* 137 */         o.put("freight", order.getFreight());
/* 138 */         o.put("createTime", order.getCreateTime());
/* 139 */         o.put("shippingStatus", order.getShippingStatus());
/* 140 */         o.put("paymentStatus", order.getPaymentStatus());
/* 141 */         o.put("paymentId", order.getPayment().getId());
/* 142 */         o.put("total", order.getTotal());
/* 143 */         arr.put(o);
/*     */       }
/*     */     }
/*     */ 
/* 147 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 148 */       arr.toString(), "/api/myorders.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/buyorder.jspx"})
/*     */   public void buyorder(Long id, String appId, Integer orderType, String outOrderNum, String sign, String sessionKey, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 174 */     Map map = new HashMap();
/* 175 */     String result = "00";
/* 176 */     Website site = SiteUtils.getWeb(request);
/* 177 */     String callback = request.getParameter("callback");
/* 178 */     if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign)) && 
/* 179 */       (StringUtils.isNotBlank(sessionKey)))
/*     */     {
/* 181 */       ApiRecord record = this.apiRecordMng.findBySign(sign, appId);
/* 182 */       if (record != null) {
/* 183 */         result = "04";
/*     */       } else {
/* 185 */         ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);
/*     */ 
/* 187 */         String validateSign = getValidateSign(apiAccount, request);
/* 188 */         if (sign.equals(validateSign)) {
/* 189 */           result = "01";
/* 190 */           contentOrder(id, orderType, outOrderNum);
/*     */         } else {
/* 192 */           result = "03";
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 197 */     map.put("result", result);
/* 198 */     if (!StringUtils.isBlank(callback)) {
/* 199 */       ResponseUtils.renderJson(response, callback + "(" + 
/* 200 */         ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
/*     */     }
/*     */     else
/* 203 */       ResponseUtils.renderJson(response, 
/* 204 */         ConvertMapToJson.buildJsonBody(map, 0, false));
/*     */   }
/*     */ 
/*     */   private void contentOrder(Long orderId, Integer orderType, String outOrderNum)
/*     */   {
/* 210 */     Double orderAmount = Double.valueOf(0.0D);
/* 211 */     if (orderId != null) {
/* 212 */       Order order = this.orderMng.findById(orderId);
/* 213 */       if (order != null)
/*     */       {
/* 215 */         PaymentPlugins payment = new PaymentPlugins();
/* 216 */         orderAmount = getAlipayMobileOrderAmount(outOrderNum, payment);
/*     */       }
/* 218 */       if (orderAmount.equals(Double.valueOf(0.0D))) {
/* 219 */         order.setPaymentStatus(Integer.valueOf(2));
/* 220 */         order.setTradeNo(outOrderNum);
/* 221 */         this.orderMng.updateByUpdater(order);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private Double getAlipayMobileOrderAmount(String outOrderNum, PaymentPlugins payment)
/*     */   {
/* 229 */     AlipayTradeQueryResponse res = query(
/* 230 */       "https://openapi.alipay.com/gateway.do", payment, null, 
/* 231 */       outOrderNum);
/* 232 */     Double orderAmount = Double.valueOf(0.0D);
/* 233 */     if ((res.isSuccess()) && (res != null) && 
/* 234 */       (res.getCode().equals("10000")) && 
/* 235 */       ("TRADE_SUCCESS".equalsIgnoreCase(res.getTradeStatus()))) {
/* 236 */       String totalAmout = res.getTotalAmount();
/* 237 */       if (StringUtils.isNotBlank(totalAmout)) {
/* 238 */         orderAmount = Double.valueOf(Double.parseDouble(totalAmout));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 243 */     return orderAmount;
/*     */   }
/*     */ 
/*     */   public AlipayTradeQueryResponse query(String serverUrl, PaymentPlugins payment, String out_trade_no, String trade_no)
/*     */   {
/* 258 */     AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(
/* 259 */       serverUrl, payment.getPublicKey(), payment.getSellerKey(), 
/* 260 */       payment.getSellerEmail(), "UTF-8");
/* 261 */     AlipayTradeQueryRequest alipayQueryRequest = new AlipayTradeQueryRequest();
/* 262 */     String biz_content = "{    \"out_trade_no\":\"" + 
/* 264 */       out_trade_no + "\"," + 
/* 266 */       "    \"trade_no\":\"" + trade_no + "\"" + "  }";
/*     */ 
/* 268 */     alipayQueryRequest.setBizContent(biz_content);
/* 269 */     AlipayTradeQueryResponse alipayQueryResponse = null;
/*     */     try {
/* 271 */       alipayQueryResponse = (AlipayTradeQueryResponse)alipayClient.execute(alipayQueryRequest);
/*     */ 
/* 273 */       if ((alipayQueryResponse != null) && (alipayQueryResponse.isSuccess()) && 
/* 274 */         (alipayQueryResponse.getCode().equals("10000")))
/*     */       {
/* 276 */         if ("TRADE_SUCCESS".equalsIgnoreCase(alipayQueryResponse
/* 276 */           .getTradeStatus()))
/*     */         {
/* 278 */           List fund_bill_list = alipayQueryResponse
/* 279 */             .getFundBillList();
/* 280 */           if (fund_bill_list != null) {
/* 281 */             doFundBillList(out_trade_no, fund_bill_list);
/*     */           }
/*     */ 
/*     */         }
/* 285 */         else if (!"TRADE_CLOSED"
/* 284 */           .equalsIgnoreCase(alipayQueryResponse
/* 285 */           .getTradeStatus()))
/*     */         {
/* 287 */           "TRADE_FINISHED"
/* 288 */             .equalsIgnoreCase(alipayQueryResponse
/* 289 */             .getTradeStatus());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (AlipayApiException e)
/*     */     {
/* 299 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 302 */     return alipayQueryResponse;
/*     */   }
/*     */ 
/*     */   public static void doFundBillList(String out_trade_no, List<TradeFundBill> fund_bill_list)
/*     */   {
/* 308 */     for (TradeFundBill tfb : fund_bill_list)
/* 309 */       System.out.println("付款资金渠道：" + tfb.getFundChannel() + " 付款金额：" + 
/* 310 */         tfb.getAmount());
/*     */   }
/*     */ 
/*     */   private String getValidateSign(ApiAccount apiAccount, HttpServletRequest request)
/*     */   {
/* 316 */     String appKey = apiAccount.getAppKey();
/* 317 */     Enumeration penum = request.getParameterNames();
/* 318 */     Map param = new HashMap();
/*     */ 
/* 320 */     while (penum.hasMoreElements()) {
/* 321 */       String pKey = (String)penum.nextElement();
/* 322 */       String value = request.getParameter(pKey);
/*     */ 
/* 324 */       if ((StringUtils.isNotBlank(value)) && (!pKey.equals("sign"))) {
/* 325 */         param.put(pKey, value);
/*     */       }
/*     */     }
/*     */ 
/* 329 */     String validateSign = PayUtil.createSign(param, appKey);
/* 330 */     return validateSign;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.OrderAppAct
 * JD-Core Version:    0.6.0
 */