/*    */ package com.jspgou.common.security;
/*    */ 
/*    */ import org.apache.shiro.authc.AccountException;
/*    */ 
/*    */ public class AuthenticationException extends AccountException
/*    */ {
/*    */   private Object extraInformation;
/*    */ 
/*    */   public AuthenticationException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public AuthenticationException(String msg)
/*    */   {
/* 20 */     super(msg);
/*    */   }
/*    */ 
/*    */   public AuthenticationException(String msg, Object extraInformation) {
/* 24 */     super(msg);
/* 25 */     this.extraInformation = extraInformation;
/*    */   }
/*    */ 
/*    */   public Object getExtraInformation()
/*    */   {
/* 35 */     return this.extraInformation;
/*    */   }
/*    */ 
/*    */   public void clearExtraInformation() {
/* 39 */     this.extraInformation = null;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.AuthenticationException
 * JD-Core Version:    0.6.0
 */