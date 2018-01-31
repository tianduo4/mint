/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Gathering;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.OrderReturn;
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopDictionary;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopScore;
/*     */ import com.jspgou.cms.entity.ShopShipments;
/*     */ import com.jspgou.cms.manager.GatheringMng;
/*     */ import com.jspgou.cms.manager.OrderItemMng;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.OrderReturnMng;
/*     */ import com.jspgou.cms.manager.PaymentMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShippingMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.ShopScoreMng;
/*     */ import com.jspgou.cms.manager.ShopShipmentsMng;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.commons.lang.StringUtils;
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
/*     */ public class OrderAct
/*     */ {
/*  70 */   private static final Logger log = LoggerFactory.getLogger(OrderAct.class);
/*     */   public static final String ALL = "all";
/*     */   public static final String PENDING = "pending";
/*     */   public static final String PROSESSING = "processing";
/*     */   public static final String DELIVERED = "delivered";
/*     */   public static final String COMPLETE = "complete";
/*  76 */   public static final String[] TYPES = { "all", "pending", "processing", "delivered", 
/*  77 */     "complete" };
/*     */   private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng shippingMng;
/*     */ 
/*     */   @Autowired
/*     */   private PaymentMng paymentMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private OrderReturnMng orderReturnMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopScoreMng shopScoreMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng productFashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderItemMng orderItemMng;
/*     */ 
/*     */   @Autowired
/*     */   private GatheringMng gatheringMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopShipmentsMng shopShipmentsMng;
/*     */ 
/*     */   @RequestMapping({"/order/v_list.do"})
/*     */   public String list(Long code, Integer status, Integer paymentStatus, Integer shippingStatus, Long paymentId, Long shoppingId, Date startTime, Date endTime, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  83 */     Website web = SiteUtils.getWeb(request);
/*  84 */     String userName = RequestUtils.getQueryParam(request, "userName");
/*  85 */     userName = StringUtils.trim(userName);
/*  86 */     Pagination pagination = this.manager.getPage(web.getId(), null, null, userName, 
/*  87 */       paymentId, shoppingId, startTime, endTime, null, null, status, paymentStatus, shippingStatus, code, 
/*  88 */       SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  89 */     model.addAttribute("pagination", pagination);
/*     */ 
/*  91 */     List shippingList = this.shippingMng.getList(web.getId(), true);
/*  92 */     List paymentList = this.paymentMng.getList(web.getId(), true);
/*  93 */     model.addAttribute("paymentList", paymentList);
/*  94 */     model.addAttribute("shippingList", shippingList);
/*  95 */     model.addAttribute("paymentId", paymentId);
/*  96 */     model.addAttribute("shoppingId", shoppingId);
/*  97 */     model.addAttribute("userName", userName);
/*  98 */     model.addAttribute("startTime", startTime);
/*  99 */     model.addAttribute("endTime", endTime);
/* 100 */     model.addAttribute("status", status);
/* 101 */     model.addAttribute("paymentStatus", paymentStatus);
/* 102 */     model.addAttribute("shippingStatus", shippingStatus);
/* 103 */     return "order/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/v_view.do"})
/*     */   public String view(Long id, HttpServletRequest request, ModelMap model) {
/* 109 */     Website web = SiteUtils.getWeb(request);
/* 110 */     WebErrors errors = validateEdit(id, request);
/* 111 */     if (errors.hasErrors()) {
/* 112 */       return errors.showErrorPage(model);
/*     */     }
/* 114 */     model.addAttribute("order", this.manager.findById(id));
/* 115 */     return "order/view";
/*     */   }
/*     */   @RequestMapping({"/order/o_affirm.do"})
/*     */   public String affirm(Long id, HttpServletRequest request, ModelMap model) {
/* 120 */     Website web = SiteUtils.getWeb(request);
/* 121 */     WebErrors errors = validateEdit(id, request);
/* 122 */     if (errors.hasErrors()) {
/* 123 */       return errors.showErrorPage(model);
/*     */     }
/* 125 */     Order order = this.manager.findById(id);
/* 126 */     if (order.getStatus().intValue() == 1) {
/* 127 */       order.setStatus(Integer.valueOf(2));
/* 128 */       this.manager.updateByUpdater(order);
/*     */     }
/* 130 */     model.addAttribute("order", order);
/* 131 */     return "order/view";
/*     */   }
/*     */   @RequestMapping({"/order/o_abolish.do"})
/*     */   public String abolish(Long id, HttpServletRequest request, ModelMap model) {
/* 136 */     Website web = SiteUtils.getWeb(request);
/* 137 */     WebErrors errors = validateEdit(id, request);
/* 138 */     if (errors.hasErrors()) {
/* 139 */       return errors.showErrorPage(model);
/*     */     }
/* 141 */     Order order = this.manager.findById(id);
/* 142 */     if ((order.getStatus().intValue() != 4) && (order.getShippingStatus().intValue() != 2) && (order.getPaymentStatus().intValue() != 2)) {
/* 143 */       order.setStatus(Integer.valueOf(3));
/*     */       ProductFashion fashion;
/* 145 */       for (OrderItem item : order.getItems()) {
/* 146 */         Product product = item.getProduct();
/* 147 */         if (item.getProductFash() != null) {
/* 148 */           fashion = item.getProductFash();
/* 149 */           fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
/* 150 */           product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/* 151 */           this.productFashionMng.update(fashion);
/*     */         } else {
/* 153 */           product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/*     */         }
/* 155 */         this.productMng.updateByUpdater(product);
/*     */       }
/*     */ 
/* 158 */       ShopMember member = order.getMember();
/* 159 */       member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
/* 160 */       this.shopMemberMng.update(member);
/* 161 */       List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
/* 162 */       for (ShopScore s : list) {
/* 163 */         this.shopScoreMng.deleteById(s.getId());
/*     */       }
/* 165 */       this.manager.updateByUpdater(order);
/*     */     }
/* 167 */     model.addAttribute("order", order);
/* 168 */     return "order/view";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/v_payment.do"})
/*     */   public String zhifu(Long id, HttpServletRequest request, ModelMap model) {
/* 174 */     Website web = SiteUtils.getWeb(request);
/* 175 */     WebErrors errors = validateEdit(id, request);
/* 176 */     if (errors.hasErrors()) {
/* 177 */       return errors.showErrorPage(model);
/*     */     }
/* 179 */     Order order = this.manager.findById(id);
/* 180 */     model.addAttribute("order", order);
/* 181 */     return "order/payment";
/*     */   }
/*     */   @RequestMapping({"/order/v_shipments.do"})
/*     */   public String shipmentses(Long id, HttpServletRequest request, ModelMap model) {
/* 186 */     Website web = SiteUtils.getWeb(request);
/* 187 */     WebErrors errors = validateEdit(id, request);
/* 188 */     if (errors.hasErrors()) {
/* 189 */       return errors.showErrorPage(model);
/*     */     }
/* 191 */     Order order = this.manager.findById(id);
/* 192 */     Pagination pagination = this.shopShipmentsMng.getPage(1, 10);
/* 193 */     model.addAttribute("allList", pagination.getList());
/* 194 */     List<ShopShipments> list = this.shopShipmentsMng.getList(Boolean.valueOf(true));
/* 195 */     if ((list != null) && (list.size() > 0)) {
/* 196 */       for (ShopShipments li : list) {
/* 197 */         model.addAttribute("mentId", li.getId());
/*     */       }
/*     */     }
/*     */ 
/* 201 */     model.addAttribute("order", order);
/* 202 */     return "order/shipments";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/o_payment.do"})
/*     */   public String payment(Gathering bean, Long id, HttpServletRequest request, ModelMap model)
/*     */   {
/* 209 */     Website web = SiteUtils.getWeb(request);
/* 210 */     WebErrors errors = validateEdit(id, request);
/* 211 */     if (errors.hasErrors()) {
/* 212 */       return errors.showErrorPage(model);
/*     */     }
/* 214 */     Order order = this.manager.findById(id);
/* 215 */     ShopAdmin admin = AdminThread.get();
/* 216 */     bean.setShopAdmin(admin);
/* 217 */     bean.setIndent(order);
/* 218 */     if ((order.getPaymentStatus().intValue() == 1) && (order.getPayment().getType().byteValue() == 2)) {
/* 219 */       this.gatheringMng.save(bean);
/* 220 */       order.setPaymentStatus(Integer.valueOf(2));
/* 221 */       this.manager.updateByUpdater(order);
/*     */     }
/* 223 */     model.addAttribute("order", order);
/* 224 */     return "order/view";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/o_accomplish.do"})
/*     */   public String accomplish(Long id, HttpServletRequest request, ModelMap model)
/*     */   {
/* 232 */     WebErrors errors = validateEdit(id, request);
/* 233 */     if (errors.hasErrors()) {
/* 234 */       return errors.showErrorPage(model);
/*     */     }
/* 236 */     Order order = this.manager.findById(id);
/* 237 */     if ((order.getShippingStatus().intValue() == 2) && (order.getStatus().intValue() == 2) && (order.getPaymentStatus().intValue() == 2)) {
/* 238 */       order.setStatus(Integer.valueOf(4));
/*     */ 
/* 240 */       ShopMember member = order.getMember();
/* 241 */       member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
/* 242 */       member.setScore(Integer.valueOf(member.getScore().intValue() + order.getScore().intValue()));
/* 243 */       this.shopMemberMng.update(member);
/* 244 */       List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
/* 245 */       for (ShopScore s : list) {
/* 246 */         s.setStatus(true);
/* 247 */         this.shopScoreMng.update(s);
/*     */       }
/* 249 */       this.manager.updateByUpdater(order);
/* 250 */       this.manager.updateliRun(id);
/*     */     }
/* 252 */     model.addAttribute("order", order);
/* 253 */     return "order/view";
/*     */   }
/*     */   @RequestMapping({"/order/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/* 258 */     Website web = SiteUtils.getWeb(request);
/* 259 */     WebErrors errors = validateEdit(id, request);
/* 260 */     if (errors.hasErrors()) {
/* 261 */       return errors.showErrorPage(model);
/*     */     }
/* 263 */     List shippingList = this.shippingMng.getList(web.getId(), true);
/* 264 */     List paymentList = this.paymentMng.getList(web.getId(), true);
/* 265 */     model.addAttribute("order", this.manager.findById(id));
/* 266 */     model.addAttribute("paymentList", paymentList);
/* 267 */     model.addAttribute("shippingList", shippingList);
/* 268 */     model.addAttribute("orderReturn", this.orderReturnMng.findByOrderId(id));
/* 269 */     return "order/edit";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/o_update.do"})
/*     */   public String update(Long id, String comments, Long shippingId, Long paymentId, Long[] itemId, Integer[] itemCount, Double[] itemPrice, Double totalPrice, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 276 */     WebErrors errors = validateUpdate(id, shippingId, itemId, itemCount, itemPrice, request);
/* 277 */     if (errors.hasErrors()) {
/* 278 */       return errors.showErrorPage(model);
/*     */     }
/* 280 */     Order order = this.manager.findById(id);
/* 281 */     int score = 0;
/* 282 */     int weight = 0;
/* 283 */     double price = 0.0D;
/* 284 */     for (int i = 0; i < itemId.length; i++) {
/* 285 */       OrderItem orderItem = this.orderItemMng.findById(itemId[i]);
/* 286 */       Product product = orderItem.getProduct();
/* 287 */       product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + orderItem.getCount().intValue() - itemCount[i].intValue()));
/* 288 */       if (orderItem.getProductFash() != null) {
/* 289 */         ProductFashion productFash = orderItem.getProductFash();
/* 290 */         productFash.setStockCount(Integer.valueOf(productFash.getStockCount().intValue() + orderItem.getCount().intValue() - itemCount[i].intValue()));
/* 291 */         this.productFashionMng.update(productFash);
/*     */       }
/* 293 */       orderItem.setCount(itemCount[i]);
/* 294 */       orderItem.setSalePrice(itemPrice[i]);
/* 295 */       score += itemCount[i].intValue() * orderItem.getProduct().getScore().intValue();
/* 296 */       weight += orderItem.getProduct().getWeight().intValue() * itemCount[i].intValue();
/* 297 */       if (orderItem.getProductFash() != null)
/* 298 */         price += itemPrice[i].doubleValue() * itemCount[i].intValue();
/*     */       else {
/* 300 */         price += itemPrice[i].doubleValue() * itemCount[i].intValue();
/*     */       }
/* 302 */       this.orderItemMng.updateByUpdater(orderItem);
/* 303 */       this.productMng.updateByUpdater(product);
/*     */     }
/* 305 */     order.setScore(Integer.valueOf(score));
/* 306 */     order.setWeight(Double.valueOf(weight));
/* 307 */     order.setProductPrice(Double.valueOf(price));
/* 308 */     double freight = this.shippingMng.findById(shippingId).calPrice(Double.valueOf(weight)).doubleValue();
/* 309 */     order.setFreight(Double.valueOf(freight));
/* 310 */     order.setTotal(Double.valueOf(freight + price));
/* 311 */     order.setComments(comments);
/* 312 */     order.setShipping(this.shippingMng.findById(shippingId));
/* 313 */     order.setPayment(this.paymentMng.findById(paymentId));
/* 314 */     order.setLastModified(new Timestamp(System.currentTimeMillis()));
/* 315 */     this.manager.updateByUpdater(order);
/* 316 */     log.info("update Order, id={}.", order.getId());
/* 317 */     return list(null, null, null, null, null, null, null, null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/o_returnMoney.do"})
/*     */   public void returnMoney(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 323 */     Website web = SiteUtils.getWeb(request);
/* 324 */     OrderReturn orderReturn = this.orderReturnMng.findByOrderId(id);
/* 325 */     if (orderReturn.getPayType().intValue() == 2) {
/* 326 */       Payment pay = this.paymentMng.findById(Long.valueOf(3L));
/* 327 */       PrintWriter out = null;
/*     */       try {
/* 329 */         String aliURL = alipayReturn(pay, web, orderReturn, request, model);
/* 330 */         response.setContentType("text/html;charset=UTF-8");
/* 331 */         out = response.getWriter();
/* 332 */         out.print(aliURL);
/*     */       } catch (Exception localException) {
/*     */       } finally {
/* 335 */         out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String alipayReturn(Payment pay, Website web, OrderReturn orderReturn, HttpServletRequest request, ModelMap model)
/*     */   {
/* 344 */     Date date = new Date();
/* 345 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/* 346 */     SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 347 */     String date1 = sdf.format(date);
/*     */ 
/* 350 */     String batch_no = date1.concat(String.valueOf(orderReturn.getId().longValue() * 100L));
/*     */ 
/* 353 */     String refund_date = sdf1.format(date);
/*     */ 
/* 356 */     String batch_num = String.valueOf(1);
/*     */ 
/* 359 */     String detail_data = orderReturn.getOrder().getId().toString() + "^" + 1.0D + "^" + orderReturn.getShopDictionary().getName();
/*     */ 
/* 377 */     String notify_url = "http://" + web.getDomain() + "/cart/aliReturn.jspx";
/*     */ 
/* 380 */     Map sParaTemp = new HashMap();
/*     */ 
/* 384 */     sParaTemp.put("batch_no", batch_no);
/* 385 */     sParaTemp.put("refund_date", refund_date);
/* 386 */     sParaTemp.put("batch_num", batch_num);
/* 387 */     sParaTemp.put("detail_data", detail_data);
/* 388 */     sParaTemp.put("notify_url", notify_url);
/*     */ 
/* 390 */     String sHtmlText = null;
/*     */     try {
/* 392 */       sHtmlText = refund_fastpay_by_platform_pwd(sParaTemp);
/*     */     } catch (Exception e) {
/* 394 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 397 */     return sHtmlText;
/*     */   }
/*     */ 
/*     */   public static String refund_fastpay_by_platform_pwd(Map<String, String> sParaTemp)
/*     */     throws Exception
/*     */   {
/* 414 */     sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
/* 415 */     sParaTemp.put("_input_charset", "UTF-8");
/* 416 */     String strButtonName = "退款";
/* 417 */     return buildForm(sParaTemp, "https://mapi.alipay.com/gateway.do?", "get", strButtonName);
/*     */   }
/*     */ 
/*     */   public static String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod, String strButtonName)
/*     */   {
/* 431 */     Map sPara = buildRequestPara(sParaTemp);
/* 432 */     List keys = new ArrayList(sPara.keySet());
/*     */ 
/* 434 */     StringBuffer sbHtml = new StringBuffer();
/*     */ 
/* 436 */     sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + gateway + 
/* 437 */       "_input_charset=" + "UTF-8" + "\" method=\"" + strMethod + 
/* 438 */       "\">");
/*     */ 
/* 440 */     for (int i = 0; i < keys.size(); i++) {
/* 441 */       String name = (String)keys.get(i);
/* 442 */       String value = (String)sPara.get(name);
/*     */ 
/* 444 */       sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
/*     */     }
/*     */ 
/* 448 */     sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
/* 449 */     sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
/*     */ 
/* 451 */     return sbHtml.toString();
/*     */   }
/*     */ 
/*     */   private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp)
/*     */   {
/* 462 */     Map sPara = paraFilter(sParaTemp);
/*     */ 
/* 464 */     String mysign = buildMysign(sPara);
/*     */ 
/* 467 */     sPara.put("sign", mysign);
/* 468 */     sPara.put("sign_type", "MD5");
/*     */ 
/* 470 */     return sPara;
/*     */   }
/*     */ 
/*     */   public static String buildMysign(Map<String, String> sArray) {
/* 474 */     String prestr = createLinkString(sArray);
/* 475 */     prestr = prestr + (String)sArray.get("key");
/* 476 */     String mysign = md5(prestr);
/* 477 */     return mysign;
/*     */   }
/*     */ 
/*     */   public static String md5(String text)
/*     */   {
/* 482 */     return DigestUtils.md5Hex(getContentBytes(text, "UTF-8"));
/*     */   }
/*     */ 
/*     */   private static byte[] getContentBytes(String content, String charset)
/*     */   {
/* 488 */     if ((charset == null) || ("".equals(charset))) {
/* 489 */       return content.getBytes();
/*     */     }
/*     */     try
/*     */     {
/* 493 */       return content.getBytes(charset); } catch (UnsupportedEncodingException e) {
/*     */     }
/* 495 */     throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
/*     */   }
/*     */ 
/*     */   public static String createLinkString(Map<String, String> params)
/*     */   {
/* 505 */     List keys = new ArrayList(params.keySet());
/* 506 */     Collections.sort(keys);
/*     */ 
/* 508 */     String prestr = "";
/*     */ 
/* 510 */     for (int i = 0; i < keys.size(); i++) {
/* 511 */       String key = (String)keys.get(i);
/* 512 */       String value = (String)params.get(key);
/*     */ 
/* 514 */       if (i == keys.size() - 1)
/* 515 */         prestr = prestr + key + "=" + value;
/*     */       else {
/* 517 */         prestr = prestr + key + "=" + value + "&";
/*     */       }
/*     */     }
/*     */ 
/* 521 */     return prestr;
/*     */   }
/*     */ 
/*     */   public static Map<String, String> paraFilter(Map<String, String> sArray)
/*     */   {
/* 532 */     Map result = new HashMap();
/*     */ 
/* 534 */     if ((sArray == null) || (sArray.size() <= 0)) {
/* 535 */       return result;
/*     */     }
/*     */ 
/* 538 */     for (String key : sArray.keySet()) {
/* 539 */       String value = (String)sArray.get(key);
/* 540 */       if ((value == null) || (value.equals("")) || (key.equalsIgnoreCase("sign")) || 
/* 541 */         (key.equalsIgnoreCase("sign_type"))) {
/*     */         continue;
/*     */       }
/* 544 */       result.put(key, value);
/*     */     }
/*     */ 
/* 547 */     return result;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/ajaxtotalDeliveryFee.do"})
/*     */   public void ajaxtotalDeliveryFee(Long deliveryMethod, Double weight, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/* 556 */     ShopMember member = MemberThread.get();
/* 557 */     JSONObject json = new JSONObject();
/* 558 */     if (member == null) {
/* 559 */       json.put("status", 0);
/*     */     }
/* 561 */     Shipping shipping = this.shippingMng.findById(deliveryMethod);
/*     */ 
/* 564 */     Double freight = shipping.calPrice(weight);
/* 565 */     json.put("status", 1);
/* 566 */     json.put("freight", freight);
/*     */ 
/* 568 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   private Integer findItem(Long[] itemIds, Long itemId) {
/* 572 */     for (int i = 0; i < itemIds.length; i++) {
/* 573 */       if (itemIds[i].equals(itemId)) {
/* 574 */         return Integer.valueOf(i);
/*     */       }
/*     */     }
/* 577 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/o_delete.do"})
/*     */   public String delete(Long[] ids, String type, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 583 */     WebErrors errors = validateDelete(ids, request);
/* 584 */     if (errors.hasErrors()) {
/* 585 */       return errors.showErrorPage(model);
/*     */     }
/* 587 */     Order[] beans = this.manager.deleteByIds(ids);
/* 588 */     for (Order bean : beans) {
/* 589 */       log.info("delete Order, id={}", bean.getId());
/*     */     }
/* 591 */     return list(null, null, null, null, null, null, null, null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 595 */     WebErrors errors = WebErrors.create(request);
/* 596 */     Website web = SiteUtils.getWeb(request);
/* 597 */     if (vldExist(id, web.getId(), errors)) {
/* 598 */       return errors;
/*     */     }
/* 600 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, Long shippingId, Long[] itemId, Integer[] itemCount, Double[] itemPrice, HttpServletRequest request)
/*     */   {
/* 607 */     WebErrors errors = WebErrors.create(request);
/* 608 */     Website web = SiteUtils.getWeb(request);
/* 609 */     if (vldExist(id, web.getId(), errors)) {
/* 610 */       return errors;
/*     */     }
/* 612 */     if ((itemId == null) || (itemCount == null) || (itemPrice == null) || 
/* 613 */       (itemId.length == 0) || (itemId.length != itemCount.length) || 
/* 614 */       (itemCount.length != itemPrice.length)) {
/* 615 */       errors.addErrorString("order item invalid!");
/* 616 */       return errors;
/*     */     }
/* 618 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 622 */     WebErrors errors = WebErrors.create(request);
/* 623 */     Website web = SiteUtils.getWeb(request);
/* 624 */     errors.ifEmpty(ids, "ids");
/* 625 */     for (Long id : ids) {
/* 626 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 628 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 632 */     if (errors.ifNull(id, "id")) {
/* 633 */       return true;
/*     */     }
/* 635 */     Order entity = this.manager.findById(id);
/* 636 */     if (errors.ifNotExist(entity, Order.class, id)) {
/* 637 */       return true;
/*     */     }
/* 639 */     if (!entity.getWebsite().getId().equals(webId)) {
/* 640 */       errors.notInWeb(Order.class, id);
/* 641 */       return true;
/*     */     }
/* 643 */     if (entity.getReturnOrder() != null) {
/* 644 */       errors.notInReturn(entity.getReturnOrder(), Order.class, id);
/* 645 */       return true;
/*     */     }
/* 647 */     return false;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.OrderAct
 * JD-Core Version:    0.6.0
 */