/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BasePopularityItem;
/*    */ 
/*    */ public class PopularityItem extends BasePopularityItem
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public PopularityItem()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PopularityItem(Long id)
/*    */   {
/* 19 */     super(id);
/*    */   }
/*    */ 
/*    */   public PopularityItem(Long id, Cart cart, Integer count)
/*    */   {
/* 33 */     super(id, 
/* 32 */       cart, 
/* 33 */       count);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.PopularityItem
 * JD-Core Version:    0.6.0
 */