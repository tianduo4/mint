/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.RelatedgoodsDao;
/*     */ import com.jspgou.cms.entity.Relatedgoods;
/*     */ import com.jspgou.cms.manager.RelatedgoodsMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class RelatedgoodsMngImpl
/*     */   implements RelatedgoodsMng
/*     */ {
/*     */   private RelatedgoodsDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/*  25 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/*  26 */     return page;
/*     */   }
/*     */ 
/*     */   public List<Relatedgoods> findByIdProductId(Long productId)
/*     */   {
/*  31 */     List entity = this.dao.findByIdProductId(productId);
/*  32 */     return entity;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Relatedgoods findById(Long id) {
/*  38 */     Relatedgoods entity = this.dao.findById(id);
/*  39 */     return entity;
/*     */   }
/*     */ 
/*     */   public Relatedgoods save(Relatedgoods bean)
/*     */   {
/*  44 */     this.dao.save(bean);
/*  45 */     return bean;
/*     */   }
/*     */ 
/*     */   public Relatedgoods update(Relatedgoods bean)
/*     */   {
/*  50 */     Updater updater = new Updater(bean);
/*  51 */     Relatedgoods entity = this.dao.updateByUpdater(updater);
/*  52 */     return entity;
/*     */   }
/*     */ 
/*     */   public Relatedgoods deleteById(Long id)
/*     */   {
/*  57 */     Relatedgoods bean = this.dao.deleteById(id);
/*  58 */     return bean;
/*     */   }
/*     */ 
/*     */   public Relatedgoods[] deleteByIds(Long[] ids)
/*     */   {
/*  63 */     Relatedgoods[] beans = new Relatedgoods[ids.length];
/*  64 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  65 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  67 */     return beans;
/*     */   }
/*     */ 
/*     */   public void addProduct(Long id, Long[] productIds)
/*     */   {
/*  72 */     if (productIds != null)
/*  73 */       for (Long productId : productIds)
/*  74 */         if (productId != null) {
/*  75 */           Relatedgoods r = new Relatedgoods();
/*  76 */           r.setProductId(id);
/*  77 */           r.setProductIds(productId);
/*  78 */           this.dao.save(r);
/*     */         }
/*     */   }
/*     */ 
/*     */   public void updateProduct(Long id, Long[] productIds)
/*     */   {
/*  86 */     List Relatedgoods = this.dao.findByIdProductId(id);
/*  87 */     for (int i = 0; i < Relatedgoods.size(); i++) {
/*  88 */       deleteById(((Relatedgoods)Relatedgoods.get(i)).getId());
/*     */     }
/*  90 */     if (productIds != null)
/*  91 */       for (Long productId : productIds)
/*  92 */         if (productId != null) {
/*  93 */           Relatedgoods r = new Relatedgoods();
/*  94 */           r.setProductId(id);
/*  95 */           r.setProductIds(productId);
/*  96 */           this.dao.save(r);
/*     */         }
/*     */   }
/*     */ 
/*     */   public void deleteProduct(Long productid)
/*     */   {
/* 104 */     List Relatedgoods = this.dao.findByIdProductId(productid);
/* 105 */     for (int i = 0; i < Relatedgoods.size(); i++)
/* 106 */       deleteById(((Relatedgoods)Relatedgoods.get(i)).getId());
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(RelatedgoodsDao dao)
/*     */   {
/* 114 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.RelatedgoodsMngImpl
 * JD-Core Version:    0.6.0
 */