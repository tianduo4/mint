/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.OrderDao;
/*     */ import com.jspgou.cms.entity.Cart;
/*     */ import com.jspgou.cms.entity.CartItem;
/*     */ import com.jspgou.cms.entity.Coupon;
/*     */ import com.jspgou.cms.entity.Gathering;
/*     */ import com.jspgou.cms.entity.MemberCoupon;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.OrderReturn;
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.entity.PopularityGroup;
/*     */ import com.jspgou.cms.entity.PopularityItem;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberAddress;
/*     */ import com.jspgou.cms.entity.ShopScore;
/*     */ import com.jspgou.cms.entity.ShopScore.ScoreTypes;
/*     */ import com.jspgou.cms.manager.CartItemMng;
/*     */ import com.jspgou.cms.manager.CartMng;
/*     */ import com.jspgou.cms.manager.GatheringMng;
/*     */ import com.jspgou.cms.manager.MemberCouponMng;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.OrderReturnMng;
/*     */ import com.jspgou.cms.manager.PaymentMng;
/*     */ import com.jspgou.cms.manager.PopularityItemMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShipmentsMng;
/*     */ import com.jspgou.cms.manager.ShippingMng;
/*     */ import com.jspgou.cms.manager.ShopMemberAddressMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.ShopScoreMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class OrderMngImpl
/*     */   implements OrderMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng productFashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopScoreMng shopScoreMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private CartMng cartMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private CartItemMng cartItemMng;
/*     */ 
/*     */   @Autowired
/*     */   private GatheringMng gatheringMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShipmentsMng shipmentMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderReturnMng orderReturnMng;
/*     */ 
/*     */   @Autowired
/*     */   private MemberCouponMng memberCouponMng;
/*     */ 
/*     */   @Autowired
/*     */   private PaymentMng paymentMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberAddressMng shopMemberAddressMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng shippingMng;
/*     */ 
/*     */   @Autowired
/*     */   private PopularityItemMng popularityItemMng;
/*     */ 
/*     */   public Pagination getPageForOrderReturn(Long memberId, int pageNo, int pageSize)
/*     */   {
/*  66 */     return this.dao.getPageForOrderReturn(memberId, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Order createOrder(Cart cart, Long[] cartItemId, Long shippingMethodId, Long deliveryInfo, Long paymentMethodId, String comments, String ip, ShopMember member, Long webId, String memberCouponId)
/*     */   {
/*  73 */     Website web = this.websiteMng.findById(webId);
/*  74 */     Long mcId = null;
/*  75 */     if (!StringUtils.isBlank(memberCouponId)) {
/*  76 */       mcId = Long.valueOf(Long.parseLong(memberCouponId));
/*     */     }
/*  78 */     Payment pay = this.paymentMng.findById(paymentMethodId);
/*     */ 
/*  80 */     Shipping shipping = this.shippingMng.findById(shippingMethodId);
/*  81 */     ShopMemberAddress address = this.shopMemberAddressMng.findById(deliveryInfo);
/*     */ 
/*  83 */     Order order = new Order();
/*  84 */     order.setShipping(shipping);
/*  85 */     order.setWebsite(web);
/*  86 */     order.setMember(member);
/*  87 */     order.setPayment(pay);
/*  88 */     order.setIp(ip);
/*  89 */     order.setComments(comments);
/*  90 */     order.setStatus(Integer.valueOf(1));
/*  91 */     order.setShippingStatus(Integer.valueOf(1));
/*  92 */     order.setPaymentStatus(Integer.valueOf(1));
/*  93 */     order.setReceiveName(address.getUsername());
/*  94 */     order.setReceiveAddress(getAddress(address));
/*  95 */     order.setReceiveMobile(address.getTel());
/*  96 */     order.setReceivePhone(address.getMobile());
/*  97 */     order.setReceiveZip(address.getPostCode());
/*  98 */     order.setDelStatus(Boolean.valueOf(true));
/*  99 */     int score = 0;
/* 100 */     int weight = 0;
/* 101 */     double price = 0.0D;
/* 102 */     Double couponPrice = Double.valueOf(0.0D);
/* 103 */     Double popularityPrice = Double.valueOf(0.0D);
/* 104 */     if (cart != null) {
/* 105 */       List popularityItems = this.popularityItemMng.getlist(cart.getId(), null);
/* 106 */       for (PopularityItem popularityItem : popularityItems) {
/* 107 */         popularityPrice = Double.valueOf(popularityPrice.doubleValue() + popularityItem.getPopularityGroup().getPrivilege().doubleValue() * popularityItem.getCount().intValue());
/*     */       }
/*     */     }
/* 110 */     if (mcId != null) {
/* 111 */       MemberCoupon memberCoupon = this.memberCouponMng.findById(mcId);
/* 112 */       if ((memberCoupon != null) && 
/* 113 */         (memberCoupon.getCoupon().getIsusing().booleanValue()) && (!memberCoupon.getIsuse().booleanValue())) {
/* 114 */         couponPrice = Double.valueOf(memberCoupon.getCoupon().getCouponPrice().doubleValue());
/* 115 */         memberCoupon.setIsuse(Boolean.valueOf(true));
/* 116 */         this.memberCouponMng.update(memberCoupon);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 121 */     List itemList = new ArrayList();
/* 122 */     for (Long cId : cartItemId) {
/* 123 */       itemList.add(this.cartItemMng.findById(cId));
/*     */     }
/* 125 */     for (CartItem item : itemList) {
/* 126 */       score += item.getProduct().getScore().intValue() * item.getCount().intValue();
/* 127 */       weight += item.getProduct().getWeight().intValue() * item.getCount().intValue();
/* 128 */       if (item.getProductFash() != null)
/* 129 */         price += item.getProductFash().getSalePrice().doubleValue() * item.getCount().intValue();
/*     */       else {
/* 131 */         price += item.getProduct().getSalePrice().doubleValue() * item.getCount().intValue();
/*     */       }
/*     */     }
/* 134 */     if (member.getFreezeScore() != null)
/* 135 */       member.setFreezeScore(Integer.valueOf(score + member.getFreezeScore().intValue()));
/*     */     else {
/* 137 */       member.setFreezeScore(Integer.valueOf(score + 0));
/*     */     }
/* 139 */     order.setScore(Integer.valueOf(score));
/* 140 */     order.setWeight(Double.valueOf(weight));
/* 141 */     order.setProductPrice(Double.valueOf(price));
/* 142 */     double freight = shipping.calPrice(Double.valueOf(weight)).doubleValue();
/* 143 */     order.setFreight(Double.valueOf(freight));
/* 144 */     order.setTotal(Double.valueOf(freight + price - couponPrice.doubleValue() - popularityPrice.doubleValue()));
/* 145 */     Long date = Long.valueOf(new Date().getTime() + member.getId().longValue());
/* 146 */     order.setCode(date);
/*     */ 
/* 148 */     CartItem cartItem = null;
/* 149 */     OrderItem orderItem = null;
/* 150 */     String productName = "";
/* 151 */     for (int j = 0; j < itemList.size(); j++) {
/* 152 */       orderItem = new OrderItem();
/* 153 */       cartItem = (CartItem)itemList.get(j);
/* 154 */       orderItem.setOrdeR(order);
/* 155 */       orderItem.setProduct(cartItem.getProduct());
/* 156 */       orderItem.setWebsite(web);
/* 157 */       orderItem.setCount(cartItem.getCount());
/* 158 */       if (cartItem.getProductFash() != null) {
/* 159 */         orderItem.setProductFash(cartItem.getProductFash());
/* 160 */         orderItem.setSalePrice(cartItem.getProductFash().getSalePrice());
/*     */       } else {
/* 162 */         orderItem.setSalePrice(cartItem.getProduct().getSalePrice());
/*     */       }
/* 164 */       productName = productName + orderItem.getProduct().getName();
/* 165 */       order.addToItems(orderItem);
/*     */     }
/* 167 */     order.setProductName(productName);
/* 168 */     List popularityItemList = this.popularityItemMng.getlist(cart.getId(), null);
/* 169 */     for (PopularityItem popularityItem : popularityItemList) {
/* 170 */       this.popularityItemMng.deleteById(popularityItem.getId());
/*     */     }
/* 172 */     cart.getItems().removeAll(itemList);
/* 173 */     this.cartMng.update(cart);
/* 174 */     order = save(order);
/* 175 */     ShopScore shopScore = null;
/* 176 */     Product product = null;
/* 177 */     ProductFashion fashion = null;
/* 178 */     for (OrderItem item : order.getItems())
/*     */     {
/* 180 */       product = item.getProduct();
/* 181 */       if (item.getProductFash() != null) {
/* 182 */         fashion = item.getProductFash();
/* 183 */         fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() - item.getCount().intValue()));
/* 184 */         product.setStockCount(Integer.valueOf(product.getStockCount().intValue() - item.getCount().intValue()));
/* 185 */         this.productFashionMng.update(fashion);
/*     */       } else {
/* 187 */         product.setStockCount(Integer.valueOf(product.getStockCount().intValue() - item.getCount().intValue()));
/*     */       }
/* 189 */       this.productMng.updateByUpdater(product);
/* 190 */       shopScore = new ShopScore();
/* 191 */       shopScore.setMember(member);
/* 192 */       shopScore.setName(cartItem.getProduct().getName());
/* 193 */       shopScore.setScoreTime(new Date());
/* 194 */       shopScore.setStatus(false);
/* 195 */       shopScore.setUseStatus(false);
/* 196 */       shopScore.setScoreType(Integer.valueOf(ShopScore.ScoreTypes.ORDER_SCORE.getValue()));
/* 197 */       shopScore.setScore(item.getProduct().getScore());
/* 198 */       shopScore.setCode(Long.toString(order.getCode().longValue()));
/* 199 */       this.shopScoreMng.save(shopScore);
/*     */     }
/* 201 */     return order;
/*     */   }
/*     */ 
/*     */   public String getAddress(ShopMemberAddress address)
/*     */   {
/* 206 */     String str = "";
/* 207 */     if (address.getProvince() != null) {
/* 208 */       str = str + address.getProvince() + "-";
/*     */     }
/* 210 */     if (address.getCity() != null) {
/* 211 */       str = str + address.getCity() + "-";
/*     */     }
/* 213 */     if (address.getCountry() != null) {
/* 214 */       str = str + address.getCountry() + "-";
/*     */     }
/* 216 */     str = str + address.getDetailaddress();
/* 217 */     return str;
/*     */   }
/*     */ 
/*     */   public Order updateByUpdater(Order bean)
/*     */   {
/* 222 */     Updater updater = new Updater(bean);
/* 223 */     return this.dao.updateByUpdater(updater);
/*     */   }
/*     */ 
/*     */   public Pagination getOrderByReturn(Long memberId, Integer pageNo, Integer pageSize)
/*     */   {
/* 228 */     return this.dao.getOrderByReturn(memberId, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Integer paymentStatus, Integer shippingStatus, Long code, int pageNo, int pageSize)
/*     */   {
/* 236 */     Pagination page = this.dao.getPage(webId, memberId, productName, userName, paymentId, shippingId, 
/* 237 */       startTime, endTime, startOrderTotal, endOrderTotal, status, paymentStatus, shippingStatus, code, pageNo, pageSize);
/* 238 */     return page;
/*     */   }
/*     */ 
/*     */   public List<Order> getlist()
/*     */   {
/* 243 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 244 */     Date newDate = new Date();
/* 245 */     Date endDate = null;
/* 246 */     Calendar date = Calendar.getInstance();
/* 247 */     date.setTime(newDate);
/* 248 */     date.set(5, date.get(5) - 1);
/*     */     try {
/* 250 */       endDate = sdf.parse(sdf.format(date.getTime()));
/*     */     } catch (ParseException e) {
/* 252 */       e.printStackTrace();
/*     */     }
/* 254 */     List list = this.dao.getlist(endDate);
/* 255 */     return list;
/*     */   }
/*     */ 
/*     */   public void abolishOrder()
/*     */   {
/* 260 */     List orderList = getlist();
/* 261 */     for (Order order : orderList) {
/* 262 */       order.setStatus(Integer.valueOf(3));
/*     */       ProductFashion fashion;
/* 264 */       for (OrderItem item : order.getItems()) {
/* 265 */         Product product = item.getProduct();
/* 266 */         if (item.getProductFash() != null) {
/* 267 */           fashion = item.getProductFash();
/* 268 */           fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
/* 269 */           product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/* 270 */           this.productFashionMng.update(fashion);
/*     */         } else {
/* 272 */           product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/*     */         }
/* 274 */         this.productMng.updateByUpdater(product);
/*     */       }
/*     */ 
/* 277 */       ShopMember member = order.getMember();
/* 278 */       member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
/* 279 */       this.shopMemberMng.update(member);
/* 280 */       List list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
/* 281 */       for (ShopScore s : list) {
/* 282 */         this.shopScoreMng.deleteById(s.getId());
/*     */       }
/* 284 */       updateByUpdater(order);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Long code, int pageNo, int pageSize)
/*     */   {
/* 293 */     Pagination page = this.dao.getPage(webId, memberId, productName, userName, paymentId, shippingId, 
/* 294 */       startTime, endTime, startOrderTotal, endOrderTotal, status, code, pageNo, pageSize);
/* 295 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPageForMember(Long memberId, int pageNo, int pageSize) {
/* 301 */     return this.dao.getPageForMember(memberId, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageForMember1(Long memberId, int pageNo, int pageSize)
/*     */   {
/* 306 */     return this.dao.getPageForMember1(memberId, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageForMember2(Long memberId, int pageNo, int pageSize)
/*     */   {
/* 311 */     return this.dao.getPageForMember2(memberId, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageForMember3(Long memberId, int pageNo, int pageSize) {
/* 315 */     return this.dao.getPageForMember3(memberId, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Order findById(Long id) {
/* 321 */     Order entity = this.dao.findById(id);
/* 322 */     return entity;
/*     */   }
/*     */ 
/*     */   public Order findByCode(Long code)
/*     */   {
/* 327 */     Order entity = this.dao.findByCode(code);
/* 328 */     return entity;
/*     */   }
/*     */ 
/*     */   public void updateSaleCount(Long id)
/*     */   {
/* 334 */     Order entity = this.dao.findById(id);
/* 335 */     for (OrderItem item : entity.getItems()) {
/* 336 */       Product product = item.getProduct();
/* 337 */       product.setSaleCount(Integer.valueOf(product.getSaleCount().intValue() + item.getCount().intValue()));
/* 338 */       this.productMng.update(product);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateliRun(Long id)
/*     */   {
/* 345 */     Order entity = this.dao.findById(id);
/* 346 */     for (OrderItem item : entity.getItems()) {
/* 347 */       Product product = item.getProduct();
/* 348 */       ProductFashion productFashion = item.getProductFash();
/* 349 */       if (productFashion != null)
/* 350 */         product.setLiRun(Double.valueOf(product.getLiRun().doubleValue() + item.getCount().intValue() * (productFashion.getSalePrice().doubleValue() - productFashion.getCostPrice().doubleValue())));
/*     */       else {
/* 352 */         product.setLiRun(Double.valueOf(product.getLiRun().doubleValue() + item.getCount().intValue() * (product.getSalePrice().doubleValue() - product.getCostPrice().doubleValue())));
/*     */       }
/*     */ 
/* 355 */       this.productMng.update(product);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Order save(Order bean)
/*     */   {
/* 361 */     bean.init();
/* 362 */     this.dao.save(bean);
/* 363 */     return bean;
/*     */   }
/*     */ 
/*     */   public List<Object> getTotlaOrder()
/*     */   {
/* 368 */     return this.dao.getTotlaOrder();
/*     */   }
/*     */ 
/*     */   public BigDecimal getMemberMoneyByYear(Long memberId)
/*     */   {
/* 373 */     return this.dao.getMemberMoneyByYear(memberId);
/*     */   }
/*     */ 
/*     */   public Order deleteById(Long id)
/*     */   {
/* 378 */     Order bean = this.dao.findById(id);
/* 379 */     this.gatheringMng.deleteByorderId(id);
/* 380 */     this.shipmentMng.deleteByorderId(id);
/* 381 */     if (bean.getReturnOrder() != null) {
/* 382 */       this.orderReturnMng.deleteById(bean.getReturnOrder().getId());
/*     */     }
/* 384 */     if (((bean.getShippingStatus().intValue() == 1) && (bean.getStatus().intValue() == 1)) || ((bean.getShippingStatus().intValue() == 1) && (bean.getStatus().intValue() == 2))) {
/* 385 */       Set set = bean.getItems();
/*     */       ProductFashion fashion;
/* 387 */       for (OrderItem item : set) {
/* 388 */         Product product = item.getProduct();
/* 389 */         if (item.getProductFash() != null) {
/* 390 */           fashion = item.getProductFash();
/* 391 */           fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
/* 392 */           product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/* 393 */           this.productFashionMng.update(fashion);
/*     */         } else {
/* 395 */           product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/*     */         }
/* 397 */         this.productMng.updateByUpdater(product);
/*     */       }
/*     */ 
/* 400 */       ShopMember member = bean.getMember();
/* 401 */       member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - bean.getScore().intValue()));
/* 402 */       this.shopMemberMng.update(member);
/* 403 */       List list = this.shopScoreMng.getlist(Long.toString(bean.getCode().longValue()));
/* 404 */       for (ShopScore s : list) {
/* 405 */         this.shopScoreMng.deleteById(s.getId());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 410 */     bean = this.dao.deleteById(id);
/* 411 */     return bean;
/*     */   }
/*     */ 
/*     */   public Order[] deleteByIds(Long[] ids)
/*     */   {
/* 417 */     Order[] beans = new Order[ids.length];
/* 418 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 419 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 421 */     return beans;
/*     */   }
/*     */ 
/*     */   public Order[] updateByIds(Long[] ids)
/*     */   {
/* 426 */     Order[] beans = new Order[ids.length];
/* 427 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 428 */       beans[i] = updateById(ids[i]);
/*     */     }
/* 430 */     return beans;
/*     */   }
/*     */ 
/*     */   public Order updateById(Long id)
/*     */   {
/* 435 */     return this.dao.updateById(id);
/*     */   }
/*     */ 
/*     */   public List<Order> getCountByStatus(Date startTime, Date endTime, Integer status)
/*     */   {
/* 440 */     return this.dao.getCountByStatus(startTime, endTime, status);
/*     */   }
/*     */ 
/*     */   public List<Order> getCountByStatus1(Date startTime, Date endTime, Integer status) {
/* 444 */     return this.dao.getCountByStatus1(startTime, endTime, status);
/*     */   }
/*     */ 
/*     */   public List<Order> getStatisticByYear(Integer year, Integer status)
/*     */   {
/* 449 */     return this.dao.getStatisticByYear(year, status);
/*     */   }
/*     */ 
/*     */   public List<Order> getStatisticByYear1(Integer year, Integer status) {
/* 453 */     return this.dao.getStatisticByYear1(year, status);
/*     */   }
/*     */ 
/*     */   public List<Order> getOrderList(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Long code, int firstResult, int maxResults)
/*     */   {
/* 459 */     return this.dao.getOrderList(webId, memberId, productName, userName, paymentId, shippingId, startTime, endTime, startOrderTotal, endOrderTotal, status, code, firstResult, maxResults);
/*     */   }
/*     */ 
/*     */   public List<Object[]> findListByIds(Long[] ids)
/*     */   {
/* 464 */     return this.dao.findListByIds(ids);
/*     */   }
/*     */ 
/*     */   public BigDecimal getOrderSale(Date date, Long webId)
/*     */   {
/* 470 */     return this.dao.getOrderSale(date, webId);
/*     */   }
/*     */ 
/*     */   public Long getOrderCount(Date date, Long webId)
/*     */   {
/* 476 */     return this.dao.getOrderCount(date, webId);
/*     */   }
/*     */ 
/*     */   public Long getUnSendOrderCount(Long webId)
/*     */   {
/* 482 */     return this.dao.getUnSendOrderCount(webId);
/*     */   }
/*     */ 
/*     */   public Long getUnPayOrderCount(Long webId)
/*     */   {
/* 488 */     return this.dao.getUnPayOrderCount(webId);
/*     */   }
/*     */ 
/*     */   public Long getReturnCount(Long webId)
/*     */   {
/* 494 */     return this.dao.getReturnCount(webId);
/*     */   }
/*     */ 
/*     */   public JSONObject getOrderSale(Long webId, String type, String monthStr, String yearStr)
/*     */     throws ParseException
/*     */   {
/* 501 */     List list = this.dao.getOrderSale(webId, type, monthStr, yearStr);
/*     */ 
/* 503 */     int years = DateUtils.getCurrentYear();
/* 504 */     int months = DateUtils.getCurrentMonth();
/* 505 */     int days = DateUtils.getCurrentDay();
/*     */ 
/* 507 */     if ((StringUtils.isNotEmpty(monthStr)) && (!(years + "-" + months).equals(monthStr))) {
/* 508 */       Date d = DateUtils.pasreToDate(monthStr, DateUtils.COMMON_FORMAT_MONTH);
/* 509 */       days = DateUtils.getDaysOfMonth(d);
/* 510 */       months = Integer.parseInt(DateUtils.formateDate(d, DateUtils.COMMON_FORMAT_MONTH_STR));
/*     */     }
/*     */ 
/* 513 */     if ((StringUtils.isNotEmpty(yearStr)) && (years != Integer.parseInt(yearStr))) {
/* 514 */       years = Integer.parseInt(yearStr);
/* 515 */       months = 12;
/*     */     }
/*     */ 
/* 518 */     JSONArray xJson = new JSONArray();
/* 519 */     JSONArray ySaleJson = new JSONArray();
/* 520 */     JSONArray yCountJson = new JSONArray();
/* 521 */     int firstLoop = 0;
/* 522 */     String suffix = "";
/* 523 */     if ("month".equals(type)) {
/* 524 */       firstLoop = days;
/* 525 */       suffix = months;
/* 526 */     } else if ("year".equals(type)) {
/* 527 */       firstLoop = months;
/* 528 */       suffix = years;
/*     */     }
/* 530 */     for (int i = 1; i <= firstLoop; i++) {
/* 531 */       String firstVal = StringUtils.leftPad(i, 2, '0');
/* 532 */       xJson.add(suffix + "-" + firstVal);
/* 533 */       String ySaleVal = "0";
/* 534 */       String yCountVal = "0";
/* 535 */       for (int j = 0; j < list.size(); j++) {
/* 536 */         Object[] obj = (Object[])list.get(j);
/* 537 */         String seVal = StringUtils.leftPad(String.valueOf(obj[2]), 2, '0');
/* 538 */         if (firstVal.equals(seVal)) {
/* 539 */           ySaleVal = String.valueOf(obj[0]);
/* 540 */           yCountVal = String.valueOf(obj[1]);
/*     */         }
/*     */       }
/* 543 */       ySaleJson.add(Double.valueOf(Double.parseDouble(ySaleVal)));
/* 544 */       yCountJson.add(Double.valueOf(Double.parseDouble(yCountVal)));
/*     */     }
/* 546 */     JSONObject json = new JSONObject();
/* 547 */     json.put("xdata", xJson);
/* 548 */     json.put("ySaleData", ySaleJson);
/* 549 */     json.put("yCountData", yCountJson);
/* 550 */     return json;
/*     */   }
/*     */ 
/*     */   public void updateOrderPay(Gathering gathering, Order bean)
/*     */   {
/* 555 */     this.gatheringMng.save(gathering);
/* 556 */     updateByUpdater(bean);
/*     */   }
/*     */ 
/*     */   public void updateOrderCanel(Order order)
/*     */   {
/* 562 */     order.setStatus(Integer.valueOf(3));
/*     */ 
/* 564 */     for (OrderItem item : order.getItems()) {
/* 565 */       Product product = item.getProduct();
/* 566 */       if (item.getProductFash() != null) {
/* 567 */         ProductFashion fashion = item.getProductFash();
/* 568 */         fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
/* 569 */         product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/* 570 */         this.productFashionMng.update(fashion);
/*     */       } else {
/* 572 */         product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/*     */       }
/* 574 */       this.productMng.updateByUpdater(product);
/*     */ 
/* 576 */       ShopMember member = order.getMember();
/* 577 */       member.setFreezeScore(Integer.valueOf(member.getFreezeScore().intValue() - order.getScore().intValue()));
/* 578 */       this.shopMemberMng.update(member);
/* 579 */       List list = this.shopScoreMng.getlist(Long.toString(order.getCode().longValue()));
/* 580 */       for (ShopScore s : list) {
/* 581 */         this.shopScoreMng.deleteById(s.getId());
/*     */       }
/* 583 */       updateByUpdater(order);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.OrderMngImpl
 * JD-Core Version:    0.6.0
 */