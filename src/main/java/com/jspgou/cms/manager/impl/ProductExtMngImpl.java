/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductExtDao;
/*    */ import com.jspgou.cms.entity.Product;
/*    */ import com.jspgou.cms.entity.ProductExt;
/*    */ import com.jspgou.cms.manager.ProductExtMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ProductExtMngImpl
/*    */   implements ProductExtMng
/*    */ {
/*    */   private ProductExtDao dao;
/*    */ 
/*    */   public ProductExt save(ProductExt ext, Product product)
/*    */   {
/* 24 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
/* 25 */     long code = Long.parseLong(sdf.format(new Date())) + product.getId().longValue();
/* 26 */     ext.setCode(Long.valueOf(code));
/* 27 */     ext.setProduct(product);
/* 28 */     ext.init();
/* 29 */     this.dao.save(ext);
/* 30 */     return ext;
/*    */   }
/*    */ 
/*    */   public ProductExt saveOrUpdate(ProductExt ext, Product product)
/*    */   {
/* 35 */     ProductExt entity = this.dao.findById(ext.getId());
/* 36 */     if (entity != null) {
/* 37 */       Updater updater = new Updater(ext);
/* 38 */       this.dao.updateByUpdater(updater);
/* 39 */       return entity;
/*    */     }
/* 41 */     return save(ext, product);
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ProductExtDao dao)
/*    */   {
/* 49 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ProductExtMngImpl
 * JD-Core Version:    0.6.0
 */