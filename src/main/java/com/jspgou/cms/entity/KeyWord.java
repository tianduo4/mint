/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseKeyWord;
/*    */ 
/*    */ public class KeyWord extends BaseKeyWord
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public KeyWord()
/*    */   {
/*    */   }
/*    */ 
/*    */   public KeyWord(Integer id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public KeyWord(Integer id, String keyword, Integer times)
/*    */   {
/* 36 */     super(id, 
/* 35 */       keyword, 
/* 36 */       times);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.KeyWord
 * JD-Core Version:    0.6.0
 */