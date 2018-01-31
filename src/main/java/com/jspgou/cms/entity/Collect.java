/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseCollect;
/*    */ 
/*    */ public class Collect extends BaseCollect
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Collect()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Collect(Integer id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public Collect(Integer id, ShopMember member, Product product)
/*    */   {
/* 36 */     super(id, 
/* 35 */       member, 
/* 36 */       product);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Collect
 * JD-Core Version:    0.6.0
 */