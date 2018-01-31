/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ExendedDao;
/*     */ import com.jspgou.cms.entity.Exended;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.manager.ExendedMng;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ExendedMngImpl
/*     */   implements ExendedMng
/*     */ {
/*     */   private ExendedDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypeMng productTypeMng;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/*  29 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/*  30 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Exended findById(Long id) {
/*  36 */     Exended entity = this.dao.findById(id);
/*  37 */     return entity;
/*     */   }
/*     */ 
/*     */   public List<Exended> getList()
/*     */   {
/*  42 */     return this.dao.getList();
/*     */   }
/*     */ 
/*     */   public List<Exended> getList(Long typeId)
/*     */   {
/*  47 */     return this.dao.getList(typeId);
/*     */   }
/*     */ 
/*     */   public Exended save(Exended bean, Long[] typeIds)
/*     */   {
/*  52 */     bean = this.dao.save(bean);
/*  53 */     if ((typeIds != null) && (typeIds.length > 0)) {
/*  54 */       for (Long tid : typeIds) {
/*  55 */         this.productTypeMng.findById(tid).addToexendeds(bean);
/*     */       }
/*     */     }
/*  58 */     return bean;
/*     */   }
/*     */ 
/*     */   public Exended update(Exended bean, Long[] typeIds)
/*     */   {
/*  63 */     Updater updater = new Updater(bean);
/*  64 */     Exended entity = this.dao.updateByUpdater(updater);
/*  65 */     Set<ProductType> types = entity.getProductTypes();
/*  66 */     for (ProductType type : types) {
/*  67 */       type.removeFromExendeds(entity);
/*     */     }
/*  69 */     types.clear();
/*  70 */     if ((typeIds != null) && (typeIds.length > 0)) {
/*  71 */       for (Long tid : typeIds) {
/*  72 */         this.productTypeMng.findById(tid).addToexendeds(entity);
/*     */       }
/*     */     }
/*  75 */     return entity;
/*     */   }
/*     */ 
/*     */   public Exended deleteById(Long id)
/*     */   {
/*  80 */     Exended bean = this.dao.deleteById(id);
/*  81 */     return bean;
/*     */   }
/*     */ 
/*     */   public Exended[] deleteByIds(Long[] ids)
/*     */   {
/*  86 */     Exended[] beans = new Exended[ids.length];
/*  87 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  88 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  90 */     return beans;
/*     */   }
/*     */ 
/*     */   public Exended[] updatePriority(Long[] wids, Integer[] priority)
/*     */   {
/*  95 */     int len = wids.length;
/*  96 */     Exended[] beans = new Exended[len];
/*  97 */     for (int i = 0; i < len; i++) {
/*  98 */       beans[i] = findById(wids[i]);
/*  99 */       beans[i].setPriority(priority[i]);
/*     */     }
/* 101 */     return beans;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(ExendedDao dao)
/*     */   {
/* 111 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ExendedMngImpl
 * JD-Core Version:    0.6.0
 */