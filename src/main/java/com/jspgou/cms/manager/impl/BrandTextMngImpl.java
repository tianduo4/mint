/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.BrandTextDao;
/*    */ import com.jspgou.cms.entity.Brand;
/*    */ import com.jspgou.cms.entity.BrandText;
/*    */ import com.jspgou.cms.manager.BrandTextMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class BrandTextMngImpl
/*    */   implements BrandTextMng
/*    */ {
/*    */   private BrandTextDao dao;
/*    */ 
/*    */   public BrandText save(Brand brand, String text)
/*    */   {
/* 21 */     BrandText bean = new BrandText();
/* 22 */     bean.setBrand(brand);
/* 23 */     bean.setText(text);
/* 24 */     this.dao.save(bean);
/* 25 */     return bean;
/*    */   }
/*    */ 
/*    */   public BrandText update(BrandText bean)
/*    */   {
/* 30 */     BrandText entity = this.dao.updateByUpdater(new Updater(bean));
/* 31 */     return entity;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(BrandTextDao dao)
/*    */   {
/* 38 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.BrandTextMngImpl
 * JD-Core Version:    0.6.0
 */