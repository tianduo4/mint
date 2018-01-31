/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseShopMoney;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class ShopMoney extends BaseShopMoney
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ShopMoney()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopMoney(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public ShopMoney(Long id, String name, Double money, boolean status, Date createTime)
/*    */   {
/* 40 */     super(id, 
/* 37 */       name, 
/* 38 */       money, 
/* 39 */       status, 
/* 40 */       createTime);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopMoney
 * JD-Core Version:    0.6.0
 */