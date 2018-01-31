/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.dao.OrderDao;
/*     */ import com.jspgou.cms.entity.Gathering;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.Shipments;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopScore;
/*     */ import com.jspgou.cms.entity.ShopShipments;
/*     */ import com.jspgou.cms.manager.OrderItemMng;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShipmentsMng;
/*     */ import com.jspgou.cms.manager.ShippingMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.ShopScoreMng;
/*     */ import com.jspgou.cms.manager.ShopShipmentsMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class OrderController
/*     */ {
/*  55 */   private static final Logger log = LoggerFactory.getLogger(OrderController.class);
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderDao orderDao;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopScoreMng shopScoreMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopShipmentsMng shopShipmentsMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderItemMng orderItemMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng productFashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng shippingMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShipmentsMng shipmentMng;
/*     */ 
/*  78 */   @RequestMapping({"/order/list"})
/*     */   public void orderList(Long code1, String userName, Integer status, Integer paymentStatus, Integer shippingStatus, Long paymentId, Long shoppingId, Date startTime, Date endTime, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) { String body = "\"\"";
/*  79 */     String message = "\"\"";
/*  80 */     int code = 200;
/*     */     try {
/*  82 */       if (pageNo == null) {
/*  83 */         pageNo = Integer.valueOf(1);
/*     */       }
/*  85 */       if (pageSize == null) {
/*  86 */         pageSize = Integer.valueOf(10);
/*     */       }
/*  88 */       Website web = SiteUtils.getWeb(request);
/*  89 */       Pagination page = this.orderDao.getPage1(web.getId(), null, null, 
/*  90 */         userName, paymentId, shoppingId, startTime, endTime, null, 
/*  91 */         null, status, paymentStatus, shippingStatus, code1, pageNo.intValue(), 
/*  92 */         pageSize.intValue());
/*     */ 
/*  94 */       List list = page.getList();
/*  95 */       int totalCount = page.getTotalCount();
/*  96 */       JSONArray jsonArray = new JSONArray();
/*  97 */       if ((list != null) && (list.size() > 0)) {
/*  98 */         for (int i = 0; i < list.size(); i++) {
/*  99 */           jsonArray.put(i, ((Order)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/* 102 */       message = "\"success\"";
/* 103 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     } catch (Exception e) {
/* 105 */       e.printStackTrace();
/* 106 */       code = 100;
/* 107 */       message = "\"system exception\"";
/*     */     }
/* 109 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 110 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/view"})
/*     */   public void view(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 120 */     String body = "\"\"";
/* 121 */     String message = "\"success\"";
/* 122 */     int code = 200;
/*     */     try {
/* 124 */       WebErrors errors = WebErrors.create(request);
/* 125 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 127 */       if (errors.hasErrors()) {
/* 128 */         code = 202;
/* 129 */         message = "\"param error\"";
/*     */       } else {
/* 131 */         Order order = new Order();
/* 132 */         if (id.longValue() != 0L) {
/* 133 */           order = this.orderMng.findById(id);
/*     */         }
/* 135 */         body = order.convertToJson().toString();
/*     */       }
/*     */     } catch (Exception e) {
/* 138 */       e.printStackTrace();
/* 139 */       code = 100;
/* 140 */       message = "\"system exception\"";
/*     */     }
/* 142 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 143 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/order/update"})
/*     */   public void update(Long id, String itemId, String itemCount, String itemPrice, Long shippingId, Double freight, String comments, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 156 */     String body = "\"\"";
/* 157 */     String message = "\"success\"";
/* 158 */     int code = 200;
/*     */     try {
/* 160 */       WebErrors errors = WebErrors.create(request);
/* 161 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 163 */       if (errors.hasErrors()) {
/* 164 */         code = 202;
/* 165 */         message = "\"param error\"";
/*     */       } else {
/* 167 */         Order order = this.orderMng.findById(id);
/* 168 */         int score = 0;
/* 169 */         int weight = 0;
/* 170 */         double price = 0.0D;
/* 171 */         Long[] itemIds = null;
/* 172 */         Integer[] itemCounts = null;
/* 173 */         Double[] itemPrices = null;
/* 174 */         if (StringUtils.isNotBlank(itemId)) {
/* 175 */           String[] str = itemId.split(",");
/* 176 */           itemIds = new Long[str.length];
/* 177 */           for (int i = 0; i < str.length; i++) {
/* 178 */             itemIds[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */           }
/*     */         }
/* 181 */         if (StringUtils.isNotBlank(itemCount)) {
/* 182 */           String[] str1 = itemCount.split(",");
/* 183 */           itemCounts = new Integer[str1.length];
/* 184 */           for (int i = 0; i < str1.length; i++) {
/* 185 */             itemCounts[i] = Integer.valueOf(Integer.parseInt(str1[i]));
/*     */           }
/*     */         }
/* 188 */         if (StringUtils.isNotBlank(itemPrice)) {
/* 189 */           String[] str2 = itemPrice.split(",");
/* 190 */           itemPrices = new Double[str2.length];
/* 191 */           for (int i = 0; i < str2.length; i++) {
/* 192 */             itemPrices[i] = Double.valueOf(Double.parseDouble(str2[i]));
/*     */           }
/*     */         }
/* 195 */         for (int i = 0; i < itemIds.length; i++) {
/* 196 */           OrderItem[] orderItem = this.orderItemMng.findById(itemIds);
/* 197 */           Product product = orderItem[i].getProduct();
/* 198 */           product.setStockCount(
/* 199 */             Integer.valueOf(product.getStockCount().intValue() + 
/* 199 */             orderItem[i].getCount().intValue() - itemCounts[i].intValue()));
/* 200 */           if (orderItem[i].getProductFash() != null) {
/* 201 */             ProductFashion productFash = orderItem[i]
/* 202 */               .getProductFash();
/* 203 */             productFash.setStockCount(
/* 204 */               Integer.valueOf(productFash.getStockCount().intValue() + 
/* 204 */               orderItem[i].getCount().intValue() - itemCounts[i].intValue()));
/* 205 */             this.productFashionMng.update(productFash);
/*     */           }
/* 207 */           orderItem[i].setCount(itemCounts[i]);
/* 208 */           orderItem[i].setSalePrice(itemPrices[i]);
/*     */ 
/* 210 */           score = score + itemCounts[i].intValue() * 
/* 210 */             orderItem[i].getProduct().getScore().intValue();
/*     */ 
/* 212 */           weight = weight + orderItem[i].getProduct().getWeight().intValue() * 
/* 212 */             itemCounts[i].intValue();
/* 213 */           if (orderItem[i].getProductFash() != null)
/* 214 */             price += itemPrices[i].doubleValue() * itemCounts[i].intValue();
/*     */           else {
/* 216 */             price += itemPrices[i].doubleValue() * itemCounts[i].intValue();
/*     */           }
/* 218 */           this.orderItemMng.updateByUpdaters(orderItem);
/* 219 */           this.productMng.updateByUpdater(product);
/*     */         }
/* 221 */         order.setScore(Integer.valueOf(score));
/* 222 */         order.setWeight(Double.valueOf(weight));
/* 223 */         order.setProductPrice(Double.valueOf(price));
/*     */ 
/* 229 */         order.setFreight(freight);
/* 230 */         order.setTotal(Double.valueOf(freight.doubleValue() + price));
/* 231 */         if (comments != null) {
/* 232 */           order.setComments(comments);
/*     */         }
/*     */ 
/* 235 */         order.setShipping(this.shippingMng.findById(shippingId));
/* 236 */         order.setLastModified(new Timestamp(System.currentTimeMillis()));
/* 237 */         this.orderMng.updateByUpdater(order);
/* 238 */         log.info("update Order, id={}.", order.getId());
/*     */       }
/*     */     } catch (Exception e) {
/* 241 */       e.printStackTrace();
/* 242 */       code = 100;
/* 243 */       message = "\"system exception\"";
/*     */     }
/* 245 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 246 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/order/affirm"})
/*     */   public void affirm(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 259 */     String body = "\"\"";
/* 260 */     String message = "\"success\"";
/* 261 */     int code = 200;
/*     */     try {
/* 263 */       WebErrors errors = WebErrors.create(request);
/* 264 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 266 */       if (errors.hasErrors()) {
/* 267 */         code = 202;
/* 268 */         message = "\"param error\"";
/*     */       } else {
/* 270 */         Order order = this.orderMng.findById(id);
/* 271 */         if (order.getStatus().intValue() == 1) {
/* 272 */           order.setStatus(Integer.valueOf(2));
/* 273 */           this.orderMng.updateByUpdater(order);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 277 */       e.printStackTrace();
/* 278 */       code = 100;
/* 279 */       message = "\"system exception\"";
/*     */     }
/* 281 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 282 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/order/accomplish"})
/*     */   public void accomplish(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 295 */     String body = "\"\"";
/* 296 */     String message = "\"success\"";
/* 297 */     int code = 200;
/*     */     try {
/* 299 */       WebErrors errors = WebErrors.create(request);
/* 300 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 302 */       if (errors.hasErrors()) {
/* 303 */         code = 202;
/* 304 */         message = "\"param error\"";
/*     */       } else {
/* 306 */         Order order = this.orderMng.findById(id);
/* 307 */         if ((order.getShippingStatus().intValue() == 2) && (order.getStatus().intValue() == 2) && 
/* 308 */           (order.getPaymentStatus().intValue() == 2)) {
/* 309 */           order.setStatus(Integer.valueOf(4));
/*     */ 
/* 311 */           ShopMember member = order.getMember();
/* 312 */           member.setFreezeScore(
/* 313 */             Integer.valueOf(member.getFreezeScore().intValue() - 
/* 313 */             order.getScore().intValue()));
/* 314 */           member.setScore(Integer.valueOf(member.getScore().intValue() + order.getScore().intValue()));
/* 315 */           this.shopMemberMng.update(member);
/* 316 */           List list = this.shopScoreMng.getlist(
/* 317 */             Long.toString(order.getCode().longValue()));
/* 318 */           for (ShopScore s : list) {
/* 319 */             s.setStatus(true);
/* 320 */             this.shopScoreMng.update(s);
/*     */           }
/* 322 */           this.orderMng.updateByUpdater(order);
/* 323 */           this.orderMng.updateliRun(id);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 327 */       e.printStackTrace();
/* 328 */       code = 100;
/* 329 */       message = "\"system exception\"";
/*     */     }
/* 331 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 332 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/shopShipments"})
/*     */   public void shopShipments(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 344 */     String body = "\"\"";
/* 345 */     String message = "\"\"";
/* 346 */     int code = 200;
/*     */     try {
/* 348 */       if (pageNo == null) {
/* 349 */         pageNo = Integer.valueOf(1);
/*     */       }
/* 351 */       if (pageSize == null) {
/* 352 */         pageSize = Integer.valueOf(10);
/*     */       }
/* 354 */       Pagination page = this.shopShipmentsMng.getPage(pageNo.intValue(), pageSize.intValue());
/* 355 */       List list = page.getList();
/* 356 */       JSONArray jsonArray = new JSONArray();
/* 357 */       int totalCount = page.getTotalCount();
/* 358 */       if ((list != null) && (list.size() > 0)) {
/* 359 */         for (int i = 0; i < list.size(); i++) {
/* 360 */           jsonArray.put(i, ((ShopShipments)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/* 363 */       message = "\"success\"";
/* 364 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     } catch (Exception e) {
/* 366 */       e.printStackTrace();
/* 367 */       ExceptionUtil.convertException(response, request, e);
/*     */     }
/* 369 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 370 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/order/save"})
/*     */   public void shopShipments_save(Long orderId, Long id, Shipments bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 383 */     String body = "\"\"";
/* 384 */     String message = "\"success\"";
/* 385 */     int code = 200;
/*     */     try {
/* 387 */       WebErrors errors = WebErrors.create(request);
/* 388 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { orderId, id });
/* 389 */       if (!errors.hasErrors()) {
/* 390 */         Order order = this.orderMng.findById(orderId);
/* 391 */         bean.setShopAdmin(CmsThreadVariable.getAdminUser());
/* 392 */         bean.setIndent(order);
/* 393 */         bean.setIsPrint(Boolean.valueOf(false));
/* 394 */         if (id != null) {
/* 395 */           ShopShipments shipment = this.shopShipmentsMng.findById(id);
/* 396 */           bean.setShippingName(shipment.getName());
/* 397 */           bean.setShippingMobile(shipment.getMobile());
/* 398 */           bean.setShippingAddress(shipment.getAddress());
/*     */         }
/* 400 */         this.shipmentMng.save(bean);
/* 401 */         order.setShippingStatus(Integer.valueOf(2));
/* 402 */         this.orderMng.updateByUpdater(order);
/* 403 */         this.orderMng.updateSaleCount(orderId);
/*     */       }
/*     */       else {
/* 406 */         code = 202;
/* 407 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 410 */       e.printStackTrace();
/* 411 */       code = 100;
/* 412 */       message = "\"system exception\"";
/*     */     }
/* 414 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 415 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/order/pay"})
/*     */   public void orderPay(Long orderId, Gathering bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 429 */     String body = "\"\"";
/* 430 */     String message = "\"success\"";
/* 431 */     int code = 200;
/*     */     try {
/* 433 */       WebErrors errors = WebErrors.create(request);
/* 434 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { orderId, bean.getBank(), bean.getAccounts(), Double.valueOf(bean.getMoney()), bean.getDrawee() });
/* 435 */       if (!errors.hasErrors()) {
/* 436 */         Order order = this.orderMng.findById(orderId);
/* 437 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/* 438 */         bean.setShopAdmin(admin);
/* 439 */         bean.setIndent(order);
/*     */ 
/* 441 */         if ((order.getPayment().getType().byteValue() == 1) || (order.getPayment().getType().byteValue() == 2)) {
/* 442 */           order.setPaymentStatus(Integer.valueOf(2));
/* 443 */           this.orderMng.updateOrderPay(bean, order);
/*     */         }
/*     */       } else {
/* 446 */         code = 202;
/* 447 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 450 */       e.printStackTrace();
/* 451 */       code = 100;
/* 452 */       message = "\"system exception\"";
/*     */     }
/* 454 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 455 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/order/shipmentList"})
/*     */   public void shipmentsList(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 465 */     String body = "\"\"";
/* 466 */     String message = "\"success\"";
/* 467 */     int code = 200;
/*     */     try {
/* 469 */       Website web = SiteUtils.getWeb(request);
/* 470 */       List list = this.shippingMng.getList(web.getId(), true);
/* 471 */       JSONArray jsonArray = new JSONArray();
/* 472 */       if (((list != null ? 1 : 0) & (list.size() > 0 ? 1 : 0)) != 0) {
/* 473 */         for (int i = 0; i < list.size(); i++) {
/* 474 */           jsonArray.put(i, ((Shipping)list.get(i)).converToJson());
/*     */         }
/*     */       }
/* 477 */       body = jsonArray.toString();
/*     */     } catch (Exception e) {
/* 479 */       e.printStackTrace();
/* 480 */       code = 100;
/* 481 */       message = "\"system exception\"";
/*     */     }
/* 483 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 484 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/order/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 495 */     String body = "\"\"";
/* 496 */     String message = "\"success\"";
/* 497 */     int code = 200;
/*     */     try
/*     */     {
/* 500 */       if (!StringUtils.isNotBlank(ids)) {
/* 501 */         code = 202;
/* 502 */         message = "\"param error\"";
/*     */       } else {
/* 504 */         Long[] id = null;
/* 505 */         String[] str = ids.split(",");
/* 506 */         id = new Long[str.length];
/* 507 */         for (int i = 0; i < str.length; i++) {
/* 508 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 510 */         this.orderMng.updateByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 513 */       ExceptionUtil.convertException(response, request, e);
/* 514 */       return;
/*     */     }
/* 516 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 517 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/order/cancel"})
/*     */   public void orderClanel(Long orderId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 531 */     String body = "\"\"";
/* 532 */     String message = "\"success\"";
/* 533 */     int code = 200;
/*     */     try {
/* 535 */       WebErrors errors = WebErrors.create(request);
/* 536 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { orderId });
/* 537 */       if (!errors.hasErrors())
/*     */       {
/* 540 */         Order order = this.orderMng.findById(orderId);
/* 541 */         if ((order.getPayment() != null) && (((order.getPayment().getId().longValue() == 1L) && (order.getPaymentStatus().intValue() == 1)) || (
/* 542 */           (order.getPayment().getId().longValue() != 1L) && (order.getPaymentStatus().intValue() == 1) && (order.getShippingStatus().intValue() == 1)))) {
/* 543 */           this.orderMng.updateOrderCanel(order);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 550 */         code = 202;
/* 551 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 554 */       e.printStackTrace();
/* 555 */       code = 100;
/* 556 */       message = "\"system exception\"";
/*     */     }
/* 558 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 559 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.OrderController
 * JD-Core Version:    0.6.0
 */