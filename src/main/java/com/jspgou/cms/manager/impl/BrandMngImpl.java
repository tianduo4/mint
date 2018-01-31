/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.BrandDao;
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.BrandText;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.BrandTextMng;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class BrandMngImpl
/*     */   implements BrandMng
/*     */ {
/*     */   private ProductTypeMng productTypeMng;
/*     */   private BrandTextMng brandTextMng;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */   private BrandDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Brand> getAllList()
/*     */   {
/*  30 */     List list = this.dao.getAllList();
/*  31 */     return list;
/*     */   }
/*     */ 
/*     */   public List<Brand> getList()
/*     */   {
/*  37 */     return this.dao.getList();
/*     */   }
/*     */ 
/*     */   public List<Brand> getListForTag(Long webId, int firstResult, int maxResults)
/*     */   {
/*  42 */     return this.dao.getList(webId, firstResult, maxResults, true);
/*     */   }
/*     */ 
/*     */   public Brand getsiftBrand()
/*     */   {
/*  47 */     return this.dao.getsiftBrand();
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Brand findById(Long id) {
/*  53 */     Brand entity = this.dao.findById(id);
/*  54 */     return entity;
/*     */   }
/*     */ 
/*     */   public Brand save(Brand bean, String text)
/*     */   {
/*  59 */     Brand entity = this.dao.save(bean);
/*  60 */     this.brandTextMng.save(entity, text);
/*  61 */     return entity;
/*     */   }
/*     */ 
/*     */   public Brand update(Brand bean, String text)
/*     */   {
/*  66 */     Updater updater = new Updater(bean);
/*  67 */     Brand entity = this.dao.updateByUpdater(updater);
/*     */ 
/*  69 */     entity.getBrandText().setText(text);
/*  70 */     return entity;
/*     */   }
/*     */ 
/*     */   public Brand deleteById(Long id)
/*     */   {
/*  75 */     Brand entity = findById(id);
/*  76 */     entity = this.dao.deleteById(id);
/*  77 */     return entity;
/*     */   }
/*     */ 
/*     */   public Brand[] deleteByIds(Long[] ids)
/*     */   {
/*  82 */     Brand[] beans = new Brand[ids.length];
/*  83 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  84 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  86 */     return beans;
/*     */   }
/*     */ 
/*     */   public Brand[] updatePriority(Long[] ids, Integer[] priority)
/*     */   {
/*  91 */     Brand[] beans = new Brand[ids.length];
/*  92 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  93 */       beans[i] = findById(ids[i]);
/*  94 */       beans[i].setPriority(priority[i]);
/*     */     }
/*  96 */     return beans;
/*     */   }
/*     */ 
/*     */   public boolean brandNameNotExist(String brandName)
/*     */   {
/* 101 */     return this.dao.countByBrandName(brandName) <= 0;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setProductTypeMng(ProductTypeMng productTypeMng)
/*     */   {
/* 113 */     this.productTypeMng = productTypeMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setBrandTextMng(BrandTextMng brandTextMng) {
/* 118 */     this.brandTextMng = brandTextMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setDao(BrandDao dao) {
/* 123 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public Pagination getPage(String name, String brandtype, int pageNo, int pageSize)
/*     */   {
/* 128 */     Pagination page = this.dao.getPage(name, brandtype, pageNo, pageSize);
/* 129 */     return page;
/*     */   }
/*     */ 
/*     */   public List<Brand> getBrandList(String name)
/*     */   {
/* 135 */     return this.dao.getBrandList(name);
/*     */   }
/*     */ 
/*     */   public List<Brand> getListByCateoryId(Integer cateoryId)
/*     */   {
/* 140 */     Category cate = this.categoryMng.findById(cateoryId);
/* 141 */     List list = new ArrayList(cate.getBrands());
/* 142 */     return list;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.BrandMngImpl
 * JD-Core Version:    0.6.0
 */