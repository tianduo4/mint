/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.CartDao;
/*    */ import com.jspgou.cms.entity.Cart;
/*    */ import com.jspgou.cms.entity.Product;
/*    */ import com.jspgou.cms.entity.ProductFashion;
/*    */ import com.jspgou.cms.manager.CartMng;
/*    */ import com.jspgou.cms.manager.GiftMng;
/*    */ import com.jspgou.cms.manager.PopularityGroupMng;
/*    */ import com.jspgou.cms.manager.ProductFashionMng;
/*    */ import com.jspgou.cms.manager.ProductMng;
/*    */ import com.jspgou.core.manager.MemberMng;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class CartMngImpl
/*    */   implements CartMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private PopularityGroupMng popularityGroupMng;
/*    */ 
/*    */   @Autowired
/*    */   private CartDao dao;
/*    */ 
/*    */   @Autowired
/*    */   private GiftMng giftMng;
/*    */ 
/*    */   @Autowired
/*    */   private MemberMng memberMng;
/*    */ 
/*    */   @Autowired
/*    */   private ProductMng productMng;
/*    */ 
/*    */   @Autowired
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   @Autowired
/*    */   private ProductFashionMng fashionMng;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Cart findById(Long id)
/*    */   {
/* 28 */     Cart entity = this.dao.findById(id);
/* 29 */     return entity;
/*    */   }
/*    */ 
/*    */   public Cart collectAddItem(Product product, Long fashId, Long popularityId, int count, boolean isAdd, Long memberId, Long webId)
/*    */   {
/* 36 */     Cart cart = findById(memberId);
/* 37 */     if (cart == null) {
/* 38 */       cart = new Cart();
/* 39 */       cart.setMember(this.memberMng.findById(memberId));
/* 40 */       cart.setWebsite(this.websiteMng.findById(webId));
/* 41 */       cart.init();
/*    */     }
/* 43 */     if (fashId != null) {
/* 44 */       ProductFashion productFashion = this.fashionMng.findById(fashId);
/* 45 */       cart.addItem(productFashion.getProductId(), productFashion, this.popularityGroupMng.findById(popularityId), count, isAdd);
/*    */     } else {
/* 47 */       cart.addItem(product, null, this.popularityGroupMng.findById(popularityId), count, isAdd);
/*    */     }
/* 49 */     cart = this.dao.saveOrUpdate(cart);
/* 50 */     return cart;
/*    */   }
/*    */ 
/*    */   public Cart addGift(Long giftId, int count, boolean isAdd, Long memberId, Long webId)
/*    */   {
/* 56 */     Cart cart = findById(memberId);
/* 57 */     if (cart == null) {
/* 58 */       cart = new Cart();
/* 59 */       cart.setMember(this.memberMng.findById(memberId));
/* 60 */       cart.setWebsite(this.websiteMng.findById(webId));
/*    */     }
/* 62 */     cart.addGift(this.giftMng.findById(giftId), count, isAdd);
/* 63 */     this.dao.saveOrUpdate(cart);
/* 64 */     return cart;
/*    */   }
/*    */ 
/*    */   public Cart update(Cart cart)
/*    */   {
/* 69 */     return this.dao.update(cart);
/*    */   }
/*    */ 
/*    */   public Cart deleteById(Long id)
/*    */   {
/* 74 */     Cart bean = this.dao.deleteById(id);
/* 75 */     return bean;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.CartMngImpl
 * JD-Core Version:    0.6.0
 */