/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ProductFashionDao;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.StandardMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.image.ImageScale;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ProductFashionMngImpl
/*     */   implements ProductFashionMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   @Autowired
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*     */   @Autowired
/*     */   private ImageScale imageScale;
/*     */ 
/*     */   @Autowired
/*     */   private StandardMng standardMng;
/*     */ 
/*     */   public ProductFashion deleteById(Long id)
/*     */   {
/*  29 */     return this.dao.deleteById(id);
/*     */   }
/*     */ 
/*     */   public ProductFashion[] deleteByIds(Long[] ids)
/*     */   {
/*  34 */     ProductFashion[] beans = new ProductFashion[ids.length];
/*  35 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  36 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  38 */     return beans;
/*     */   }
/*     */ 
/*     */   public ProductFashion findById(Long id)
/*     */   {
/*  43 */     return this.dao.findById(id);
/*     */   }
/*     */ 
/*     */   public List<ProductFashion> findByProductId(Long productId)
/*     */   {
/*  48 */     return this.dao.findByProductId(productId);
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long productId, int pageNo, int pageSize)
/*     */   {
/*  53 */     return this.dao.getPage(productId, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public ProductFashion save(ProductFashion bean, String[] arr)
/*     */   {
/*  58 */     String attitude = "";
/*  59 */     for (String a : arr) {
/*  60 */       attitude = attitude + " " + this.standardMng.findById(Long.valueOf(Long.parseLong(a))).getName();
/*     */     }
/*  62 */     bean.setAttitude(attitude);
/*  63 */     bean.init();
/*  64 */     return this.dao.save(bean);
/*     */   }
/*     */ 
/*     */   public void addStandard(ProductFashion bean, String[] arr)
/*     */   {
/*  69 */     for (String a : arr)
/*  70 */       bean.addTostandards(this.standardMng.findById(Long.valueOf(Long.parseLong(a))));
/*     */   }
/*     */ 
/*     */   public void updateStandard(ProductFashion bean, String[] arr)
/*     */   {
/*  77 */     bean.getStandards().clear();
/*  78 */     for (String a : arr)
/*  79 */       bean.addTostandards(this.standardMng.findById(Long.valueOf(Long.parseLong(a))));
/*     */   }
/*     */ 
/*     */   public void deleteImage(ProductFashion entity, String uploadPath)
/*     */   {
/*     */   }
/*     */ 
/*     */   public ProductFashion update(ProductFashion bean, String[] arr)
/*     */   {
/* 100 */     String attitude = "";
/* 101 */     for (String a : arr) {
/* 102 */       attitude = attitude + " " + this.standardMng.findById(Long.valueOf(Long.parseLong(a))).getName();
/*     */     }
/* 104 */     bean.setAttitude(attitude);
/* 105 */     Updater updater = new Updater(bean);
/* 106 */     ProductFashion entity = this.dao.updateByUpdater(updater);
/* 107 */     return entity;
/*     */   }
/*     */ 
/*     */   public ProductFashion update(ProductFashion bean)
/*     */   {
/* 112 */     Updater updater = new Updater(bean);
/* 113 */     ProductFashion entity = this.dao.updateByUpdater(updater);
/* 114 */     return entity;
/*     */   }
/*     */ 
/*     */   public Pagination productLack(Integer status, Integer count, int pageNo, int pageSize)
/*     */   {
/* 119 */     return this.dao.productLack(status, count, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Integer productLackCount(Integer status, Integer count)
/*     */   {
/* 124 */     return this.dao.productLackCount(status, count);
/*     */   }
/*     */ 
/*     */   public Pagination getSaleTopPage(int pageNo, int pageSize)
/*     */   {
/* 129 */     return this.dao.getSaleTopPage(pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Boolean getOneFash(Long productId)
/*     */   {
/* 134 */     return this.dao.getOneFashion(productId);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ProductFashionMngImpl
 * JD-Core Version:    0.6.0
 */