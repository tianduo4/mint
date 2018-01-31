/*    */ package com.jspgou.common.security;
/*    */ 
/*    */ public class AccountExpiredException extends AccountStatusException
/*    */ {
/*    */   public AccountExpiredException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public AccountExpiredException(String msg)
/*    */   {
/* 16 */     super(msg);
/*    */   }
/*    */ 
/*    */   public AccountExpiredException(String msg, Object extraInformation) {
/* 20 */     super(msg, extraInformation);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.AccountExpiredException
 * JD-Core Version:    0.6.0
 */