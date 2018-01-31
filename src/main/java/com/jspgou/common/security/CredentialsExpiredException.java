/*    */ package com.jspgou.common.security;
/*    */ 
/*    */ public class CredentialsExpiredException extends AccountStatusException
/*    */ {
/*    */   public CredentialsExpiredException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CredentialsExpiredException(String msg)
/*    */   {
/* 16 */     super(msg);
/*    */   }
/*    */ 
/*    */   public CredentialsExpiredException(String msg, Object extraInformation) {
/* 20 */     super(msg, extraInformation);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.CredentialsExpiredException
 * JD-Core Version:    0.6.0
 */