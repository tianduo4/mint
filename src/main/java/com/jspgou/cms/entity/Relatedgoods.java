/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseRelatedgoods;
/*    */ 
/*    */ public class Relatedgoods extends BaseRelatedgoods
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Relatedgoods()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Relatedgoods(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public Relatedgoods(Long id, Long productId, Long productIds)
/*    */   {
/* 36 */     super(id, 
/* 35 */       productId, 
/* 36 */       productIds);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Relatedgoods
 * JD-Core Version:    0.6.0
 */