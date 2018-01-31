/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseGiftExchange;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class GiftExchange extends BaseGiftExchange
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public GiftExchange()
/*    */   {
/*    */   }
/*    */ 
/*    */   public GiftExchange(Long id)
/*    */   {
/* 19 */     super(id);
/*    */   }
/*    */ 
/*    */   public GiftExchange(Long id, ShopMember member, Gift gift, Date createTime, Integer status)
/*    */   {
/* 37 */     super(id, 
/* 34 */       member, 
/* 35 */       gift, 
/* 36 */       createTime, 
/* 37 */       status);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.GiftExchange
 * JD-Core Version:    0.6.0
 */