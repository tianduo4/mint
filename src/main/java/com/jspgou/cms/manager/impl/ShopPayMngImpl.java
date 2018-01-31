/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopPayDao;
/*    */ import com.jspgou.cms.entity.ShopPay;
/*    */ import com.jspgou.cms.manager.ShopPayMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopPayMngImpl
/*    */   implements ShopPayMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private ShopPayDao dao;
/*    */ 
/*    */   public ShopPay deleteById(Integer id)
/*    */   {
/* 22 */     return this.dao.deleteById(id);
/*    */   }
/*    */ 
/*    */   public ShopPay[] deleteByIds(Integer[] ids)
/*    */   {
/* 27 */     ShopPay[] beans = new ShopPay[ids.length];
/* 28 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 29 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 31 */     return beans;
/*    */   }
/*    */ 
/*    */   public ShopPay findById(Integer id)
/*    */   {
/* 37 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   public ShopPay save(ShopPay bean)
/*    */   {
/* 43 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   public ShopPay updateByUpdater(ShopPay bean)
/*    */   {
/* 50 */     Updater updater = new Updater(bean);
/*    */ 
/* 52 */     return this.dao.updateByUpdater(updater);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopPayMngImpl
 * JD-Core Version:    0.6.0
 */