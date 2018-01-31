/*    */ package com.jspgou.core.security;
/*    */ 
/*    */ import com.jspgou.common.security.AuthenticationException;
/*    */ 
/*    */ public class UserNotInWebsiteException extends AuthenticationException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public UserNotInWebsiteException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UserNotInWebsiteException(String s)
/*    */   {
/* 20 */     super(s);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.security.UserNotInWebsiteException
 * JD-Core Version:    0.6.0
 */