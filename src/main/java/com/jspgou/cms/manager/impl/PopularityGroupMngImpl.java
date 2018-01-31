/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.PopularityGroupDao;
/*    */ import com.jspgou.cms.entity.PopularityGroup;
/*    */ import com.jspgou.cms.entity.Product;
/*    */ import com.jspgou.cms.manager.PopularityGroupMng;
/*    */ import com.jspgou.cms.manager.ProductMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.Set;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class PopularityGroupMngImpl
/*    */   implements PopularityGroupMng
/*    */ {
/*    */   private PopularityGroupDao dao;
/*    */ 
/*    */   @Autowired
/*    */   private ProductMng productMng;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 20 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 21 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public PopularityGroup findById(Long id) {
/* 27 */     PopularityGroup entity = null;
/* 28 */     if (id != null) {
/* 29 */       entity = this.dao.findById(id);
/*    */     }
/* 31 */     return entity;
/*    */   }
/*    */ 
/*    */   public PopularityGroup save(PopularityGroup bean)
/*    */   {
/* 36 */     bean.init();
/* 37 */     this.dao.save(bean);
/* 38 */     return bean;
/*    */   }
/*    */ 
/*    */   public PopularityGroup update(PopularityGroup bean)
/*    */   {
/* 43 */     Updater updater = new Updater(bean);
/* 44 */     PopularityGroup entity = this.dao.updateByUpdater(updater);
/* 45 */     return entity;
/*    */   }
/*    */ 
/*    */   public PopularityGroup deleteById(Long id)
/*    */   {
/* 50 */     PopularityGroup bean = findById(id);
/* 51 */     bean.getProducts().clear();
/* 52 */     this.dao.deleteById(id);
/* 53 */     return bean;
/*    */   }
/*    */ 
/*    */   public PopularityGroup[] deleteByIds(Long[] ids)
/*    */   {
/* 58 */     PopularityGroup[] beans = new PopularityGroup[ids.length];
/* 59 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 60 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 62 */     return beans;
/*    */   }
/*    */ 
/*    */   public void addProduct(PopularityGroup bean, Long[] productIds)
/*    */   {
/* 67 */     double price = 0.0D;
/* 68 */     if (productIds != null) {
/* 69 */       for (Long productId : productIds) {
/* 70 */         bean.addToProducts(this.productMng.findById(productId));
/* 71 */         price += this.productMng.findById(productId).getPrice().doubleValue();
/*    */       }
/*    */     }
/* 74 */     bean.setPrice(Double.valueOf(price));
/*    */   }
/*    */ 
/*    */   public void updateProduct(PopularityGroup bean, Long[] productIds)
/*    */   {
/* 79 */     double price = 0.0D;
/* 80 */     bean.getProducts().clear();
/* 81 */     if (productIds != null) {
/* 82 */       for (Long productId : productIds) {
/* 83 */         bean.addToProducts(this.productMng.findById(productId));
/* 84 */         price += this.productMng.findById(productId).getPrice().doubleValue();
/*    */       }
/*    */     }
/* 87 */     bean.setPrice(Double.valueOf(price));
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(PopularityGroupDao dao)
/*    */   {
/* 96 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.PopularityGroupMngImpl
 * JD-Core Version:    0.6.0
 */