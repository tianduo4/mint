/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.PaymentDao;
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.manager.PaymentMng;
/*     */ import com.jspgou.cms.manager.ShippingMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class PaymentMngImpl
/*     */   implements PaymentMng
/*     */ {
/*     */   private PaymentDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng shippingMng;
/*     */ 
/*     */   public List<Payment> getListForCart(Long webId)
/*     */   {
/*  23 */     return this.dao.getListForCart(webId, true);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Payment> getList(Long webId, boolean all) {
/*  29 */     return this.dao.getList(webId, all);
/*     */   }
/*     */ 
/*     */   public Payment[] updatePriority(Long[] ids, Integer[] priority)
/*     */   {
/*  34 */     Payment[] beans = new Payment[ids.length];
/*  35 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  36 */       beans[i] = findById(ids[i]);
/*  37 */       beans[i].setPriority(priority[i]);
/*     */     }
/*  39 */     return beans;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Payment> getByCode(String code, Long webId) {
/*  45 */     return this.dao.getByCode(code, webId);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Payment findById(Long id) {
/*  51 */     Payment entity = this.dao.findById(id);
/*  52 */     return entity;
/*     */   }
/*     */ 
/*     */   public Payment save(Payment bean)
/*     */   {
/*  57 */     this.dao.save(bean);
/*  58 */     return bean;
/*     */   }
/*     */ 
/*     */   public Payment update(Payment bean)
/*     */   {
/*  63 */     Updater updater = new Updater(bean);
/*  64 */     Payment entity = this.dao.updateByUpdater(updater);
/*  65 */     return entity;
/*     */   }
/*     */ 
/*     */   public Payment deleteById(Long id)
/*     */   {
/*  70 */     Payment bean = this.dao.deleteById(id);
/*  71 */     return bean;
/*     */   }
/*     */ 
/*     */   public Payment[] deleteByIds(Long[] ids)
/*     */   {
/*  76 */     Payment[] beans = new Payment[ids.length];
/*  77 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  78 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  80 */     return beans;
/*     */   }
/*     */ 
/*     */   public void addShipping(Payment Payment, long[] shippingIds)
/*     */   {
/*  89 */     if (shippingIds != null)
/*  90 */       for (long shippingId : shippingIds)
/*  91 */         Payment.addToShippings(this.shippingMng.findById(Long.valueOf(shippingId)));
/*     */   }
/*     */ 
/*     */   public void updateShipping(Payment Payment, long[] shippingIds)
/*     */   {
/*  99 */     Payment.getShippings().clear();
/* 100 */     if (shippingIds != null)
/* 101 */       for (long shippingId : shippingIds)
/* 102 */         Payment.addToShippings(this.shippingMng.findById(Long.valueOf(shippingId)));
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(PaymentDao dao)
/*     */   {
/* 116 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.PaymentMngImpl
 * JD-Core Version:    0.6.0
 */