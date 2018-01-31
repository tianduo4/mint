/*    */ package com.jspgou.common.security;
/*    */ 
/*    */ public class LockedException extends AccountStatusException
/*    */ {
/*    */   public LockedException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LockedException(String msg)
/*    */   {
/* 16 */     super(msg);
/*    */   }
/*    */ 
/*    */   public LockedException(String msg, Object extraInformation) {
/* 20 */     super(msg, extraInformation);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.LockedException
 * JD-Core Version:    0.6.0
 */