/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductTypeDao;
/*    */ import com.jspgou.cms.entity.ProductType;
/*    */ import com.jspgou.cms.manager.BrandMng;
/*    */ import com.jspgou.cms.manager.ProductTypeMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ProductTypeMngImpl
/*    */   implements ProductTypeMng
/*    */ {
/*    */   private BrandMng brandMng;
/*    */   private ProductTypeDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<ProductType> getList(Long webId)
/*    */   {
/* 27 */     return this.dao.getList(webId);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ProductType findById(Long id) {
/* 33 */     ProductType entity = this.dao.findById(id);
/* 34 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductType save(ProductType bean)
/*    */   {
/* 39 */     this.dao.save(bean);
/* 40 */     return bean;
/*    */   }
/*    */ 
/*    */   public ProductType update(ProductType bean)
/*    */   {
/* 45 */     ProductType entity = this.dao
/* 46 */       .updateByUpdater(new Updater(bean));
/* 47 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductType deleteById(Long id)
/*    */   {
/* 52 */     ProductType entity = this.dao.deleteById(id);
/* 53 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductType[] deleteByIds(Long[] ids)
/*    */   {
/* 58 */     ProductType[] beans = new ProductType[ids.length];
/* 59 */     for (int i = 0; i < ids.length; i++) {
/* 60 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 62 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setBrandMng(BrandMng brandMng)
/*    */   {
/* 70 */     this.brandMng = brandMng;
/*    */   }
/*    */   @Autowired
/*    */   public void setDao(ProductTypeDao dao) {
/* 75 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public Pagination getPage(Long webId, int pageNo, int pageSize)
/*    */   {
/* 81 */     return this.dao.getPage(webId, pageNo, pageSize);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ProductTypeMngImpl
 * JD-Core Version:    0.6.0
 */