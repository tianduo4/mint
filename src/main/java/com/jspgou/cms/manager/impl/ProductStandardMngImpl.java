/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductStandardDao;
/*    */ import com.jspgou.cms.entity.ProductStandard;
/*    */ import com.jspgou.cms.manager.ProductStandardMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ProductStandardMngImpl
/*    */   implements ProductStandardMng
/*    */ {
/*    */   private ProductStandardDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 24 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 25 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ProductStandard findById(Long id) {
/* 31 */     ProductStandard entity = this.dao.findById(id);
/* 32 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductStandard findByProductIdAndStandardId(Long productId, Long standardId)
/*    */   {
/* 37 */     List list = this.dao.findByProductIdAndStandardId(productId, standardId);
/* 38 */     if (list.size() > 0) {
/* 39 */       return (ProductStandard)list.get(0);
/*    */     }
/* 41 */     return null;
/*    */   }
/*    */ 
/*    */   public List<ProductStandard> findByProductId(Long productId)
/*    */   {
/* 47 */     return this.dao.findByProductId(productId);
/*    */   }
/*    */ 
/*    */   public ProductStandard save(ProductStandard bean)
/*    */   {
/* 52 */     this.dao.save(bean);
/* 53 */     return bean;
/*    */   }
/*    */ 
/*    */   public ProductStandard update(ProductStandard bean)
/*    */   {
/* 58 */     Updater updater = new Updater(bean);
/* 59 */     ProductStandard entity = this.dao.updateByUpdater(updater);
/* 60 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductStandard deleteById(Long id)
/*    */   {
/* 65 */     ProductStandard bean = this.dao.deleteById(id);
/* 66 */     return bean;
/*    */   }
/*    */ 
/*    */   public ProductStandard[] deleteByIds(Long[] ids)
/*    */   {
/* 71 */     ProductStandard[] beans = new ProductStandard[ids.length];
/* 72 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 73 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 75 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ProductStandardDao dao)
/*    */   {
/* 82 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ProductStandardMngImpl
 * JD-Core Version:    0.6.0
 */