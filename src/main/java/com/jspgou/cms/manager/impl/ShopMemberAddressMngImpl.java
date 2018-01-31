/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopMemberAddressDao;
/*    */ import com.jspgou.cms.entity.Cart;
/*    */ import com.jspgou.cms.entity.ShopMemberAddress;
/*    */ import com.jspgou.cms.manager.CartMng;
/*    */ import com.jspgou.cms.manager.ShopMemberAddressMng;
/*    */ import com.jspgou.cms.manager.ShopMemberMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopMemberAddressMngImpl
/*    */   implements ShopMemberAddressMng
/*    */ {
/*    */   private ShopMemberAddressDao dao;
/*    */   private ShopMemberMng shopMemberMng;
/*    */   private CartMng cartMng;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<ShopMemberAddress> getList(Long memberId)
/*    */   {
/* 27 */     return this.dao.getList(memberId);
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress findByMemberDefault(Long memberId, Boolean isDefault)
/*    */   {
/* 32 */     List list = this.dao.findByMemberDefault(memberId, isDefault);
/* 33 */     if (list.size() >= 1) {
/* 34 */       return (ShopMemberAddress)list.get(0);
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   public List<ShopMemberAddress> findByMemberId(Long memberId)
/*    */   {
/* 42 */     return this.dao.findByMemberDefault(memberId, null);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ShopMemberAddress findById(Long id) {
/* 48 */     ShopMemberAddress entity = this.dao.findById(id);
/* 49 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress save(ShopMemberAddress bean)
/*    */   {
/* 54 */     return this.dao.save(bean);
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress updateByUpdater(ShopMemberAddress bean)
/*    */   {
/* 59 */     Updater updater = new Updater(bean);
/* 60 */     return this.dao.updateByUpdater(updater);
/*    */   }
/*    */ 
/*    */   public ShopMemberAddress deleteById(Long id, Long memberId)
/*    */   {
/* 66 */     Cart cart = this.cartMng.findById(memberId);
/*    */ 
/* 71 */     ShopMemberAddress bean = this.dao.deleteById(id);
/* 72 */     return bean;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopMemberAddressDao dao)
/*    */   {
/* 81 */     this.dao = dao;
/*    */   }
/*    */   @Autowired
/*    */   public void setShopMemberMng(ShopMemberMng shopMemberMng) {
/* 86 */     this.shopMemberMng = shopMemberMng;
/*    */   }
/*    */   @Autowired
/*    */   public void setCartMng(CartMng cartMng) {
/* 91 */     this.cartMng = cartMng;
/*    */   }
/*    */ 
/*    */   public void deleteByMemberId(Long memberId)
/*    */   {
/* 96 */     this.dao.deleteByMemberId(memberId);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopMemberAddressMngImpl
 * JD-Core Version:    0.6.0
 */