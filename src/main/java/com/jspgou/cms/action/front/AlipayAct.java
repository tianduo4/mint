/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.alipay.api.AlipayApiException;
/*     */ import com.alipay.api.AlipayClient;
/*     */ import com.alipay.api.DefaultAlipayClient;
/*     */ import com.alipay.api.internal.util.AlipaySignature;
/*     */ import com.alipay.api.request.AlipayTradeWapPayRequest;
/*     */ import com.alipay.api.response.AlipayTradeWapPayResponse;
/*     */ import com.jspgou.cms.Alipay;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.PaymentPlugins;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.PaymentPluginsMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.web.FrontUtils;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.httpclient.ProtocolException;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.jdom.JDOMException;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class AlipayAct extends Alipay
/*     */ {
/*  64 */   private static final Logger log = LoggerFactory.getLogger(AlipayAct.class);
/*     */   public static final String WEIXIN_AUTH_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
/*     */   private static final String WECHAT_CODE = "tpl.weChatCode";
/*     */   public static final String WECHAT_PUBLIC_PAY = "tpl.weChatPublicPay";
/*     */   public static final String SUCCESSFULLY_ORDER = "tpl.successfullyOrder";
/*     */   public static final String JSAPI = "JSAPI";
/*     */   public static final String PUBLIC_PAY_RETURN_URL = "/WeChatPublicPayReturn.jspx";
/*     */   public static final String WEIXIN_AUTH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
/*     */   private static final String ALIPAY_TRADE_WAP_PAY = "https://openapi.alipay.com/gateway.do";
/*     */   public static final String ALIPAY_MOBILE_RETURN_URL = "/shopMember/index.jspx";
/*     */   public static final String ALIPAY_MOBILE_PAY_NOTIFY_URL = "/alipayMobilePayReturn.jspx";
/*     */   public static final String UTF8 = "UTF-8";
/*     */   private static AlipayClient alipayClient;
/*     */   private static final String JSON = "json";
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private PaymentPluginsMng paymentPluginsMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @RequestMapping({"/pay.jspx"})
/*     */   public String pay(Long orderId, String code, String pay, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JDOMException, IOException
/*     */   {
/*  93 */     Website web = SiteUtils.getWeb(request);
/*  94 */     if ((orderId != null) && (this.manager.findById(orderId) != null)) {
/*  95 */       Order order = this.manager.findById(orderId);
/*  96 */       PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode(code);
/*  97 */       PrintWriter out = null;
/*  98 */       String aliURL = null;
/*  99 */       if ((!StringUtils.isBlank(code)) && (code.equals("weChatPayment"))) {
/* 100 */         return weChatPayment(paymentPlugins, web, order, request, response, model);
/*     */       }
/* 102 */       if ((!StringUtils.isBlank(code)) && (code.equals("alipayMobile")))
/*     */       {
/* 104 */         String alipayUrl = null;
/* 105 */         return alipayMobile(alipayUrl, paymentPlugins, web, order, null, 
/* 106 */           null, request, response, model);
/*     */       }
/* 108 */       if ((!StringUtils.isBlank(code)) && (code.equals("weChatPublicPay"))) {
/* 109 */         ShopMember member = MemberThread.get();
/*     */ 
/* 111 */         if (member == null) {
/* 112 */           return "redirect:../login.jspx";
/*     */         }
/* 114 */        String wechatOppenId = member.getWechatOppenid();
/* 115 */         if (StringUtils.isNotEmpty(wechatOppenId)) {
/* 116 */           return weChatPublicPay(paymentPlugins, web, pay, order, wechatOppenId, 
/* 117 */             request, model);
/*     */         }
/* 119 */         return weChatCode(paymentPlugins, orderId, pay, Integer.valueOf(1), request, response, model);
/*     */       }
/*     */       try
/*     */       {
/* 123 */         if ((!StringUtils.isBlank(code)) && (code.equals("alipayPartner")))
/* 124 */           aliURL = alipay(paymentPlugins, web, order, request, model);
/* 125 */         else if ((!StringUtils.isBlank(code)) && (code.equals("alipay"))) {
/* 126 */           aliURL = alipayapi(paymentPlugins, web, order, request, model);
/*     */         }
/* 128 */         response.setContentType("text/html;charset=UTF-8");
/* 129 */         out = response.getWriter();
/* 130 */         out.print(aliURL);
/*     */       } catch (IOException e) {
/* 132 */         e.printStackTrace();
/*     */       } finally {
/* 134 */         out.close();
/*     */       }
/* 136 */       return null;
/*     */     }
/*     */ 
/* 139 */     return FrontUtils.showMessage(request, model, "请回到我的订单页面，再进行支付");
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/aliReturn.jspx"})
/*     */   public String aliReturn(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException
/*     */   {
/* 146 */     Map params = new HashMap();
/* 147 */     Map requestParams = request.getParameterMap();
/* 148 */     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
/* 149 */       String name = (String)iter.next();
/* 150 */       String[] values = (String[])requestParams.get(name);
/* 151 */       String valueStr = "";
/* 152 */       for (int i = 0; i < values.length; i++) {
/* 153 */         valueStr = valueStr + values[i] + ",";
/*     */       }
/* 155 */       params.put(name, valueStr);
/*     */     }
/*     */ 
/* 158 */     String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
/*     */ 
/* 160 */     String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
/*     */ 
/* 162 */     String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
/* 163 */     PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipayPartner");
/* 164 */     Long orderId = Long.valueOf(Long.parseLong(out_trade_no));
/* 165 */     Order order = this.manager.findById(orderId);
/* 166 */     if (verify(params, paymentPlugins.getPartner(), paymentPlugins.getSellerKey())) {
/* 167 */       if (trade_status.equals("WAIT_BUYER_PAY"))
/*     */       {
/* 169 */         return FrontUtils.showMessage(request, model, "付款失败！");
/* 170 */       }if (trade_status.equals("WAIT_SELLER_SEND_GOODS"))
/*     */       {
/* 172 */         order.setPaymentStatus(Integer.valueOf(2));
/* 173 */         order.setTradeNo(trade_no);
/* 174 */         order.setPaymentCode("alipayPartner");
/* 175 */         this.manager.updateByUpdater(order);
/* 176 */         return FrontUtils.showMessage(request, model, "付款成功，请等待发货！");
/* 177 */       }if (trade_status.equals("WAIT_BUYER_CONFIRM_GOODS"))
/*     */       {
/* 179 */         return FrontUtils.showMessage(request, model, "已发货，未确认收货！");
/* 180 */       }if (trade_status.equals("TRADE_FINISHED"))
/*     */       {
/* 182 */         return FrontUtils.showMessage(request, model, "交易完成！");
/*     */       }
/* 184 */       return FrontUtils.showMessage(request, model, "success！");
/*     */     }
/*     */ 
/* 187 */     return FrontUtils.showMessage(request, model, "付款异常！");
/*     */   }
/*     */ 
/*     */   private String alipay(PaymentPlugins paymentPlugins, Website web, Order order, HttpServletRequest request, ModelMap model)
/*     */   {
/* 193 */     String payment_type = "1";
/*     */ 
/* 195 */     String notify_url = "http://" + web.getDomain() + "/aliReturn.jspx";
/*     */ 
/* 197 */     String return_url = "http://" + web.getDomain() + "/aliReturn.jspx";
/*     */ 
/* 199 */     String seller_email = paymentPlugins.getSellerEmail();
/*     */ 
/* 201 */     String out_trade_no = order.getId().toString();
/*     */ 
/* 203 */     String subject = "(" + order.getId() + ")";
/*     */ 
/* 205 */     String price = String.valueOf(order.getTotal());
/*     */ 
/* 207 */     String quantity = "1";
/*     */ 
/* 209 */     String logistics_fee = String.valueOf(order.getFreight());
/*     */ 
/* 211 */     String logistics_type = getLogisticsType(order);
/*     */ 
/* 213 */     String logistics_payment = "BUYER_PAY";
/*     */ 
/* 215 */     String body = "(" + order.getId() + ")";
/*     */ 
/* 217 */     String show_url = "http://" + web.getDomain() + "/";
/*     */ 
/* 219 */     String receive_name = order.getReceiveName();
/*     */ 
/* 221 */     String receive_address = order.getReceiveAddress();
/*     */ 
/* 223 */     String receive_zip = order.getReceiveZip();
/*     */ 
/* 225 */     String receive_phone = order.getReceivePhone();
/*     */ 
/* 227 */     String receive_mobile = order.getReceiveMobile();
/*     */ 
/* 229 */     Map sParaTemp = new HashMap();
/* 230 */     sParaTemp.put("service", "create_partner_trade_by_buyer");
/* 231 */     sParaTemp.put("partner", paymentPlugins.getPartner());
/* 232 */     sParaTemp.put("_input_charset", "utf-8");
/* 233 */     sParaTemp.put("payment_type", payment_type);
/* 234 */     sParaTemp.put("notify_url", notify_url);
/* 235 */     sParaTemp.put("return_url", return_url);
/* 236 */     sParaTemp.put("seller_email", seller_email);
/* 237 */     sParaTemp.put("out_trade_no", out_trade_no);
/* 238 */     sParaTemp.put("subject", subject);
/* 239 */     sParaTemp.put("price", price);
/* 240 */     sParaTemp.put("quantity", quantity);
/* 241 */     sParaTemp.put("logistics_fee", logistics_fee);
/* 242 */     sParaTemp.put("logistics_type", logistics_type);
/* 243 */     sParaTemp.put("logistics_payment", logistics_payment);
/* 244 */     sParaTemp.put("body", body);
/* 245 */     sParaTemp.put("show_url", show_url);
/* 246 */     sParaTemp.put("receive_name", receive_name);
/* 247 */     sParaTemp.put("receive_address", receive_address);
/* 248 */     sParaTemp.put("receive_zip", receive_zip);
/* 249 */     sParaTemp.put("receive_phone", receive_phone);
/* 250 */     sParaTemp.put("receive_mobile", receive_mobile);
/*     */ 
/* 252 */     String sHtmlText = buildRequest(sParaTemp, paymentPlugins.getSellerKey(), "get", "确认");
/* 253 */     return sHtmlText;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/aliReturnUrl.jspx"})
/*     */   public String aliReturndirect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException
/*     */   {
/* 260 */     Map params = new HashMap();
/* 261 */     Map requestParams = request.getParameterMap();
/* 262 */     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
/* 263 */       String name = (String)iter.next();
/* 264 */       String[] values = (String[])requestParams.get(name);
/* 265 */       String valueStr = "";
/* 266 */       for (int i = 0; i < values.length; i++) {
/* 267 */         valueStr = 
/* 268 */           valueStr + values[i] + ",";
/*     */       }
/*     */ 
/* 272 */       params.put(name, valueStr);
/*     */     }
/*     */ 
/* 275 */     String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
/*     */ 
/* 277 */     String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
/*     */ 
/* 279 */     String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
/*     */ 
/* 281 */     PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipay");
/* 282 */     Long orderId = Long.valueOf(Long.parseLong(out_trade_no));
/*     */ 
/* 284 */     Order order = this.manager.findByCode(orderId);
/* 285 */     if (verify(params, paymentPlugins.getPartner(), paymentPlugins.getSellerKey())) {
/* 286 */       if (trade_status.equals("TRADE_FINISHED"))
/*     */       {
/* 290 */         order.setPaymentStatus(Integer.valueOf(2));
/* 291 */         this.manager.updateByUpdater(order);
/* 292 */         return FrontUtils.showMessage(request, model, "付款成功，请等待发货！");
/*     */       }
/*     */ 
/* 297 */       if (trade_status.equals("TRADE_SUCCESS"))
/*     */       {
/* 301 */         order.setPaymentStatus(Integer.valueOf(2));
/* 302 */         this.manager.updateByUpdater(order);
/* 303 */         return FrontUtils.showMessage(request, model, "付款成功，请等待发货！");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 308 */       return FrontUtils.showMessage(request, model, "验证失败！");
/*     */     }
/* 310 */     return FrontUtils.showMessage(request, model, "付款异常！");
/*     */   }
/*     */ 
/*     */   private String alipayapi(PaymentPlugins paymentPlugins, Website web, Order order, HttpServletRequest request, ModelMap model)
/*     */   {
/* 316 */     String payment_type = "1";
/*     */ 
/* 318 */     String notify_url = "http://" + web.getDomain() + "/aliReturnUrl.jspx";
/*     */ 
/* 320 */     String return_url = "http://" + web.getDomain() + "/aliReturnUrl.jspx";
/*     */ 
/* 322 */     String seller_email = paymentPlugins.getSellerEmail();
/*     */ 
/* 325 */     String out_trade_no = order.getCode().toString();
/*     */ 
/* 328 */     String subject = order.getProductName();
/*     */ 
/* 330 */     String total_fee = String.valueOf(order.getTotal());
/*     */ 
/* 332 */     String body = "(" + order.getId() + ")";
/*     */ 
/* 334 */     String show_url = "http://" + web.getDomain() + "/";
/*     */ 
/* 336 */     String anti_phishing_key = "";
/*     */ 
/* 338 */     String exter_invoke_ip = RequestUtils.getIpAddr(request);
/* 339 */     Map sParaTemp = new HashMap();
/* 340 */     sParaTemp.put("service", "create_direct_pay_by_user");
/* 341 */     sParaTemp.put("partner", paymentPlugins.getPartner());
/* 342 */     sParaTemp.put("_input_charset", "utf-8");
/* 343 */     sParaTemp.put("payment_type", payment_type);
/* 344 */     sParaTemp.put("notify_url", notify_url);
/* 345 */     sParaTemp.put("return_url", return_url);
/* 346 */     sParaTemp.put("seller_email", seller_email);
/* 347 */     sParaTemp.put("out_trade_no", out_trade_no);
/* 348 */     sParaTemp.put("subject", subject);
/* 349 */     sParaTemp.put("total_fee", total_fee);
/* 350 */     sParaTemp.put("body", body);
/* 351 */     sParaTemp.put("show_url", show_url);
/* 352 */     sParaTemp.put("anti_phishing_key", anti_phishing_key);
/* 353 */     sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
/*     */ 
/* 355 */     String sHtmlText = buildRequest(sParaTemp, paymentPlugins.getSellerKey(), "get", "确认");
/* 356 */     return sHtmlText;
/*     */   }
/*     */   @RequestMapping({"/WeChatScanCodePayReturn.jspx"})
/*     */   public void WeChatScanCodePayReturn(Long code, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JDOMException, IOException, JSONException {
/* 361 */     JSONObject json = new JSONObject();
/*     */ 
/* 363 */     if (code != null) {
/* 364 */       if (this.manager.findByCode(code).getPaymentStatus().intValue() == 2) {
/* 365 */         json.put("status", 4);
/* 366 */         json.put("error", "支付成功,点击确定，跳转到我的订单");
/*     */       } else {
/* 368 */         json.put("status", 6);
/* 369 */         json.put("error", "订单未支付");
/*     */       }
/*     */     } else {
/* 372 */       String xml_receive_result = getXmlRequest(request);
/* 373 */       if ((StringUtils.isBlank(xml_receive_result)) || (xml_receive_result == "")) {
/* 374 */         json.put("status", 5);
/* 375 */         json.put("error", "检测到您可能没有进行扫码支付，请支付");
/*     */       } else {
/* 377 */         Map result_map = doXMLParse(xml_receive_result);
/* 378 */         String sign_receive = (String)result_map.get("sign");
/* 379 */         result_map.remove("sign");
/* 380 */         String key = this.paymentPluginsMng.findByCode("weChatPayment").getSellerKey();
/* 381 */         if (key == null) {
/* 382 */           json.put("status", 1);
/* 383 */           json.put("error", "微信扫码支付密钥错误，请通知商户");
/*     */         }
/* 385 */         String checkSign = createSign(result_map, key);
/* 386 */         if ((checkSign != null) && (checkSign.equals(sign_receive)))
/*     */         {
/*     */           try {
/* 389 */             noticeWeChatSuccess();
///* 390 */             if (result_map == null)
//                          break label551; //TODO
                      String return_code = (String)result_map.get("return_code");
/* 392 */             if (("SUCCESS".equals(return_code)) && ("SUCCESS".equals(result_map.get("result_code"))))
/*     */             {
/* 394 */               String transaction_id = (String)result_map.get("transaction_id");
/*     */ 
/* 396 */               String out_trade_no = (String)result_map.get("out_trade_no");
/* 397 */               Order order = this.manager.findByCode(Long.valueOf(Long.parseLong(out_trade_no)));
/* 398 */               order.setPaymentStatus(Integer.valueOf(2));
/* 399 */               order.setTradeNo(transaction_id);
/* 400 */               order.setPaymentCode("weChatPayment");
/* 401 */               this.manager.updateByUpdater(order);
/* 402 */               json.put("status", 0);
/* 403 */               json.put("error", "支付成功,点击确定，跳转到我的订单");
///* 404 */               break label551;  //TODO
                      }
//                      if ((!"SUCCESS".equals(return_code)) || (result_map.get("err_code") == null))
//                          break label551; //TODO
                     String message = (String)result_map.get("err_code_des");
/* 406 */             json.put("status", 2);
/* 407 */             json.put("error", message);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 411 */             e.printStackTrace();
/*     */           }
/*     */         } else {
/* 414 */           Map parames = new HashMap();
/* 415 */           parames.put("return_code", "FAIL");
/* 416 */           parames.put("return_msg", "校验错误");
/*     */ 
/* 418 */           String xmlWeChat = getRequestXml(parames);
/*     */           try {
/* 420 */             testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
/*     */           } catch (Exception e) {
/* 422 */             e.printStackTrace();
/*     */           }
/* 424 */           json.put("status", 3);
/* 425 */           json.put("error", "支付参数错误，请重新支付!");
/*     */         }
/*     */       }
/*     */     }
/* 429 */     label551: ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   private String weChatPayment(PaymentPlugins paymentPlugins, Website web, Order order, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JDOMException, IOException
/*     */   {
/* 434 */     Map paramMap = new HashMap();
/* 435 */     if ((StringUtils.isNotBlank(paymentPlugins.getPartner())) && (StringUtils.isNotBlank(paymentPlugins.getSellerEmail())))
/*     */     {
/* 437 */       paramMap.put("appid", paymentPlugins.getPartner());
/*     */ 
/* 439 */       paramMap.put("mch_id", paymentPlugins.getSellerEmail());
/*     */ 
/* 441 */       paramMap.put("device_info", "WEB");
/*     */ 
/* 443 */       paramMap.put("nonce_str", getWCRandomNumber(Integer.valueOf(10)));
/*     */ 
/* 445 */       paramMap.put("body", order.getProductName());
/*     */ 
/* 447 */       paramMap.put("out_trade_no", order.getCode().toString());
/*     */ 
/* 449 */       paramMap.put("fee_type", "CNY");
/*     */ 
/* 451 */       paramMap.put("total_fee", changeY2F(Double.valueOf(order.getTotal().doubleValue() + order.getFreight().doubleValue())));
/*     */ 
/* 454 */       paramMap.put("spbill_create_ip", "127.0.0.1");
/*     */ 
/* 456 */       paramMap.put("notify_url", "http://" + web.getDomain() + "/WeChatScanCodePayReturn.jspx");
/*     */ 
/* 458 */       paramMap.put("trade_type", "NATIVE");
/*     */ 
/* 460 */       paramMap.put("product_id", order.getId().toString());
/* 461 */       if (StringUtils.isNotBlank(paymentPlugins.getSellerKey()))
/*     */       {
/* 463 */         paramMap.put("sign", createSign(paramMap, paymentPlugins.getSellerKey()));
/*     */ 
/* 465 */         String xmlWeChat = getRequestXml(paramMap);
/* 466 */         String resXml = testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
/* 467 */         Map map = doXMLParse(resXml);
/* 468 */         String returnCode = (String)map.get("return_code");
/* 469 */         if (returnCode.equalsIgnoreCase("FAIL"))
/* 470 */           return FrontUtils.showMessage(request, model, (String)map.get("return_msg"));
/* 471 */         if (returnCode.equalsIgnoreCase("SUCCESS")) {
/* 472 */           if (map.get("err_code") != null)
/* 473 */             return FrontUtils.showMessage(request, model, (String)map.get("err_code_des"));
/* 474 */           if (((String)map.get("result_code")).equalsIgnoreCase("SUCCESS")) {
/* 475 */             String code_url = (String)map.get("code_url");
/* 476 */             model.addAttribute("code_url", code_url);
/* 477 */             ShopFrontHelper.setCommonData(request, model, web, 1);
/*     */ 
/* 479 */             model.addAttribute("orderId", order.getId());
/* 480 */             model.addAttribute("out_trade_no", order.getCode());
/* 481 */             model.addAttribute("weChatPayment", "weChatPayment");
/* 482 */             return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.weChatPayment", new Object[0]), request);
/*     */           }
/*     */         }
/* 485 */         return FrontUtils.showMessage(request, model, "系统超时，请重试!");
/*     */       }
/* 487 */       return FrontUtils.showMessage(request, model, "请通知商城管理员，微信扫码支付密钥没有设置，请检查!");
/*     */     }
/*     */ 
/* 490 */     return FrontUtils.showMessage(request, model, "请通知商城管理员，微信扫码支付商户号或者appid没有设置，请检查!");
/*     */   }
/*     */ 
/*     */   private String weChatCode(PaymentPlugins paymentPlugins, Long orderId, String pay, Integer type, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 500 */     Website web = SiteUtils.getWeb(request);
/* 501 */     String codeUrl = "";
/* 502 */     String redirect_uri = "/member/wechat_auth_call.jspx";
/* 503 */     if (StringUtils.isNotBlank(web.getGlobal().getContextPath()))
/* 504 */       redirect_uri = "http://" + web.getDomain() + web.getGlobal().getContextPath() + redirect_uri;
/*     */     else {
/* 506 */       redirect_uri = "http://" + web.getDomain() + redirect_uri;
/*     */     }
/* 508 */     codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + paymentPlugins.getPartner().trim() + "&redirect_uri=" + redirect_uri + 
/* 509 */       "&response_type=code&scope=snsapi_base&state=" + orderId + "@" + pay + "@" + type + "#wechat_redirect";
/* 510 */     model.addAttribute("codeUrl", codeUrl);
/* 511 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 512 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.weChatCode", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/wechat_auth_call.jspx"})
/*     */   public String weixinAuthCall(String code, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JDOMException, IOException
/*     */   {
/* 520 */     Website web = SiteUtils.getWeb(request);
/* 521 */     String[] state = request.getParameter("state").split("@");
/* 522 */     String orderId = state[0];
/* 523 */     String pay = state[1];
/* 524 */     String type = state[2];
/*     */ 
/* 526 */     Order order = this.manager.findById(Long.valueOf(orderId));
/*     */ 
/* 528 */     PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("weChatPublicPay");
/*     */ 
/* 530 */     String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid=" + paymentPlugins.getPartner().trim() + "&secret=" + paymentPlugins.getPublicKey().trim() + "&code=" + code;
/* 531 */     JSONObject json = httpsRequestToJsonObject(tokenUrl, "POST", null);
/* 532 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 533 */     String openid = null;
/* 534 */     if (json != null) {
/*     */       try {
/* 536 */         openid = json.getString("openid");
/* 537 */         if (StringUtils.isNotBlank(openid)) {
/* 538 */           ShopMember member = MemberThread.get();
/*     */ 
/* 540 */           if (member == null) {
/* 541 */             return "redirect:../login.jspx";
/*     */           }
/* 543 */           member.setWechatOppenid(openid);
/* 544 */           this.shopMemberMng.update(member);
/*     */         }
/*     */       } catch (JSONException e) {
/* 547 */         WebErrors errors = WebErrors.create(request);
/* 548 */         String errcode = null;
/*     */         try {
/* 550 */           errcode = json.getString("errcode");
/*     */         } catch (JSONException e1) {
/* 552 */           e1.printStackTrace();
/*     */         }
/* 554 */         if (StringUtils.isNotBlank(errcode)) {
/* 555 */           errors.addErrorCode("wechat.auth.fail");
/*     */         }
/* 557 */         return FrontHelper.showError(errors, web, model, request);
/*     */       }
/*     */     }
/* 560 */     WebErrors errors = WebErrors.create(request);
/* 561 */     if (openid == null) {
/* 562 */       errors.addErrorCode("wechat.auth.fail");
/* 563 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 565 */     if (Integer.valueOf(type).intValue() == 0) {
/* 566 */       errors.addErrorCode("wechat.pay.fail");
/* 567 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/*     */ 
/* 570 */     if (order == null) {
/* 571 */       errors.addErrorCode("wechat.pay.fail");
/* 572 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 574 */     if (Integer.valueOf(type).intValue() == 1) {
/* 575 */       return weChatPublicPay(paymentPlugins, web, pay, order, openid, request, model);
/*     */     }
/*     */ 
/* 581 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.weChatPublicPay", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public static JSONObject httpsRequestToJsonObject(String requestUrl, String requestMethod, String outputStr)
/*     */   {
/* 587 */     JSONObject jsonObject = null;
/*     */     try {
/* 589 */       StringBuffer buffer = httpsRequest(requestUrl, requestMethod, outputStr);
/* 590 */       jsonObject = new JSONObject(buffer.toString());
/*     */     } catch (ConnectException ce) {
/* 592 */       log.error("connect.timeout" + ce.getMessage());
/*     */     } catch (Exception e) {
/* 594 */       log.error("https.request.exception" + e.getMessage());
/*     */     }
/* 596 */     return jsonObject;
/*     */   }
/*     */ 
/*     */   private static StringBuffer httpsRequest(String requestUrl, String requestMethod, String output)
/*     */     throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, MalformedURLException, IOException, ProtocolException, UnsupportedEncodingException
/*     */   {
/* 602 */     URL url = new URL(requestUrl);
/* 603 */     HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
/* 604 */     connection.setDoOutput(true);
/* 605 */     connection.setDoInput(true);
/* 606 */     connection.setUseCaches(false);
/* 607 */     connection.setRequestMethod(requestMethod);
/* 608 */     if (output != null) {
/* 609 */       OutputStream outputStream = connection.getOutputStream();
/* 610 */       outputStream.write(output.getBytes("UTF-8"));
/* 611 */       outputStream.close();
/*     */     }
/*     */ 
/* 614 */     InputStream inputStream = connection.getInputStream();
/* 615 */     InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
/* 616 */     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
/* 617 */     String str = null;
/* 618 */     StringBuffer buffer = new StringBuffer();
/* 619 */     while ((str = bufferedReader.readLine()) != null) {
/* 620 */       buffer.append(str);
/*     */     }
/* 622 */     bufferedReader.close();
/* 623 */     inputStreamReader.close();
/* 624 */     inputStream.close();
/* 625 */     inputStream = null;
/* 626 */     connection.disconnect();
/* 627 */     return buffer;
/*     */   }
/*     */ 
/*     */   public String weChatPublicPay(PaymentPlugins paymentPlugins, Website web, String pay, Order order, String wechatOppenId, HttpServletRequest request, ModelMap model)
/*     */   {
/* 635 */     if ((StringUtils.isNotBlank(paymentPlugins.getPartner())) && (StringUtils.isNotBlank(paymentPlugins.getSellerEmail()))) {
/* 636 */       if (StringUtils.isNotBlank(paymentPlugins.getSellerKey())) {
/* 637 */         Map map = weixinUniformOrder("JSAPI", "/WeChatPublicPayReturn.jspx", web, paymentPlugins, order, wechatOppenId, null, request);
/* 638 */         String returnCode = (String)map.get("return_code");
/* 639 */         if (returnCode.equalsIgnoreCase("FAIL")) {
/* 640 */           return FrontUtils.showMessage(request, model, (String)map.get("return_msg"));
/*     */         }
/* 642 */         if (returnCode.equalsIgnoreCase("SUCCESS")) {
/* 643 */           if (map.get("err_code") != null)
/* 644 */             return FrontUtils.showMessage(request, model, (String)map.get("err_code_des"));
/* 645 */           if (((String)map.get("result_code")).equalsIgnoreCase("SUCCESS"))
/*     */           {
/* 647 */             String prepay_id = (String)map.get("prepay_id");
/* 648 */             Long time = Long.valueOf(System.currentTimeMillis() / 1000L);
/* 649 */             String nonceStr = getWCRandomNumber(Integer.valueOf(16));
/*     */ 
/* 651 */             model.addAttribute("appId", paymentPlugins.getPartner().trim());
/*     */ 
/* 653 */             model.addAttribute("timeStamp", time);
/*     */ 
/* 655 */             model.addAttribute("nonceStr", nonceStr);
/*     */ 
/* 657 */             model.addAttribute("package", "prepay_id=" + prepay_id);
/*     */ 
/* 659 */             model.addAttribute("signType", "MD5");
/* 660 */             Map paramMapSign = new HashMap();
/*     */ 
/* 662 */             paramMapSign.put("appId", paymentPlugins.getPartner().trim());
/* 663 */             paramMapSign.put("timeStamp", time.toString());
/* 664 */             paramMapSign.put("nonceStr", nonceStr);
/* 665 */             paramMapSign.put("package", "prepay_id=" + prepay_id);
/* 666 */             paramMapSign.put("signType", "MD5");
/*     */ 
/* 668 */             model.addAttribute("paySign", createSign(paramMapSign, paymentPlugins.getSellerKey().trim()));
/* 669 */             model.addAttribute("order", order);
/* 670 */             ShopFrontHelper.setCommonData(request, model, web, 1);
/*     */ 
/* 672 */             model.addAttribute("publicPay", "payOrderAgain");
/*     */ 
/* 674 */             return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.weChatPublicPay", new Object[0]), request);
/*     */           }
/*     */         }
/*     */ 
/* 678 */         return FrontUtils.showMessage(request, model, "system.timeout.please.try.again");
/*     */       }
/* 680 */       return FrontUtils.showMessage(request, model, "weChat.publickey.null");
/*     */     }
/*     */ 
/* 683 */     return FrontUtils.showMessage(request, model, "weChat.publicMchidorApenid.null");
/*     */   }
/*     */ 
/*     */   public static Map<String, String> weixinUniformOrder(String tradeType, String returnUrl, Website web, PaymentPlugins paymentPlugins, Order order, String wechatOppenId, String retainage, HttpServletRequest request)
/*     */   {
/* 692 */     Map paramMap = new HashMap();
/*     */ 
/* 694 */     paramMap.put("appid", paymentPlugins.getPartner().trim());
/*     */ 
/* 696 */     paramMap.put("mch_id", paymentPlugins.getSellerEmail().trim());
/*     */ 
/* 698 */     paramMap.put("device_info", "WEB");
/*     */ 
/* 700 */     paramMap.put("nonce_str", getWCRandomNumber(Integer.valueOf(10)));
/*     */ 
/* 702 */     paramMap.put("body", order.getProductName());
/*     */ 
/* 704 */     paramMap.put("out_trade_no", order.getCode().toString());
/*     */ 
/* 706 */     paramMap.put("fee_type", "CNY");
/*     */ 
/* 708 */     paramMap.put("total_fee", changeY2F(Double.valueOf(order.getTotal().doubleValue() + order.getFreight().doubleValue())));
/*     */ 
/* 710 */     paramMap.put("spbill_create_ip", RequestUtils.getIpAddr(request));
/*     */ 
/* 713 */     paramMap.put("notify_url", "http://" + web.getDomain() + 
/* 714 */       returnUrl);
/*     */ 
/* 717 */     paramMap.put("trade_type", tradeType);
/*     */ 
/* 719 */     if ((tradeType.equals("JSAPI")) && (wechatOppenId != null)) {
/* 720 */       paramMap.put("openid", wechatOppenId.trim());
/*     */     }
/*     */ 
/* 724 */     paramMap.put("product_id", order.getId().toString());
/*     */ 
/* 726 */     if (StringUtils.isNotBlank(paymentPlugins.getSellerKey()))
/*     */     {
/* 728 */       paramMap.put("sign", createSign(paramMap, paymentPlugins.getSellerKey().trim()));
/*     */     }
/*     */ 
/* 731 */     String xmlWeChat = getRequestXml(paramMap);
/* 732 */     String resXml = testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
/* 733 */     Map map = new HashMap();
/*     */     try {
/* 735 */       map = doXMLParse(resXml);
/*     */     } catch (Exception e) {
/* 737 */       e.printStackTrace();
/*     */     }
/* 739 */     return map;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/WeChatPublicPayReturn.jspx"})
/*     */   public void WeChatPublicPayReturn(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JDOMException, IOException, JSONException
/*     */   {
/* 749 */     String xml_receive_result = getXmlRequest(request);
/* 750 */     if ((StringUtils.isNotBlank(xml_receive_result)) || (xml_receive_result != "")) {
/* 751 */       Map result_map = doXMLParse(xml_receive_result);
/* 752 */       String sign_receive = (String)result_map.get("sign");
/* 753 */       result_map.remove("sign");
/* 754 */       String key = this.paymentPluginsMng.findByCode("weChatPublicPay").getSellerKey().trim();
/* 755 */       if (StringUtils.isNotBlank(key)) {
/* 756 */         String checkSign = createSign(result_map, key);
/* 757 */         if ((checkSign != null) && (checkSign.equals(sign_receive)))
/*     */         {
/*     */           try {
/* 760 */             noticeWeChatSuccess();
/* 761 */             if (result_map == null) return; String return_code = (String)result_map.get("return_code");
/* 763 */             if ((!"SUCCESS".equals(return_code)) || (!"SUCCESS".equals(result_map.get("result_code")))) return;
/* 765 */             String transaction_id = (String)result_map.get("transaction_id");
/*     */ 
/* 767 */             String out_trade_no = (String)result_map.get("out_trade_no");
/* 768 */             Order order = this.manager.findByCode(Long.valueOf(out_trade_no));
/* 769 */             order.setPaymentStatus(Integer.valueOf(2));
/* 770 */             order.setTradeNo(transaction_id);
/* 771 */             order.setPaymentCode("weChatPublicPay");
/* 772 */             this.manager.updateByUpdater(order);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 776 */             e.printStackTrace();
/*     */           }
/*     */         } else {
/* 779 */           Map parames = new HashMap();
/* 780 */           parames.put("return_code", "FAIL");
/* 781 */           parames.put("return_msg", "check.error");
/*     */ 
/* 783 */           String xmlWeChat = getRequestXml(parames);
/*     */           try {
/* 785 */             testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
/*     */           } catch (Exception e) {
/* 787 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String alipayMobile(String aliPayUrl, PaymentPlugins paymentPlugins, Website web, Order order, String deposit, String retainage, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 798 */     String aliPayUrl1 = "https://openapi.alipay.com/gateway.do";
/*     */ 
/* 802 */     AlipayClient alipayClient = getAlipayClient(aliPayUrl1, paymentPlugins.getPublicKey(), 
/* 803 */       paymentPlugins.getSellerKey(), paymentPlugins.getSellerEmail(), "UTF-8");
/* 804 */     AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
/*     */ 
/* 806 */     alipayRequest.setReturnUrl("http://" + web.getDomain() + "/shopMember/index.jspx");
/*     */ 
/* 808 */     alipayRequest.setNotifyUrl("http://" + web.getDomain() + "/alipayMobilePayReturn.jspx");
/* 809 */     String subject = null;
/* 810 */     String out_trade_no = null;
/* 811 */     Double total_amount = null;
/*     */ 
/* 813 */     subject = order.getProductName();
/* 814 */     out_trade_no = order.getCode().toString().trim();
/* 815 */     total_amount = order.getTotal();
/*     */ 
/* 817 */     alipayRequest.setBizContent("{    \"out_trade_no\":\"" + 
/* 819 */       out_trade_no + "\"," + 
/* 821 */       "    \"seller_id\":\"" + paymentPlugins.getPartner() + "\"," + 
/* 823 */       "    \"subject\":\"" + subject + "\"," + 
/* 825 */       "    \"total_amount\":" + total_amount + "," + 
/* 827 */       "    \"timeout_express\":\"90m\"" + 
/* 828 */       "  }");
/* 829 */     String form = null;
/*     */     try
/*     */     {
/* 832 */       form = ((AlipayTradeWapPayResponse)alipayClient.pageExecute(alipayRequest)).getBody();
/* 833 */       response.setContentType("text/html;charset=UTF-8");
/* 834 */       response.getWriter().write(form);
/* 835 */       response.getWriter().flush();
/*     */     } catch (Exception e) {
/* 837 */       e.printStackTrace();
/*     */     }
/* 839 */     return form;
/*     */   }
/*     */ 
/*     */   public static AlipayClient getAlipayClient(String aliPayUrl, String appId, String privateKey, String publicKey, String charset) {
/* 843 */     if (alipayClient == null) {
/* 844 */       alipayClient = new DefaultAlipayClient(aliPayUrl, appId, 
/* 845 */         privateKey, "json", charset, publicKey);
/*     */     }
/* 847 */     return alipayClient;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/alipayMobilePayReturn.jspx"})
/*     */   public void alipayMobileReturndirect(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws UnsupportedEncodingException, AlipayApiException
/*     */   {
/* 857 */     Map params = new HashMap();
/* 858 */     Map requestParams = request.getParameterMap();
/* 859 */     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
/* 860 */       String name = (String)iter.next();
/* 861 */       String[] values = (String[])requestParams.get(name);
/* 862 */       String valueStr = "";
/* 863 */       for (int i = 0; i < values.length; i++) {
/* 864 */         valueStr = valueStr + values[i] + ",";
/*     */       }
/* 866 */       params.put(name, valueStr);
/*     */     }
/*     */ 
/* 869 */     String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
/*     */ 
/* 871 */     String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
/*     */ 
/* 873 */     String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
/* 874 */     PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipayMobile");
/* 875 */     Order order = this.manager.findByCode(Long.valueOf(out_trade_no));
/* 876 */     boolean signVerified = AlipaySignature.rsaCheckV1(params, paymentPlugins.getSellerEmail(), "UTF-8");
/* 877 */     if (signVerified)
/* 878 */       if (trade_status.equals("TRADE_FINISHED"))
/*     */       {
/* 881 */         order.setStatus(Integer.valueOf(2));
/* 882 */         order.setTradeNo(trade_no);
/* 883 */         this.manager.updateByUpdater(order);
/* 884 */         ((PrintWriter)response).write("success");
/* 885 */       } else if (trade_status.equals("TRADE_SUCCESS"))
/*     */       {
/* 888 */         order.setStatus(Integer.valueOf(2));
/* 889 */         order.setTradeNo(trade_no);
/* 890 */         this.manager.updateByUpdater(order);
/*     */ 
/* 892 */         ((PrintWriter)response).write("success");
/*     */       }
/*     */   }
/*     */ 
/*     */   public String getLogisticsType(Order order)
/*     */   {
/*     */     String logistics_type;
/* 900 */     if (!StringUtils.isBlank(order.getShipping().getLogisticsType()))
/* 901 */       logistics_type = order.getShipping().getLogisticsType();
/*     */     else {
/* 903 */       logistics_type = "EXPRESS";
/*     */     }
/* 905 */     return logistics_type;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.AlipayAct
 * JD-Core Version:    0.6.0
 */