/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.CollectDao;
/*     */ import com.jspgou.cms.entity.Collect;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.CollectMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class CollectMngImpl
/*     */   implements CollectMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng fashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private CollectDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getList(Integer pageSize, Integer pageNo, Long memberId)
/*     */   {
/*  28 */     Pagination list = this.dao.getList(pageSize, pageNo, memberId);
/*  29 */     return list;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Collect findById(Integer id) {
/*  35 */     Collect entity = this.dao.findById(id);
/*  36 */     return entity;
/*     */   }
/*     */ 
/*     */   public Collect save(ShopMember bean, Long productId, Long fashionId)
/*     */   {
/*  41 */     Collect entity = new Collect();
/*  42 */     entity.setProduct(this.productMng.findById(productId));
/*  43 */     if (fashionId != null) {
/*  44 */       entity.setFashion(this.fashionMng.findById(fashionId));
/*     */     }
/*  46 */     entity.setMember(bean);
/*  47 */     entity.setTime(new Date());
/*  48 */     this.dao.save(entity);
/*  49 */     return entity;
/*     */   }
/*     */ 
/*     */   public List<Collect> getList(Long memberId, int firstResult, int maxResults)
/*     */   {
/*  54 */     return this.dao.getList(memberId, firstResult, maxResults);
/*     */   }
/*     */ 
/*     */   public List<Collect> getList(Long productId, Long memberId)
/*     */   {
/*  59 */     return this.dao.getList(productId, memberId);
/*     */   }
/*     */ 
/*     */   public Collect update(Collect bean, Long pTypeid)
/*     */   {
/*  64 */     Updater updater = new Updater(bean);
/*  65 */     Collect entity = this.dao.updateByUpdater(updater);
/*     */ 
/*  67 */     return entity;
/*     */   }
/*     */ 
/*     */   public List<Collect> findByProductId(Long productId)
/*     */   {
/*  72 */     return this.dao.findByProductId(productId);
/*     */   }
/*     */ 
/*     */   public Collect findByProductFashionId(Long id)
/*     */   {
/*  77 */     return this.dao.findByProductFashionId(id);
/*     */   }
/*     */ 
/*     */   public Collect deleteById(Integer id)
/*     */   {
/*  84 */     Collect entity = findById(id);
/*     */ 
/*  86 */     entity = this.dao.deleteById(id);
/*  87 */     return entity;
/*     */   }
/*     */ 
/*     */   public Collect[] deleteByIds(Integer[] ids)
/*     */   {
/*  92 */     Collect[] beans = new Collect[ids.length];
/*  93 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  94 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  96 */     return beans;
/*     */   }
/*     */ 
/*     */   public void deleteByMemberId(Long memberId)
/*     */   {
/* 102 */     this.dao.deleteByMemberId(memberId);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.CollectMngImpl
 * JD-Core Version:    0.6.0
 */