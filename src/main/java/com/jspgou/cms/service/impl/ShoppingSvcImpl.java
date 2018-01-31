/*    */ package com.jspgou.cms.service.impl;
/*    */ 
/*    */ import com.jspgou.cms.entity.Cart;
/*    */ import com.jspgou.cms.entity.Product;
/*    */ import com.jspgou.cms.entity.ShopMember;
/*    */ import com.jspgou.cms.manager.CartMng;
/*    */ import com.jspgou.cms.service.ShoppingSvc;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import java.util.Set;
/*    */ import javax.servlet.http.Cookie;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShoppingSvcImpl
/*    */   implements ShoppingSvc
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private CartMng cartMng;
/*    */ 
/*    */   public Cart collectAddToCart(Product product, Long fashId, Long popularityId, int count, boolean isAdd, ShopMember member, Website web, HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 33 */     Cart cart = this.cartMng.collectAddItem(product, fashId, popularityId, count, isAdd, member.getId(), web.getId());
/* 34 */     Cookie cookie = createCookie(String.valueOf(cart.calTotalItem()), request);
/* 35 */     response.addCookie(cookie);
/* 36 */     return cart;
/*    */   }
/*    */ 
/*    */   public void clearCart(ShopMember member)
/*    */   {
/* 41 */     Cart cart = this.cartMng.findById(member.getId());
/* 42 */     cart.getItems().clear();
/* 43 */     cart.setTotalItems(Integer.valueOf(0));
/* 44 */     this.cartMng.update(cart);
/*    */   }
/*    */ 
/*    */   public Cart getCart(ShopMember member, HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 50 */     Cart cart = this.cartMng.findById(member.getId());
/* 51 */     if ((cart != null) && (cart.getItems().size() > 0)) {
/* 52 */       Cookie cookie = createCookie(String.valueOf(cart.calTotalItem()), request);
/* 53 */       response.addCookie(cookie);
/* 54 */       return cart;
/*    */     }
/* 56 */     Cookie cookie = createCookie("0", request);
/* 57 */     response.addCookie(cookie);
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   public Cart getCart(Long memberId)
/*    */   {
/* 64 */     Cart cart = this.cartMng.findById(memberId);
/* 65 */     if ((cart != null) && (cart.getItems().size() > 0)) {
/* 66 */       return cart;
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   public void addCookie(ShopMember member, HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 75 */     getCart(member, request, response);
/*    */   }
/*    */ 
/*    */   public void clearCookie(HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 81 */     Cookie cookie = createCookie(null, request);
/* 82 */     cookie.setMaxAge(0);
/* 83 */     response.addCookie(cookie);
/*    */   }
/*    */ 
/*    */   private Cookie createCookie(String totalItems, HttpServletRequest request) {
/* 87 */     Cookie cookie = new Cookie("cart_total_items", totalItems);
/* 88 */     String ctx = request.getContextPath();
/* 89 */     cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
/* 90 */     return cookie;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.service.impl.ShoppingSvcImpl
 * JD-Core Version:    0.6.0
 */