/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.jspgou.cms.entity.Cart;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.OrderReturn;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopScore;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.OrderReturnMng;
/*     */ import com.jspgou.cms.manager.PaymentMng;
/*     */ import com.jspgou.cms.manager.PaymentPluginsMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShipmentsMng;
/*     */ import com.jspgou.cms.manager.ShippingMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.ShopScoreMng;
/*     */ import com.jspgou.cms.service.ShoppingSvc;
/*     */ import com.jspgou.cms.web.FrontUtils;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.security.annotation.Secured;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import com.jspgou.core.web.front.URLHelper;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Secured
/*     */ @Controller
/*     */ public class OrderAct
/*     */ {
/*     */   private static final String MY_ORDER = "tpl.myOrder";
/*     */   private static final String MY_RETURN_ORDER = "tpl.myReturnOrder";
/*     */   private static final String MY_ORDER_VIEW = "tpl.myOrderView";
/*     */   private static final String SUCCESSFULLY_ORDER = "tpl.successfullyOrder";
/*     */   private static final String CHECKOUT_SHIPPING = "tpl.checkoutShipping";
/*     */   private static final String RETURN_ORDER_SHIP = "tpl.returnOrderShip";
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng shippingMng;
/*     */ 
/*     */   @Autowired
/*     */   private PaymentMng paymentMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShoppingSvc shoppingSvc;
/*     */ 
/*     */   @Autowired
/*     */   private PaymentPluginsMng paymentPluginsMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng productFashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopScoreMng shopScoreMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderReturnMng orderReturnMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShipmentsMng shipMentsMng;
/*     */ 
/*     */   @RequestMapping({"/order/myorder*.jspx"})
/*     */   public String myOrder(String productName, Integer status, Integer shippingStatus, Integer paymentStatus, String code, String userName, Long paymentId, Long shippingId, String startTime, String endTime, Double startOrderTotal, Double endOrderTotal, HttpServletRequest request, ModelMap model)
/*     */   {
/*  79 */     Website web = SiteUtils.getWeb(request);
/*  80 */     ShopMember member = MemberThread.get();
/*  81 */     if (member == null) {
/*  82 */       return "redirect:../login.jspx";
/*     */     }
/*  84 */     if (StringUtils.isBlank(userName)) {
/*  85 */       userName = null;
/*     */     }
/*  87 */     if (StringUtils.isBlank(startTime)) {
/*  88 */       startTime = null;
/*     */     }
/*  90 */     if (StringUtils.isBlank(endTime)) {
/*  91 */       endTime = null;
/*     */     }
/*  93 */     List shippingList = this.shippingMng.getList(web.getId(), true);
/*  94 */     List paymentList = this.paymentMng.getList(web.getId(), true);
/*  95 */     model.addAttribute("productName", productName);
/*  96 */     model.addAttribute("historyProductIds", getHistoryProductIds(request));
/*  97 */     model.addAttribute("paymentList", paymentList);
/*  98 */     model.addAttribute("shippingList", shippingList);
/*  99 */     model.addAttribute("status", status);
/* 100 */     model.addAttribute("code", code);
/* 101 */     model.addAttribute("userName", userName);
/* 102 */     model.addAttribute("startTime", startTime);
/* 103 */     model.addAttribute("endTime", endTime);
/* 104 */     model.addAttribute("paymentId", paymentId);
/* 105 */     model.addAttribute("shippingId", shippingId);
/* 106 */     model.addAttribute("startOrderTotal", startOrderTotal);
/* 107 */     model.addAttribute("endOrderTotal", endOrderTotal);
/* 108 */     model.addAttribute("shippingStatus", shippingStatus);
/* 109 */     model.addAttribute("paymentStatus", paymentStatus);
/* 110 */     Integer pageNo = Integer.valueOf(URLHelper.getPageNo(request));
/* 111 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 112 */     ShopFrontHelper.setDynamicPageData(request, model, web, "", "myorder", ".jspx", pageNo.intValue());
/* 113 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myOrder", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/myOrderView.jspx"})
/*     */   public String myOrderView(Long orderId, HttpServletRequest request, ModelMap model)
/*     */   {
/* 122 */     Website web = SiteUtils.getWeb(request);
/* 123 */     ShopMember member = MemberThread.get();
/* 124 */     if (member == null) {
/* 125 */       return "redirect:../login.jspx";
/*     */     }
/* 127 */     WebErrors errors = validateOrderView(orderId, member, request);
/* 128 */     if (errors.hasErrors()) {
/* 129 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 131 */     Order order = this.manager.findById(orderId);
/* 132 */     model.addAttribute("order", order);
/* 133 */     List shipments = this.shipMentsMng.getlist(orderId);
/* 134 */     model.addAttribute("shipments", shipments);
/* 135 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 136 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myOrderView", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/order_shipping.jspx"})
/*     */   public String orderShipping(Long deliveryInfo, Long shippingMethodId, Long paymentMethodId, Integer[] cartCountId, Long[] cartProductId, Long[] cartItemId, String comments, String memberCouponId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 146 */     Website web = SiteUtils.getWeb(request);
/* 147 */     ShopMember member = MemberThread.get();
/* 148 */     if (member == null) {
/* 149 */       return "redirect:../login.jspx";
/*     */     }
/* 151 */     String message = null;
/* 152 */     boolean createOrder = true;
/* 153 */     if (cartCountId != null) {
/* 154 */       for (Integer cId : cartCountId) {
/* 155 */         for (Long pId : cartProductId) {
/* 156 */           if (this.productMng.findById(pId) == null)
/*     */           {
/* 158 */             createOrder = false;
/* 159 */             ShopFrontHelper.setCommonData(request, model, web, 1);
/* 160 */             message = FrontUtils.showMessage(request, model, "含有商品已被删除的情况,不能提交订单，跳转回首页再进行正确的操作");
/*     */           } else {
/* 162 */             Product product = this.productMng.findById(pId);
/*     */ 
/* 165 */             Integer status = Integer.valueOf(product.getStatus() != null ? product.getStatus().intValue() : 0);
/* 166 */             Long productFashionId = null;
/* 167 */             if (product.getProductFashion() != null) {
/* 168 */               productFashionId = product.getProductFashion().getId();
/*     */             }
/* 170 */             if (productFashionId == null) {
/* 171 */               if (cId.intValue() > product.getStockCount().intValue())
/*     */               {
/* 173 */                 createOrder = false;
/* 174 */                 ShopFrontHelper.setCommonData(request, model, web, 1);
/* 175 */                 message = FrontUtils.showMessage(request, model, "商品库存不足，不能提交订单，跳转回首页再进行正确的操作"); } else {
/* 176 */                 if (status.intValue() == Product.ON_SALE_STATUS)
/*     */                   continue;
/* 178 */                 createOrder = false;
/* 179 */                 ShopFrontHelper.setCommonData(request, model, web, 1);
/* 180 */                 message = FrontUtils.showMessage(request, model, "商品已经下架,不能提交订单，跳转回首页再进行正确的操作");
/*     */               }
/*     */             }
/* 183 */             else if (this.productFashionMng.findById(productFashionId) == null)
/*     */             {
/* 185 */               createOrder = false;
/* 186 */               ShopFrontHelper.setCommonData(request, model, web, 1);
/* 187 */               message = FrontUtils.showMessage(request, model, "含有款式商品已被删除的情况,不能提交订单，跳转回首页再进行正确的操作");
/*     */             } else {
/* 189 */               ProductFashion productFashion = this.productFashionMng.findById(productFashionId);
/* 190 */               if (cId.intValue() > productFashion.getStockCount().intValue())
/*     */               {
/* 192 */                 createOrder = false;
/* 193 */                 ShopFrontHelper.setCommonData(request, model, web, 1);
/* 194 */                 message = FrontUtils.showMessage(request, model, "该款式库存不足,不能提交订单，跳转回首页再进行正确的操作"); } else {
/* 195 */                 if (status.intValue() == Product.ON_SALE_STATUS)
/*     */                   continue;
/* 197 */                 createOrder = false;
/* 198 */                 ShopFrontHelper.setCommonData(request, model, web, 1);
/* 199 */                 message = FrontUtils.showMessage(request, model, "该款式商品已经下架,不能提交订单，跳转回首页再进行正确的操作");
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 206 */       if (createOrder) {
/* 207 */         Order order = null;
/* 208 */         Cart cart = this.shoppingSvc.getCart(member.getId());
/* 209 */         if (cart != null) {
/* 210 */           order = this.manager.createOrder(cart, cartItemId, shippingMethodId, deliveryInfo, paymentMethodId, comments, request.getRemoteAddr(), member, web.getId(), memberCouponId);
/*     */         }
/* 212 */         this.shoppingSvc.clearCookie(request, response);
/* 213 */         List list = this.paymentPluginsMng.getList();
/* 214 */         model.addAttribute("list", list);
/* 215 */         model.addAttribute("order", order);
/* 216 */         ShopFrontHelper.setCommonData(request, model, web, 1);
/* 217 */         message = web.getTplSys("member", MessageResolver.getMessage(request, "tpl.successfullyOrder", new Object[0]), request);
/*     */       }
/*     */     } else {
/* 220 */       return "redirect:../cart/checkout_shipping.jspx";
/*     */     }
/* 222 */     return message;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/deleteOrder.jspx"})
/*     */   public void deleteOrder(Long orderId, HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 232 */     JSONObject json = new JSONObject();
/* 233 */     if (orderId != null) {
/* 234 */       Order order = this.manager.findById(orderId);
/* 235 */       order.getItems().clear();
/* 236 */       this.manager.deleteById(orderId);
/*     */     }
/* 238 */     json.put("success", true);
/* 239 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/abolishOrder.jspx"})
/*     */   public void abolishOrder(Long orderId, HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 248 */     JSONObject json = new JSONObject();
/* 249 */     ShopMember member = MemberThread.get();
/* 250 */     if (orderId != null) {
/* 251 */       Order order = this.manager.findById(orderId);
/* 252 */       order.setStatus(Integer.valueOf(3));
/* 253 */       Set set = order.getItems();
/*     */       Product product;
/* 255 */       for (OrderItem item : set) {
/* 256 */         product = item.getProduct();
/* 257 */         if (item.getProductFash() != null) {
/* 258 */           ProductFashion fashion = item.getProductFash();
/* 259 */           fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
/* 260 */           product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/* 261 */           this.productFashionMng.update(fashion);
/*     */         } else {
/* 263 */           product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/*     */         }
/* 265 */         this.productMng.updateByUpdater(product);
/*     */       }
/*     */ 
/* 268 */       member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
/* 269 */       this.shopMemberMng.update(member);
/* 270 */       List list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
/* 271 */       for (ShopScore s : list) {
/* 272 */         this.shopScoreMng.deleteById(s.getId());
/*     */       }
/* 274 */       this.manager.updateByUpdater(order);
/*     */     }
/* 276 */     json.put("success", true);
/* 277 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/accomplishOrder.jspx"})
/*     */   public void accomplishOrder(Long orderId, HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 287 */     JSONObject json = new JSONObject();
/* 288 */     ShopMember member = MemberThread.get();
/* 289 */     if (orderId != null) {
/* 290 */       Order order = this.manager.findById(orderId);
/* 291 */       order.setStatus(Integer.valueOf(4));
/*     */ 
/* 293 */       member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
/* 294 */       member.setScore(Integer.valueOf(member.getScore().intValue() + order.getScore().intValue()));
/* 295 */       this.shopMemberMng.update(member);
/* 296 */       List list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
/* 297 */       for (ShopScore s : list) {
/* 298 */         s.setStatus(true);
/* 299 */         this.shopScoreMng.update(s);
/*     */       }
/* 301 */       this.manager.updateliRun(orderId);
/* 302 */       this.manager.updateByUpdater(order);
/*     */     }
/* 304 */     json.put("success", true);
/* 305 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/order_payAgain.jspx"})
/*     */   public String payOrderAgain(Long orderId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 313 */     Website web = SiteUtils.getWeb(request);
/* 314 */     ShopMember member = MemberThread.get();
/* 315 */     if (member == null) {
/* 316 */       return "redirect:../login.jspx";
/*     */     }
/* 318 */     WebErrors errors = validateOrderView(orderId, member, request);
/* 319 */     if (errors.hasErrors()) {
/* 320 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 322 */     this.shoppingSvc.clearCookie(request, response);
/* 323 */     Order order = this.manager.findById(orderId);
/* 324 */     List list = this.paymentPluginsMng.getList();
/* 325 */     model.addAttribute("list", list);
/* 326 */     model.addAttribute("order", order);
/* 327 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 328 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.successfullyOrder", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/myReturnOrder*.jspx"})
/*     */   public String myReturnOrder(HttpServletRequest request, ModelMap model)
/*     */   {
/* 336 */     Website web = SiteUtils.getWeb(request);
/* 337 */     ShopMember member = MemberThread.get();
/* 338 */     if (member == null) {
/* 339 */       return "redirect:../login.jspx";
/*     */     }
/*     */ 
/* 342 */     Integer pageNo = Integer.valueOf(URLHelper.getPageNo(request));
/* 343 */     Pagination pagination = this.manager.getPageForOrderReturn(member.getId(), pageNo.intValue(), 10);
/* 344 */     model.addAttribute("pagination", pagination);
/* 345 */     model.addAttribute("historyProductIds", getHistoryProductIds(request));
/* 346 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 347 */     ShopFrontHelper.setDynamicPageData(request, model, web, "", "myReturnOrder", ".jspx", pageNo.intValue());
/* 348 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.myReturnOrder", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/shipments.jspx"})
/*     */   public String shipments(Long id, HttpServletRequest request, ModelMap model)
/*     */   {
/* 357 */     Website web = SiteUtils.getWeb(request);
/* 358 */     ShopMember member = MemberThread.get();
/* 359 */     if (member == null) {
/* 360 */       return "redirect:../login.jspx";
/*     */     }
/* 362 */     WebErrors errors = validateOrderReturnView(id, member, request);
/* 363 */     if (errors.hasErrors()) {
/* 364 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 366 */     OrderReturn orderReturn = this.orderReturnMng.findById(id);
/* 367 */     if (orderReturn.getStatus().intValue() == 4) {
/* 368 */       errors.addError("请勿重复给卖家发货");
/* 369 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/*     */ 
/* 372 */     List shipments = this.shipMentsMng.getlist(orderReturn.getOrder().getId());
/* 373 */     model.addAttribute("shipments", shipments);
/* 374 */     model.addAttribute("orderReturn", orderReturn);
/* 375 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 376 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.returnOrderShip", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/shipmentsSend.jspx"})
/*     */   public void shipmentsSend(Long id, String shippingLogistics, String invoiceNo, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/* 385 */     ShopMember member = MemberThread.get();
/* 386 */     JSONObject json = new JSONObject();
/* 387 */     if (member == null) {
/* 388 */       json.put("status", 1);
/*     */     }
/* 390 */     OrderReturn bean = this.orderReturnMng.findById(id);
/* 391 */     if (StringUtils.isBlank(invoiceNo)) {
/* 392 */       json.put("status", 4);
/*     */     }
/* 394 */     bean.setInvoiceNo(invoiceNo);
/* 395 */     bean.setShippingLogistics(shippingLogistics);
/*     */ 
/* 397 */     if ((bean.getReturnType().intValue() == 1) && (bean.getStatus().intValue() == 2)) {
/* 398 */       bean.setStatus(Integer.valueOf(4));
/* 399 */       this.orderReturnMng.update(bean);
/* 400 */       json.put("status", 0);
/* 401 */     } else if (bean.getStatus().intValue() == 4) {
/* 402 */       json.put("status", 3);
/*     */     }
/*     */ 
/* 405 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/accomplish.jspx"})
/*     */   public String accomplish(Long id, HttpServletRequest request, ModelMap model)
/*     */   {
/* 418 */     Website web = SiteUtils.getWeb(request);
/* 419 */     ShopMember member = MemberThread.get();
/* 420 */     if (member == null) {
/* 421 */       return "redirect:../login.jspx";
/*     */     }
/* 423 */     WebErrors errors = validateOrderReturnView(id, member, request);
/* 424 */     if (errors.hasErrors()) {
/* 425 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 427 */     OrderReturn entity = this.orderReturnMng.findById(id);
/* 428 */     entity.setStatus(Integer.valueOf(7));
/* 429 */     Set set = entity.getOrder().getItems();
/*     */     Product product;
/* 431 */     for (OrderItem item : set) {
/* 432 */       product = item.getProduct();
/* 433 */       if (item.getProductFash() != null) {
/* 434 */         ProductFashion fashion = item.getProductFash();
/* 435 */         fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
/* 436 */         product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/* 437 */         this.productFashionMng.update(fashion);
/*     */       } else {
/* 439 */         product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/*     */       }
/* 441 */       this.productMng.updateByUpdater(product);
/*     */     }
/*     */ 
/* 444 */     member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - entity.getOrder().getScore().intValue()));
/* 445 */     this.shopMemberMng.update(member);
/* 446 */     List list = this.shopScoreMng.getlist(Long.toString(entity.getOrder().getCode().longValue()));
/* 447 */     for (ShopScore s : list) {
/* 448 */       this.shopScoreMng.deleteById(s.getId());
/*     */     }
/* 450 */     this.orderReturnMng.update(entity);
/* 451 */     return myReturnOrder(request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateOrderView(Long orderId, ShopMember member, HttpServletRequest request)
/*     */   {
/* 456 */     WebErrors errors = WebErrors.create(request);
/* 457 */     if (errors.ifNull(orderId, "orderId")) {
/* 458 */       return errors;
/*     */     }
/* 460 */     Order order = this.manager.findById(orderId);
/* 461 */     if (errors.ifNotExist(order, Order.class, orderId)) {
/* 462 */       return errors;
/*     */     }
/* 464 */     if (!order.getMember().getId().equals(member.getId())) {
/* 465 */       errors.noPermission(Order.class, orderId);
/* 466 */       return errors;
/*     */     }
/* 468 */     return errors;
/*     */   }
/*     */ 
/*     */   public String getHistoryProductIds(HttpServletRequest request)
/*     */   {
/* 473 */     String str = null;
/* 474 */     Cookie[] cookie = request.getCookies();
/* 475 */     int num = cookie.length;
/* 476 */     for (int i = 0; i < num; i++) {
/* 477 */       if (cookie[i].getName().equals("shop_record")) {
/* 478 */         str = cookie[i].getValue();
/* 479 */         break;
/*     */       }
/*     */     }
/* 482 */     return str;
/*     */   }
/*     */ 
/*     */   private WebErrors validateOrderReturnView(Long orderReturnId, ShopMember member, HttpServletRequest request)
/*     */   {
/* 487 */     WebErrors errors = WebErrors.create(request);
/* 488 */     if (errors.ifNull(orderReturnId, "orderReturnId")) {
/* 489 */       return errors;
/*     */     }
/* 491 */     OrderReturn orderReturn = this.orderReturnMng.findById(orderReturnId);
/* 492 */     if (errors.ifNotExist(orderReturn, OrderReturn.class, orderReturnId)) {
/* 493 */       return errors;
/*     */     }
/* 495 */     if (!orderReturn.getOrder().getMember().getId().equals(member.getId())) {
/* 496 */       errors.noPermission(OrderReturn.class, orderReturnId);
/* 497 */       return errors;
/*     */     }
/* 499 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.OrderAct
 * JD-Core Version:    0.6.0
 */