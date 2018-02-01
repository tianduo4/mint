/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.OrderDao;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.SQLQuery;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class OrderDaoImpl extends HibernateBaseDao<Order, Long>
/*     */   implements OrderDao
/*     */ {
/* 711 */   public static final Integer CHECKING = Integer.valueOf(1);
/*     */ 
/* 715 */   public static final Integer CHECKED = Integer.valueOf(2);
/*     */ 
/*     */   public Pagination getPageForMember(Long memberId, int pageNo, int pageSize)
/*     */   {
/*  35 */     Finder f = 
/*  36 */       Finder.create("from Order bean where bean.member.id=:memberId and bean.delStatus=:delStatus");
/*  37 */     f.setParam("memberId", memberId);
/*  38 */     f.setParam("delStatus", Boolean.valueOf(true));
/*  39 */     f.append(" and bean.status=40");
/*  40 */     f.append(" order by bean.id desc");
/*  41 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageForOrderReturn(Long memberId, int pageNo, int pageSize)
/*     */   {
/*  47 */     Finder f = 
/*  48 */       Finder.create("from Order bean where bean.returnOrder.id is not null and  bean.delStatus=:delStatus ");
/*  49 */     f.setParam("delStatus", Boolean.valueOf(true));
/*  50 */     if (memberId != null) {
/*  51 */       f.append(" and bean.member.id=:memberId");
/*  52 */       f.setParam("memberId", memberId);
/*     */     }
/*  54 */     f.append(" order by bean.id desc");
/*  55 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageForMember1(Long memberId, int pageNo, int pageSize)
/*     */   {
/*  60 */     Finder f = 
/*  61 */       Finder.create("from Order bean where bean.member.id=:memberId and  bean.delStatus=:delStatus ");
/*  62 */     f.setParam("memberId", memberId);
/*  63 */     f.setParam("delStatus", Boolean.valueOf(true));
/*  64 */     f.append(" and bean.status>=10 and bean.status<=19");
/*  65 */     f.append(" order by bean.id desc");
/*  66 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageForMember2(Long memberId, int pageNo, int pageSize)
/*     */   {
/*  71 */     Finder f = 
/*  72 */       Finder.create("from Order bean where bean.member.id=:memberId  and  bean.delStatus=:delStatus ");
/*  73 */     f.setParam("memberId", memberId);
/*  74 */     f.setParam("delStatus", Boolean.valueOf(true));
/*  75 */     f.append(" and bean.status>=20 and bean.status<=29");
/*  76 */     f.append(" order by bean.id desc");
/*  77 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<Order> getlist(Date endDate)
/*     */   {
/*  82 */     Finder f = Finder.create("from Order bean where bean.payment.type=1   and  bean.delStatus=:delStatus ");
/*  83 */     f.append(" and bean.paymentStatus=:paymentStatus");
/*  84 */     f.append(" and bean.createTime<:endTime");
/*  85 */     f.append(" and (bean.status=:checking or bean.status=:checked)");
/*  86 */     f.setParam("delStatus", Boolean.valueOf(true));
/*  87 */     f.setParam("checking", CHECKING);
/*  88 */     f.setParam("checked", CHECKED);
/*  89 */     f.setParam("endTime", endDate);
/*  90 */     f.setParam("paymentStatus", CHECKING);
/*  91 */     return find(f);
/*     */   }
/*     */ 
/*     */   public Pagination getPageForMember3(Long memberId, int pageNo, int pageSize)
/*     */   {
/*  96 */     Finder f = 
/*  97 */       Finder.create("from Order bean where bean.member.id=:memberId and  bean.delStatus=:delStatus  ");
/*  98 */     f.setParam("memberId", memberId);
/*  99 */     f.setParam("delStatus", Boolean.valueOf(true));
/* 100 */     f.append(" order by bean.id desc");
/* 101 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Integer paymentStatus, Integer shippingStatus, Long code, int pageNo, int pageSize)
/*     */   {
/* 110 */     Finder f = 
/* 111 */       Finder.create("from Order bean where bean.returnOrder.id is null and bean.delStatus=:delStatus   ");
/* 112 */     f.setParam("delStatus", Boolean.valueOf(true));
/* 113 */     if (webId != null) {
/* 114 */       f.append(" and bean.website.id=:webId");
/* 115 */       f.setParam("webId", webId);
/*     */     }
/* 117 */     if (memberId != null) {
/* 118 */       f.append(" and bean.member.id=:memberId");
/* 119 */       f.setParam("memberId", memberId);
/*     */     }
/* 121 */     if (!StringUtils.isBlank(userName)) {
/* 122 */       f.append(" and bean.receiveName like:userName");
/* 123 */       f.setParam("userName", "%" + userName + "%");
/*     */     }
/* 125 */     if (!StringUtils.isBlank(productName)) {
/* 126 */       f.append(" and bean.productName like:productName");
/* 127 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/* 129 */     if (paymentId != null) {
/* 130 */       f.append(" and bean.payment.id=:paymentId");
/* 131 */       f.setParam("paymentId", paymentId);
/*     */     }
/* 133 */     if (shippingId != null) {
/* 134 */       f.append(" and bean.shipping.id=:shippingId");
/* 135 */       f.setParam("shippingId", shippingId);
/*     */     }
/* 137 */     if (startTime != null) {
/* 138 */       f.append(" and bean.createTime>:startTime");
/* 139 */       f.setParam("startTime", startTime);
/*     */     }
/* 141 */     if (endTime != null) {
/* 142 */       f.append(" and bean.createTime<:endTime");
/* 143 */       f.setParam("endTime", endTime);
/*     */     }
/* 145 */     if (startOrderTotal != null) {
/* 146 */       f.append(" and bean.total>=:startOrderTotal");
/* 147 */       f.setParam("startOrderTotal", startOrderTotal);
/*     */     }
/* 149 */     if (endOrderTotal != null) {
/* 150 */       f.append(" and bean.total<=:endOrderTotal");
/* 151 */       f.setParam("endOrderTotal", endOrderTotal);
/*     */     }
/* 153 */     if (status != null) {
/* 154 */       if (status.intValue() == 5) {
/* 155 */         f.append(" and (bean.status=:checking or bean.status=:checked)");
/* 156 */         f.append(" and bean.paymentStatus=:payment");
/* 157 */         f.setParam("checking", CHECKING);
/* 158 */         f.setParam("checked", CHECKED);
/* 159 */         f.setParam("payment", CHECKING);
/* 160 */       } else if (status.intValue() == 6) {
/* 161 */         f.append(" and ((bean.payment.type=1 and bean.paymentStatus=:payment)or(bean.payment.type=2))");
/* 162 */         f.append(" and bean.status=:checked");
/* 163 */         f.append(" and bean.shippingStatus=:shipping");
/* 164 */         f.setParam("checked", CHECKED);
/* 165 */         f.setParam("shipping", CHECKING);
/* 166 */         f.setParam("payment", CHECKING);
/*     */       } else {
/* 168 */         f.append(" and bean.status=:status");
/* 169 */         f.setParam("status", status);
/*     */       }
/*     */     }
/* 172 */     if (paymentStatus != null) {
/* 173 */       f.append(" and bean.paymentStatus=:paymentStatus");
/* 174 */       f.setParam("paymentStatus", paymentStatus);
/*     */     }
/* 176 */     if (shippingStatus != null) {
/* 177 */       f.append(" and bean.shippingStatus=:shippingStatus");
/* 178 */       f.setParam("shippingStatus", shippingStatus);
/*     */     }
/* 180 */     if (code != null) {
/* 181 */       f.append(" and bean.code=:code");
/* 182 */       f.setParam("code", code);
/*     */     }
/* 184 */     f.append(" order by bean.id desc");
/* 185 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPage1(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Integer paymentStatus, Integer shippingStatus, Long code, int pageNo, int pageSize)
/*     */   {
/* 197 */     Finder f = 
/* 198 */       Finder.create("from Order bean where bean.returnOrder.id is null ");
/* 199 */     if (webId != null) {
/* 200 */       f.append(" and bean.website.id=:webId");
/* 201 */       f.setParam("webId", webId);
/*     */     }
/* 203 */     if (memberId != null) {
/* 204 */       f.append(" and bean.member.id=:memberId");
/* 205 */       f.setParam("memberId", memberId);
/*     */     }
/* 207 */     if (!StringUtils.isBlank(userName)) {
/* 208 */       f.append(" and bean.receiveName like:userName");
/* 209 */       f.setParam("userName", "%" + userName + "%");
/*     */     }
/* 211 */     if (!StringUtils.isBlank(productName)) {
/* 212 */       f.append(" and bean.productName like:productName");
/* 213 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/* 215 */     if (paymentId != null) {
/* 216 */       f.append(" and bean.payment.id=:paymentId");
/* 217 */       f.setParam("paymentId", paymentId);
/*     */     }
/* 219 */     if (shippingId != null) {
/* 220 */       f.append(" and bean.shipping.id=:shippingId");
/* 221 */       f.setParam("shippingId", shippingId);
/*     */     }
/* 223 */     if (startTime != null) {
/* 224 */       f.append(" and bean.createTime>:startTime");
/* 225 */       f.setParam("startTime", startTime);
/*     */     }
/* 227 */     if (endTime != null) {
/* 228 */       f.append(" and bean.createTime<:endTime");
/* 229 */       f.setParam("endTime", endTime);
/*     */     }
/* 231 */     if (startOrderTotal != null) {
/* 232 */       f.append(" and bean.total>=:startOrderTotal");
/* 233 */       f.setParam("startOrderTotal", startOrderTotal);
/*     */     }
/* 235 */     if (endOrderTotal != null) {
/* 236 */       f.append(" and bean.total<=:endOrderTotal");
/* 237 */       f.setParam("endOrderTotal", endOrderTotal);
/*     */     }
/* 239 */     if (status != null) {
/* 240 */       if (status.intValue() == 5) {
/* 241 */         f.append(" and (bean.status=:checking or bean.status=:checked)");
/* 242 */         f.append(" and bean.paymentStatus=:payment");
/* 243 */         f.setParam("checking", CHECKING);
/* 244 */         f.setParam("checked", CHECKED);
/* 245 */         f.setParam("payment", CHECKING);
/* 246 */       } else if (status.intValue() == 6) {
/* 247 */         f.append(" and ((bean.payment.type=1 and bean.paymentStatus=:payment)or(bean.payment.type=2))");
/* 248 */         f.append(" and bean.status=:checked");
/* 249 */         f.append(" and bean.shippingStatus=:shipping");
/* 250 */         f.setParam("checked", CHECKED);
/* 251 */         f.setParam("shipping", CHECKING);
/* 252 */         f.setParam("payment", CHECKING);
/*     */       } else {
/* 254 */         f.append(" and bean.status=:status");
/* 255 */         f.setParam("status", status);
/*     */       }
/*     */     }
/* 258 */     if (paymentStatus != null) {
/* 259 */       f.append(" and bean.paymentStatus=:paymentStatus");
/* 260 */       f.setParam("paymentStatus", paymentStatus);
/*     */     }
/* 262 */     if (shippingStatus != null) {
/* 263 */       f.append(" and bean.shippingStatus=:shippingStatus");
/* 264 */       f.setParam("shippingStatus", shippingStatus);
/*     */     }
/* 266 */     if (code != null) {
/* 267 */       f.append(" and bean.code=:code");
/* 268 */       f.setParam("code", code);
/*     */     }
/* 270 */     f.append(" and bean.delStatus=true");
/* 271 */     f.append(" order by bean.id desc");
/* 272 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Long code, int pageNo, int pageSize)
/*     */   {
/* 280 */     Finder f = Finder.create("from Order bean where 1=1 and bean.delStatus=true ");
/* 281 */     if (webId != null) {
/* 282 */       f.append(" and bean.website.id=:webId");
/* 283 */       f.setParam("webId", webId);
/*     */     }
/* 285 */     if (memberId != null) {
/* 286 */       f.append(" and bean.member.id=:memberId");
/* 287 */       f.setParam("memberId", memberId);
/*     */     }
/* 289 */     if (!StringUtils.isBlank(userName)) {
/* 290 */       f.append(" and bean.receiveName like:userName");
/* 291 */       f.setParam("userName", "%" + userName + "%");
/*     */     }
/* 293 */     if (!StringUtils.isBlank(productName)) {
/* 294 */       f.append(" and bean.productName like:productName");
/* 295 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/* 297 */     if (paymentId != null) {
/* 298 */       f.append(" and bean.payment.id=:paymentId");
/* 299 */       f.setParam("paymentId", paymentId);
/*     */     }
/* 301 */     if (shippingId != null) {
/* 302 */       f.append(" and bean.shipping.id=:shippingId");
/* 303 */       f.setParam("shippingId", shippingId);
/*     */     }
/* 305 */     if (startTime != null) {
/* 306 */       f.append(" and bean.createTime>:startTime");
/* 307 */       f.setParam("startTime", startTime);
/*     */     }
/* 309 */     if (endTime != null) {
/* 310 */       f.append(" and bean.createTime<:endTime");
/* 311 */       f.setParam("endTime", endTime);
/*     */     }
/* 313 */     if (startOrderTotal != null) {
/* 314 */       f.append(" and bean.total>=:startOrderTotal");
/* 315 */       f.setParam("startOrderTotal", startOrderTotal);
/*     */     }
/* 317 */     if (endOrderTotal != null) {
/* 318 */       f.append(" and bean.total<=:endOrderTotal");
/* 319 */       f.setParam("endOrderTotal", endOrderTotal);
/*     */     }
/* 321 */     if (status != null) {
/* 322 */       if (status.intValue() == 5) {
/* 323 */         f.append(" and (bean.status=:checking or bean.status=:checked)");
/* 324 */         f.append(" and bean.paymentStatus=:payment");
/* 325 */         f.setParam("checking", CHECKING);
/* 326 */         f.setParam("checked", CHECKED);
/* 327 */         f.setParam("payment", CHECKING);
/* 328 */       } else if (status.intValue() == 6) {
/* 329 */         f.append(" and (bean.status=:checking or bean.status=:checked)");
/* 330 */         f.append(" and bean.shippingStatus=:shipping");
/* 331 */         f.setParam("checking", CHECKING);
/* 332 */         f.setParam("checked", CHECKED);
/* 333 */         f.setParam("shipping", CHECKED);
/*     */       } else {
/* 335 */         f.append(" and bean.status=:status");
/* 336 */         f.setParam("status", status);
/*     */       }
/*     */     }
/*     */ 
/* 340 */     if (code != null) {
/* 341 */       f.append(" and bean.code=:code");
/* 342 */       f.setParam("code", code);
/*     */     }
/* 344 */     f.append(" order by bean.id desc");
/* 345 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<Object> getTotlaOrder()
/*     */   {
/* 350 */     List o = new ArrayList();
/* 351 */     Long totalOrder = (Long)getSession()
/* 352 */       .createQuery("select count(*) from Order bean where  bean.delStatus=true ").uniqueResult();
/* 353 */     Long noCompleteOrder = (Long)
/* 354 */       getSession()
/* 355 */       .createQuery(
/* 356 */       "select count(*) from Order bean where bean.status between 1 and 2 and  bean.delStatus=true ")
/* 357 */       .uniqueResult();
/* 358 */     Calendar c = Calendar.getInstance();
/* 359 */     String month = c.get(2) + "1";
/* 360 */     String year = c.get(1)+"";
/* 361 */     if (month.length() == 1)
/* 362 */       month = "0" + month;
/*     */     else {
/* 364 */       month = month;
/*     */     }
/* 366 */     String str = year + "-" + month;
/* 367 */     Long thisMontyOrder = (Long)
/* 368 */       getSession()
/* 369 */       .createQuery(
/* 370 */       "select count(*) from Order bean where bean.delStatus=true and  bean.createTime like :time")
/* 371 */       .setString("time", "%" + str + "%").uniqueResult();
/* 372 */     SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
/* 373 */     String tady = sf.format(new Date());
/* 374 */     Long todayOrder = (Long)
/* 375 */       getSession()
/* 376 */       .createQuery(
/* 377 */       "select count(*) from Order bean where bean.delStatus=true and  bean.createTime like :tody")
/* 378 */       .setString("tody", "%" + tady + "%").uniqueResult();
/* 379 */     Long totalProduct = (Long)getSession()
/* 380 */       .createQuery("select count(*) from Product bean")
/* 381 */       .uniqueResult();
/* 382 */     Long newProductMonth = (Long)
/* 383 */       getSession()
/* 384 */       .createQuery(
/* 385 */       "select count(*) from Product bean where bean.status !=" + Product.DEL_STATUS + " and  bean.createTime like :time")
/* 386 */       .setString("time", "%" + str + "%").uniqueResult();
/* 387 */     Long dateProduct = (Long)
/* 388 */       getSession()
/* 389 */       .createQuery(
/* 390 */       "select count(*) from Product bean where bean.status !=" + Product.DEL_STATUS + " and bean.createTime like :time")
/* 391 */       .setString("time", "%" + tady + "%").uniqueResult();
/* 392 */     Long putawayProduct = (Long)
/* 393 */       getSession()
/* 394 */       .createQuery(
/* 395 */       "select count(*) from Product bean where bean.status=" + Product.ON_SALE_STATUS)
/* 396 */       .uniqueResult();
/*     */ 
/* 398 */     Long totalMember = (Long)getSession()
/* 399 */       .createQuery("select count(*) from ShopMember bean")
/* 400 */       .uniqueResult();
/* 401 */     Long totalMonthMember = (Long)
/* 402 */       getSession()
/* 403 */       .createQuery(
/* 404 */       "select count(*) from Member bean where bean.createTime like :time")
/* 405 */       .setString("time", "%" + str + "%").uniqueResult();
/* 406 */     Long totalDateMember = (Long)
/* 407 */       getSession()
/* 408 */       .createQuery(
/* 409 */       "select count(*) from Member bean where bean.createTime like :time")
/* 410 */       .setString("time", "%" + tady + "%").uniqueResult();
/* 411 */     c.add(5, -7);
/* 412 */     Date oldDate = c.getTime();
/* 413 */     Long date7Member = (Long)
/* 414 */       getSession()
/* 415 */       .createQuery(
/* 416 */       "select count(*) from Member bean where bean.createTime >:time")
/* 417 */       .setParameter("time", oldDate).uniqueResult();
/*     */ 
/* 419 */     Long[] cc = new Long[12];
/* 420 */     cc[0] = Long.valueOf(totalOrder == null ? 0L : totalOrder.longValue());
/* 421 */     cc[1] = Long.valueOf(noCompleteOrder == null ? 0L : noCompleteOrder.longValue());
/* 422 */     cc[2] = Long.valueOf(thisMontyOrder == null ? 0L : thisMontyOrder.longValue());
/* 423 */     cc[3] = Long.valueOf(todayOrder == null ? 0L : todayOrder.longValue());
/*     */ 
/* 425 */     cc[4] = Long.valueOf(totalProduct == null ? 0L : totalProduct.longValue());
/* 426 */     cc[5] = Long.valueOf(newProductMonth == null ? 0L : newProductMonth.longValue());
/* 427 */     cc[6] = Long.valueOf(dateProduct == null ? 0L : dateProduct.longValue());
/*     */ 
/* 429 */     cc[7] = Long.valueOf(totalMember == null ? 0L : totalMember.longValue());
/* 430 */     cc[8] = Long.valueOf(totalMonthMember == null ? 0L : totalMonthMember.longValue());
/* 431 */     cc[9] = Long.valueOf(totalDateMember == null ? 0L : totalDateMember.longValue());
/* 432 */     cc[10] = Long.valueOf(date7Member == null ? 0L : date7Member.longValue());
/* 433 */     cc[11] = Long.valueOf(putawayProduct == null ? 0L : putawayProduct.longValue());
/*     */ 
/* 435 */     o.add(cc);
/* 436 */     return o;
/*     */   }
/*     */ 
/*     */   public BigDecimal getMemberMoneyByYear(Long memberId)
/*     */   {
/* 442 */     Calendar c = Calendar.getInstance();
/* 443 */     String year = c.get(1)+"";
/* 444 */     Query q = 
/* 445 */       getSession()
/* 446 */       .createQuery(
/* 447 */       "select sum((bean.salePrice)* bean.count) from OrderItem bean where bean.ordeR.member.id=:id and bean.ordeR.createTime like:time and bean.ordeR.status=4");
/*     */ 
/* 449 */     q.setParameter("id", memberId).setString("time", "%" + year + "%");
/* 450 */     Double v1 = (Double)q.uniqueResult();
/* 451 */     if (v1 == null) {
/* 452 */       v1 = Double.valueOf(0.0D);
/*     */     }
/* 454 */     return new BigDecimal(v1.doubleValue());
/*     */   }
/*     */ 
/*     */   public Integer[] getOrderByMember(Long memberId)
/*     */   {
/* 459 */     Long succOrder = (Long)
/* 460 */       getSession()
/* 461 */       .createQuery(
/* 462 */       "select count(*) from Order bean where bean.member.id=:id")
/* 463 */       .setParameter("id", memberId).uniqueResult();
/* 464 */     Long failOrder = (Long)
/* 465 */       getSession()
/* 466 */       .createQuery(
/* 467 */       "select count(*) from Order bean where bean.member.id=:id")
/* 468 */       .setParameter("id", memberId).uniqueResult();
/*     */ 
/* 470 */     Long totalOrder = (Long)
/* 471 */       getSession()
/* 472 */       .createQuery(
/* 473 */       "select count(*) from Order bean where bean.member.id=:id")
/* 474 */       .setParameter("id", memberId).uniqueResult();
/*     */ 
/* 476 */     Long pendIngOrder = (Long)
/* 477 */       getSession()
/* 478 */       .createQuery(
/* 479 */       "select count(*) from Order bean where bean.member.id=:id")
/* 480 */       .setParameter("id", memberId).uniqueResult();
/*     */ 
/* 482 */     Long proceOrder = (Long)
/* 483 */       getSession()
/* 484 */       .createQuery(
/* 485 */       "select count(*) from Order bean where bean.member.id=:id")
/* 486 */       .setParameter("id", memberId).uniqueResult();
/*     */ 
/* 488 */     Integer[] orders = new Integer[5];
/* 489 */     orders[0] = Integer.valueOf(succOrder.intValue());
/* 490 */     orders[1] = Integer.valueOf(failOrder.intValue());
/* 491 */     orders[2] = Integer.valueOf(totalOrder.intValue());
/* 492 */     orders[3] = Integer.valueOf(pendIngOrder.intValue());
/* 493 */     orders[4] = Integer.valueOf(proceOrder.intValue());
/* 494 */     return orders;
/*     */   }
/*     */ 
/*     */   public Pagination getOrderByReturn(Long memberId, Integer pageNo, Integer pageSize)
/*     */   {
/* 500 */     Finder f = 
/* 501 */       Finder.create("from Order bean where bean.member.id=:id and bean.status=41");
/* 502 */     f.setParam("id", memberId);
/* 503 */     return find(f, pageNo.intValue(), pageSize.intValue());
/*     */   }
/*     */ 
/*     */   public Order findById(Long id)
/*     */   {
/* 508 */     Finder entity = Finder.create("from Order bean where bean.id=:id");
/* 509 */     entity.setParam("id", id);
/* 510 */     List list = find(entity);
/* 511 */     if (list.size() == 1) {
/* 512 */       return (Order)list.get(0);
/*     */     }
/* 514 */     return null;
/*     */   }
/*     */ 
/*     */   public Order findByCode(Long code)
/*     */   {
/* 520 */     Finder f = Finder.create("from Order bean where bean.code=:code")
/* 521 */       .setParam("code", code);
/* 522 */     List list = find(f);
/* 523 */     if (list.size() == 1) {
/* 524 */       return (Order)list.get(0);
/*     */     }
/* 526 */     return null;
/*     */   }
/*     */ 
/*     */   public Order save(Order bean)
/*     */   {
/* 532 */     getSession().save(bean);
/* 533 */     return bean;
/*     */   }
/*     */ 
/*     */   public Order deleteById(Long id)
/*     */   {
/* 538 */     Order entity = (Order)super.get(id);
/* 539 */     if (entity != null) {
/* 540 */       getSession().delete(entity);
/*     */     }
/* 542 */     return entity;
/*     */   }
/*     */ 
/*     */   public Order updateById(Long id)
/*     */   {
/* 547 */     Order entity = (Order)super.get(id);
/* 548 */     if (entity != null) {
/* 549 */       entity.setDelStatus(Boolean.valueOf(false));
/*     */     }
/* 551 */     return entity;
/*     */   }
/*     */ 
/*     */   protected Class<Order> getEntityClass()
/*     */   {
/* 557 */     return Order.class;
/*     */   }
/*     */ 
/*     */   public List<Order> getCountByStatus(Date startTime, Date endTime, Integer status)
/*     */   {
/* 563 */     Finder finder = 
/* 564 */       Finder.create("SELECT DATE_FORMAT(createTime, '%Y-%m-%d' ), COUNT(*) from Order bean where 1=1");
/*     */ 
/* 566 */     if (startTime != null) {
/* 567 */       finder.append(" and bean.createTime>:startTime");
/* 568 */       finder.setParam("startTime", startTime);
/*     */     }
/* 570 */     if (endTime != null) {
/* 571 */       finder.append(" and bean.createTime<=:endTime");
/* 572 */       finder.setParam("endTime", endTime);
/*     */     }
/*     */ 
/* 575 */     if (status != null) {
/* 576 */       finder.append(" and bean.status=:status");
/* 577 */       finder.setParam("status", status);
/*     */     }
/*     */ 
/* 580 */     finder.append(" GROUP BY DATE_FORMAT( createTime, '%Y-%m-%d' )");
/*     */ 
/* 582 */     return find(finder);
/*     */   }
/*     */ 
/*     */   public List<Order> getCountByStatus1(Date startTime, Date endTime, Integer status)
/*     */   {
/* 587 */     Finder finder = 
/* 588 */       Finder.create("SELECT DATE_FORMAT(createTime, '%Y-%m-%d' ), sum(bean.total) from Order bean where 1=1");
/*     */ 
/* 590 */     if (startTime != null) {
/* 591 */       finder.append(" and bean.createTime>:startTime");
/* 592 */       finder.setParam("startTime", startTime);
/*     */     }
/* 594 */     if (endTime != null) {
/* 595 */       finder.append(" and bean.createTime<=:endTime");
/* 596 */       finder.setParam("endTime", endTime);
/*     */     }
/*     */ 
/* 599 */     if (status != null) {
/* 600 */       finder.append(" and bean.status=:status");
/* 601 */       finder.setParam("status", status);
/*     */     }
/*     */ 
/* 604 */     finder.append(" GROUP BY DATE_FORMAT( createTime, '%Y-%m-%d' )");
/*     */ 
/* 606 */     return find(finder);
/*     */   }
/*     */ 
/*     */   public List<Order> getStatisticByYear(Integer year, Integer status)
/*     */   {
/* 612 */     Finder finder = 
/* 613 */       Finder.create("SELECT bean.createTime,COUNT(*) from Order bean where 1=1");
/* 614 */     if (status != null) {
/* 615 */       finder.append(" and bean.status=:status");
/* 616 */       finder.setParam("status", status);
/*     */     }
/* 618 */     if (year != null) {
/* 619 */       String y = year.toString();
/* 620 */       finder.append(" and DATE_FORMAT(bean.createTime,'%Y') =:year");
/* 621 */       finder.setParam("year", y);
/*     */     }
/* 623 */     finder.append(" GROUP BY DATE_FORMAT(bean.createTime,'%Y%m')");
/* 624 */     return find(finder);
/*     */   }
/*     */ 
/*     */   public List<Order> getStatisticByYear1(Integer year, Integer status)
/*     */   {
/* 630 */     Finder finder = 
/* 631 */       Finder.create("SELECT bean.createTime,sum(bean.total) from Order bean where 1=1");
/* 632 */     if (status != null) {
/* 633 */       finder.append(" and bean.status=:status");
/* 634 */       finder.setParam("status", status);
/*     */     }
/* 636 */     if (year != null) {
/* 637 */       String y = year.toString();
/* 638 */       finder.append(" and DATE_FORMAT(bean.createTime,'%Y') =:year");
/* 639 */       finder.setParam("year", y);
/*     */     }
/* 641 */     finder.append(" GROUP BY DATE_FORMAT(bean.createTime,'%Y%m')");
/* 642 */     return find(finder);
/*     */   }
/*     */ 
/*     */   public List<Order> getOrderList(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Long code, int firstResult, int maxResults)
/*     */   {
/* 650 */     Finder f = Finder.create("from Order bean where 1=1");
/* 651 */     if (webId != null) {
/* 652 */       f.append(" and bean.website.id=:webId");
/* 653 */       f.setParam("webId", webId);
/*     */     }
/* 655 */     if (memberId != null) {
/* 656 */       f.append(" and bean.member.id=:memberId");
/* 657 */       f.setParam("memberId", memberId);
/*     */     }
/* 659 */     if (!StringUtils.isBlank(productName)) {
/* 660 */       f.append(" and bean.productName like:productName");
/* 661 */       f.setParam("productName", "%" + productName + "%");
/*     */     }
/* 663 */     if (!StringUtils.isBlank("userName")) {
/* 664 */       f.append(" and bean.receiveName like:userName");
/* 665 */       f.setParam("userName", "%" + userName + "%");
/*     */     }
/* 667 */     if (paymentId != null) {
/* 668 */       f.append("and bean.payment.id=:paymentId");
/* 669 */       f.setParam("paymentId", paymentId);
/*     */     }
/* 671 */     if (shippingId != null) {
/* 672 */       f.append(" and bean.shipping.id=:shippingId");
/* 673 */       f.setParam("shippingId", shippingId);
/*     */     }
/* 675 */     if (startTime != null) {
/* 676 */       f.append(" and bean.createTime>:startTime");
/* 677 */       f.setParam("startTime", startTime);
/*     */     }
/* 679 */     if (endTime != null) {
/* 680 */       f.append(" and bean.createTime<:endTime");
/* 681 */       f.setParam("endTime", endTime);
/*     */     }
/* 683 */     if (startOrderTotal != null) {
/* 684 */       f.append(" and bean.total>=:startOrderTotal");
/* 685 */       f.setParam("startOrderTotal", startOrderTotal);
/*     */     }
/* 687 */     if (endOrderTotal != null) {
/* 688 */       f.append(" and bean.total<=:endOrderTotal");
/* 689 */       f.setParam("endOrderTotal", endOrderTotal);
/*     */     }
/* 691 */     if (status != null) {
/* 692 */       f.append(" and bean.status=:status");
/* 693 */       f.setParam("status", status);
/*     */     }
/* 695 */     f.setFirstResult(firstResult);
/* 696 */     f.setMaxResults(maxResults);
/* 697 */     f.append(" order by bean.id desc");
/* 698 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<Object[]> findListByIds(Long[] ids) {
/* 702 */     String sql = "select t2.username,sum(1) from jc_shop_order t left join jc_core_member t1 on t1.member_id = t.member_id left join jc_core_user t2 on t1.user_id = t2.user_id where t1.member_id in (" + 
/* 703 */       StringUtils.join(ids, ",") + ")  group by t2.username";
/* 704 */     return getSession().createSQLQuery(sql).list();
/*     */   }
/*     */ 
/*     */   public BigDecimal getOrderSale(Date date, Long webId)
/*     */   {
/* 721 */     Finder finder = Finder.create("select sum(total) from Order bean where bean.createTime >=:startTime and bean.createTime <= :endTime and  bean.status = 4 and bean.delStatus = 1  and bean.website.id=:webId");
/* 722 */     finder.setParam("startTime", DateUtils.getStartDate(date));
/* 723 */     finder.setParam("endTime", DateUtils.getFinallyDate(date));
/* 724 */     finder.setParam("webId", webId);
/* 725 */     List list = find(finder);
/* 726 */     return new BigDecimal(list.get(0) != null ? ((Double)list.get(0)).doubleValue() : 0.0D);
/*     */   }
/*     */ 
/*     */   public Long getOrderCount(Date date, Long webId)
/*     */   {
/* 732 */     Finder finder = Finder.create("select Count(1) from Order bean where bean.createTime >=:startTime and bean.createTime <= :endTime and bean.delStatus = 1 and bean.website.id=:webId ");
/* 733 */     finder.setParam("startTime", DateUtils.getStartDate(date));
/* 734 */     finder.setParam("endTime", DateUtils.getFinallyDate(date));
/* 735 */     finder.setParam("webId", webId);
/* 736 */     List list = find(finder);
/* 737 */     return (Long)list.get(0);
/*     */   }
/*     */ 
/*     */   public Long getUnSendOrderCount(Long webId)
/*     */   {
/* 743 */     Finder finder = Finder.create("select Count(1) from Order bean where  bean.delStatus = 1  and bean.shippingStatus = 1 and   bean.website.id=:webId");
/* 744 */     finder.setParam("webId", webId);
/* 745 */     List list = find(finder);
/* 746 */     return (Long)list.get(0);
/*     */   }
/*     */ 
/*     */   public Long getUnPayOrderCount(Long webId)
/*     */   {
/* 752 */     Finder finder = Finder.create("select Count(1) from Order bean where  bean.delStatus = 1  and bean.paymentStatus = 1 and bean.website.id=:webId");
/* 753 */     finder.setParam("webId", webId);
/* 754 */     List list = find(finder);
/* 755 */     return (Long)list.get(0);
/*     */   }
/*     */ 
/*     */   public Long getReturnCount(Long webId)
/*     */   {
/* 761 */     Finder finder = Finder.create("select Count(1) from Order bean where  bean.returnOrder.id is not null and bean.returnOrder.status < 7   and bean.delStatus = 1 and bean.website.id=:webId ");
/* 762 */     finder.setParam("webId", webId);
/* 763 */     List list = find(finder);
/* 764 */     return (Long)list.get(0);
/*     */   }
/*     */ 
/*     */   public List getOrderSale(Long webId, String type, String month, String year)
/*     */     throws ParseException
/*     */   {
/* 770 */     Finder finder = Finder.create("select sum(bean.total), count(bean.id) ");
/*     */ 
/* 772 */     if ("month".equals(type))
/* 773 */       finder.append(" , day(bean.createTime)");
/* 774 */     else if ("year".equals(type)) {
/* 775 */       finder.append(" , month(bean.createTime)");
/*     */     }
/*     */ 
/* 778 */     finder.append(" from Order bean  where bean.status = 4");
/*     */ 
/* 780 */     Date date = new Date();
/*     */ 
/* 782 */     if (type.equals("month")) {
/* 783 */       if (StringUtils.isNotEmpty(month)) {
/* 784 */         date = DateUtils.pasreToDate(month, DateUtils.COMMON_FORMAT_MONTH);
/*     */       }
/* 786 */       finder.append(" and bean.createTime >=:startTime");
/* 787 */       finder.setParam("startTime", DateUtils.getSpecficMonthStart(date, 0));
/* 788 */       finder.append(" and bean.createTime <=:endTime");
/* 789 */       finder.setParam("endTime", DateUtils.getSpecficMonthEnd(date, 0));
/* 790 */     } else if (type.equals("year")) {
/* 791 */       if (StringUtils.isNotEmpty(year)) {
/* 792 */         date = DateUtils.pasreToDate(year, DateUtils.COMMON_FORMAT_YEAR);
/*     */       }
/* 794 */       finder.append(" and bean.createTime >=:startTime");
/* 795 */       finder.setParam("startTime", DateUtils.getSpecficYearStart(date, 0));
/* 796 */       finder.append(" and bean.createTime <=:endTime");
/* 797 */       finder.setParam("endTime", DateUtils.getSpecficYearEnd(date, 0));
/*     */     }
/*     */ 
/* 801 */     if ("month".equals(type))
/* 802 */       finder.append(" group by  day(bean.createTime)");
/* 803 */     else if ("year".equals(type)) {
/* 804 */       finder.append(" group by month(bean.createTime)");
/*     */     }
/* 806 */     return find(finder);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.OrderDaoImpl
 * JD-Core Version:    0.6.0
 */