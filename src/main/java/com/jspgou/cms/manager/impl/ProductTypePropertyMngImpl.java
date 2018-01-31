/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ProductTypePropertyDao;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.ProductTypeProperty;
/*     */ import com.jspgou.cms.manager.ProductTypePropertyMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ProductTypePropertyMngImpl
/*     */   implements ProductTypePropertyMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypePropertyDao dao;
/*     */ 
/*     */   public ProductTypeProperty deleteById(Long id)
/*     */   {
/*  28 */     return this.dao.deleteById(id);
/*     */   }
/*     */ 
/*     */   public ProductTypeProperty[] deleteByIds(Long[] ids)
/*     */   {
/*  33 */     ProductTypeProperty[] beans = new ProductTypeProperty[ids.length];
/*  34 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  35 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  37 */     return beans;
/*     */   }
/*     */ 
/*     */   public ProductTypeProperty findById(Long id)
/*     */   {
/*  42 */     return this.dao.findById(id);
/*     */   }
/*     */ 
/*     */   public Pagination getList(int pageNo, int pageSize, Long typeid, Boolean isCategory, String searchType, String searchContent)
/*     */   {
/*  48 */     return this.dao.getList(pageNo, pageSize, typeid, isCategory, searchType, searchContent);
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> getList(Long typeId, Boolean isCategory)
/*     */   {
/*  53 */     return this.dao.getList(typeId, isCategory);
/*     */   }
/*     */ 
/*     */   public void saveList(List<ProductTypeProperty> list)
/*     */   {
/*  58 */     for (ProductTypeProperty item : list)
/*  59 */       save(item);
/*     */   }
/*     */ 
/*     */   public ProductTypeProperty save(ProductTypeProperty bean)
/*     */   {
/*  65 */     return this.dao.save(bean);
/*     */   }
/*     */ 
/*     */   public void updatePriority(Long[] wids, Integer[] sort, String[] propertyName, Boolean[] single)
/*     */   {
/*  72 */     int i = 0; for (int len = wids.length; i < len; i++) {
/*  73 */       ProductTypeProperty item = findById(wids[i]);
/*  74 */       item.setPropertyName(propertyName[i]);
/*  75 */       item.setSort(sort[i]);
/*  76 */       item.setSingle(single[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> findBySearch(String searchType, String searchContent)
/*     */   {
/*  83 */     return this.dao.findBySearch(searchType, searchContent);
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> findListByTypeId(Long typeid)
/*     */   {
/*  88 */     return this.dao.findListByTypeId(typeid);
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> getList(Long typeId, boolean isCategory)
/*     */   {
/*  93 */     return this.dao.getList(typeId, isCategory);
/*     */   }
/*     */ 
/*     */   public ProductTypeProperty update(ProductTypeProperty bean)
/*     */   {
/*  98 */     Updater updater = new Updater(bean);
/*  99 */     ProductTypeProperty entity = this.dao.updateByUpdater(updater);
/* 100 */     return entity;
/*     */   }
/*     */ 
/*     */   public List<ProductTypeProperty> getItems(ProductType model, boolean isCategory, String[] fields, String[] propertyNames, Integer[] dataTypes, Integer[] sorts, Boolean[] singles) {
/* 104 */     return this.dao.getItems(model, isCategory, fields, propertyNames, dataTypes, sorts, singles);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ProductTypePropertyMngImpl
 * JD-Core Version:    0.6.0
 */