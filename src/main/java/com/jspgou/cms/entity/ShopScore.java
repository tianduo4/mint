/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseShopScore;
/*    */ 
/*    */ public class ShopScore extends BaseShopScore
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ShopScore()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopScore(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public ShopScore(Long id, Integer scoreType, boolean useStatus, boolean status)
/*    */   {
/* 38 */     super(id, 
/* 36 */       scoreType, 
/* 37 */       useStatus, 
/* 38 */       status);
/*    */   }
/*    */ 
/*    */   public static enum ScoreTypes
/*    */   {
/* 55 */     EMAIL_VALIDATE(10), 
/*    */ 
/* 59 */     ORDER_SCORE(11), 
/*    */ 
/* 63 */     BUYER_RETURN_PAY(22);
/*    */ 
/*    */     private int value;
/*    */ 
/* 66 */     private ScoreTypes(int value) { this.value = value;
/*    */     }
/*    */ 
/*    */     public int getValue()
/*    */     {
/* 72 */       return this.value;
/*    */     }
/*    */ 
/*    */     public static ScoreTypes valueOf(int value) {
/* 76 */       for (ScoreTypes s : values()) {
/* 77 */         if (s.getValue() == value) {
/* 78 */           return s;
/*    */         }
/*    */       }
/* 81 */       throw new IllegalArgumentException("Connot find value " + value + 
/* 82 */         " in eunu OrderStatus.");
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopScore
 * JD-Core Version:    0.6.0
 */