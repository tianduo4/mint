/*    */ package com.jspgou.common.security;
/*    */ 
/*    */ public class DisabledException extends AccountStatusException
/*    */ {
/*    */   public DisabledException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DisabledException(String msg)
/*    */   {
/* 16 */     super(msg);
/*    */   }
/*    */ 
/*    */   public DisabledException(String msg, Object extraInformation) {
/* 20 */     super(msg, extraInformation);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.security.DisabledException
 * JD-Core Version:    0.6.0
 */