/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.CartItemDao;
/*    */ import com.jspgou.cms.entity.CartItem;
/*    */ import com.jspgou.cms.manager.CartItemMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class CartItemMngImpl
/*    */   implements CartItemMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private CartItemDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public CartItem findById(Long id)
/*    */   {
/* 24 */     CartItem entity = this.dao.findById(id);
/* 25 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<CartItem> getlist(Long cartId, Long popularityGroupId)
/*    */   {
/* 30 */     return this.dao.getlist(cartId, popularityGroupId);
/*    */   }
/*    */ 
/*    */   public CartItem updateByUpdater(CartItem bean)
/*    */   {
/* 35 */     Updater updater = new Updater(bean);
/* 36 */     return this.dao.updateByUpdater(updater);
/*    */   }
/*    */ 
/*    */   public CartItem deleteById(Long id)
/*    */   {
/* 41 */     CartItem bean = this.dao.deleteById(id);
/* 42 */     return bean;
/*    */   }
/*    */ 
/*    */   public CartItem[] deleteByIds(Long[] ids)
/*    */   {
/* 47 */     CartItem[] beans = new CartItem[ids.length];
/* 48 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 49 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 51 */     return beans;
/*    */   }
/*    */ 
/*    */   public int deleteByProductId(Long productId)
/*    */   {
/* 56 */     return this.dao.deleteByProductId(productId);
/*    */   }
/*    */ 
/*    */   public int deleteByProductFactionId(Long productFacId)
/*    */   {
/* 61 */     return this.dao.deleteByProductFactionId(productFacId);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.CartItemMngImpl
 * JD-Core Version:    0.6.0
 */