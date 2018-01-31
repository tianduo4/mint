/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseExendedItem;
/*    */ 
/*    */ public class ExendedItem extends BaseExendedItem
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ExendedItem()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ExendedItem(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public ExendedItem(Long id, Exended exended, String name)
/*    */   {
/* 36 */     super(id, 
/* 35 */       exended, 
/* 36 */       name);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ExendedItem
 * JD-Core Version:    0.6.0
 */