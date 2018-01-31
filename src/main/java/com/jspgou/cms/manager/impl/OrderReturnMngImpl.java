/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.OrderReturnDao;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderReturn;
/*     */ import com.jspgou.cms.entity.OrderReturn.OrderReturnStatus;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.OrderReturnMng;
/*     */ import com.jspgou.cms.manager.ShopDictionaryMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class OrderReturnMngImpl
/*     */   implements OrderReturnMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderReturnDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryMng shopDictionaryMng;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Integer status, int pageNo, int pageSize)
/*     */   {
/*  30 */     Pagination page = this.dao.getPage(status, pageNo, pageSize);
/*  31 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public OrderReturn findById(Long id) {
/*  37 */     OrderReturn entity = this.dao.findById(id);
/*  38 */     return entity;
/*     */   }
/*     */ 
/*     */   public OrderReturn findByOrderId(Long orderId)
/*     */   {
/*  43 */     List list = this.dao.findByOrderId(orderId);
/*  44 */     if (list.size() > 0) {
/*  45 */       return (OrderReturn)list.get(0);
/*     */     }
/*  47 */     return null;
/*     */   }
/*     */ 
/*     */   public OrderReturn save(OrderReturn bean)
/*     */   {
/*  53 */     this.dao.save(bean);
/*  54 */     return bean;
/*     */   }
/*     */ 
/*     */   public OrderReturn save(OrderReturn bean, Order order, Long reasonId, Boolean delivery, String[] picPaths, String[] picDescs)
/*     */   {
/*  59 */     bean.setOrder(order);
/*  60 */     bean.setShopDictionary(this.shopDictionaryMng.findById(reasonId));
/*  61 */     if (delivery.booleanValue())
/*  62 */       bean.setReturnType(Integer.valueOf(OrderReturn.OrderReturnStatus.SELLER_NODELIVERY_RETURN.getValue()));
/*     */     else {
/*  64 */       bean.setReturnType(Integer.valueOf(OrderReturn.OrderReturnStatus.SELLER_DELIVERY_RETURN.getValue()));
/*     */     }
/*  66 */     Long date = Long.valueOf(new Date().getTime() + order.getId().longValue());
/*  67 */     bean.setCode(String.valueOf(date));
/*  68 */     bean.setStatus(Integer.valueOf(1));
/*  69 */     bean.setCreateTime(new Date());
/*     */ 
/*  71 */     if ((picPaths != null) && (picPaths.length > 0)) {
/*  72 */       int i = 0; for (int len = picPaths.length; i < len; i++) {
/*  73 */         if (!StringUtils.isBlank(picPaths[i])) {
/*  74 */           bean.addToPictures(picPaths[i], picDescs[i]);
/*     */         }
/*     */       }
/*     */     }
/*  78 */     bean = this.dao.save(bean);
/*  79 */     return bean;
/*     */   }
/*     */ 
/*     */   public OrderReturn update(OrderReturn bean)
/*     */   {
/*  84 */     Updater updater = new Updater(bean);
/*  85 */     OrderReturn entity = this.dao.updateByUpdater(updater);
/*  86 */     return entity;
/*     */   }
/*     */ 
/*     */   public OrderReturn deleteById(Long id)
/*     */   {
/*  92 */     Order order = this.dao.findById(id).getOrder();
/*  93 */     order.setReturnOrder(null);
/*  94 */     this.orderMng.updateByUpdater(order);
/*  95 */     OrderReturn bean = this.dao.deleteById(id);
/*  96 */     return bean;
/*     */   }
/*     */ 
/*     */   public OrderReturn[] deleteByIds(Long[] ids)
/*     */   {
/* 101 */     OrderReturn[] beans = new OrderReturn[ids.length];
/* 102 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 103 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 105 */     return beans;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(OrderReturnDao dao)
/*     */   {
/* 117 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.OrderReturnMngImpl
 * JD-Core Version:    0.6.0
 */