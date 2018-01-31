/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductTextDao;
/*    */ import com.jspgou.cms.entity.ProductText;
/*    */ import com.jspgou.cms.manager.ProductTextMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ProductTextMngImpl
/*    */   implements ProductTextMng
/*    */ {
/*    */   private ProductTextDao dao;
/*    */ 
/*    */   public ProductText update(ProductText bean)
/*    */   {
/* 21 */     Updater updater = new Updater(bean);
/* 22 */     ProductText entity = this.dao.updateByUpdater(updater);
/* 23 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductText save(ProductText bean)
/*    */   {
/* 28 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ProductTextDao dao)
/*    */   {
/* 35 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ProductTextMngImpl
 * JD-Core Version:    0.6.0
 */