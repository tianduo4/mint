/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopConfigDao;
/*    */ import com.jspgou.cms.entity.ShopConfig;
/*    */ import com.jspgou.cms.manager.ShopConfigMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopConfigMngImpl
/*    */   implements ShopConfigMng
/*    */ {
/*    */   private ShopConfigDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ShopConfig findById(Long id)
/*    */   {
/* 22 */     ShopConfig entity = this.dao.findById(id);
/* 23 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopConfig save(ShopConfig bean)
/*    */   {
/* 28 */     this.dao.save(bean);
/* 29 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopConfig update(ShopConfig bean)
/*    */   {
/* 34 */     Updater updater = new Updater(bean);
/* 35 */     ShopConfig entity = this.dao.updateByUpdater(updater);
/* 36 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopConfig deleteById(Long id)
/*    */   {
/* 41 */     ShopConfig bean = this.dao.deleteById(id);
/* 42 */     return bean;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopConfigDao dao)
/*    */   {
/* 49 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopConfigMngImpl
 * JD-Core Version:    0.6.0
 */