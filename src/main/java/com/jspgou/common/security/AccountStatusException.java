/*    */ package com.jspgou.common.security;
/*    */ 
/*    */ public class AccountStatusException extends AuthenticationException
/*    */ {
/*    */   public AccountStatusException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public AccountStatusException(String msg)
/*    */   {
/* 16 */     super(msg);
/*    */   }
/*    */ 
/*    */   public AccountStatusException(String msg, Object extraInformation) {
/* 20 */     super(msg, extraInformation);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.AccountStatusException
 * JD-Core Version:    0.6.0
 */