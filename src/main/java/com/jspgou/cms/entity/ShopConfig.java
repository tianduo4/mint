/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseShopConfig;
/*    */ 
/*    */ public class ShopConfig extends BaseShopConfig
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ShopConfig()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopConfig(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public ShopConfig(Long id, ShopMemberGroup registerGroup, String shopPriceName, String marketPriceName, Integer favoriteSize, Boolean registerAuto)
/*    */   {
/* 42 */     super(id, 
/* 38 */       registerGroup, 
/* 39 */       shopPriceName, 
/* 40 */       marketPriceName, 
/* 41 */       favoriteSize, 
/* 42 */       registerAuto);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopConfig
 * JD-Core Version:    0.6.0
 */