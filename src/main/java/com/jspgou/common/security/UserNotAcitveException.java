/*    */ package com.jspgou.common.security;
/*    */ 
/*    */ public class UserNotAcitveException extends AccountStatusException
/*    */ {
/*    */   public UserNotAcitveException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UserNotAcitveException(String s)
/*    */   {
/* 16 */     super(s);
/*    */   }
/*    */ 
/*    */   public UserNotAcitveException(String msg, Object extraInformation) {
/* 20 */     super(msg, extraInformation);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.UserNotAcitveException
 * JD-Core Version:    0.6.0
 */