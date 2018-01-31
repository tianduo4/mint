/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseCartGift;
/*    */ import com.jspgou.core.entity.Website;
/*    */ 
/*    */ public class CartGift extends BaseCartGift
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public CartGift()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CartGift(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public CartGift(Long id, Website website, Cart cart, Gift gift, Integer count)
/*    */   {
/* 40 */     super(id, 
/* 37 */       website, 
/* 38 */       cart, 
/* 39 */       gift, 
/* 40 */       count);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.CartGift
 * JD-Core Version:    0.6.0
 */