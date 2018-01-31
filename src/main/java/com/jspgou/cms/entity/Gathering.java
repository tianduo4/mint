/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseGathering;
/*    */ 
/*    */ public class Gathering extends BaseGathering
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Gathering()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Gathering(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public Gathering(Long id, Order indent, ShopAdmin shopAdmin, String bank, String accounts, String drawee, String comment)
/*    */   {
/* 44 */     super(id, 
/* 39 */       indent, 
/* 40 */       shopAdmin, 
/* 41 */       bank, 
/* 42 */       accounts, 
/* 43 */       drawee, 
/* 44 */       comment);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Gathering
 * JD-Core Version:    0.6.0
 */