/*    */ package com.jspgou.cms.web.threadvariable;
/*    */ 
/*    */ import com.jspgou.cms.entity.ShopMember;
/*    */ 
/*    */ public class MemberThread
/*    */ {
/*  9 */   private static ThreadLocal<ShopMember> instance = new ThreadLocal();
/*    */ 
/* 11 */   private static ThreadLocal<String> sessionKeyInstance = new ThreadLocal();
/*    */ 
/* 13 */   private static ThreadLocal<String> userNameInstance = new ThreadLocal();
/*    */ 
/*    */   public static ShopMember get() {
/* 16 */     return (ShopMember)instance.get();
/*    */   }
/*    */ 
/*    */   public static String getSessionKey() {
/* 20 */     return (String)sessionKeyInstance.get();
/*    */   }
/*    */ 
/*    */   public static String getUserName() {
/* 24 */     return (String)userNameInstance.get();
/*    */   }
/*    */   public static void set(ShopMember member) {
/* 27 */     instance.set(member);
/*    */   }
/*    */ 
/*    */   public static void setSessionKey(String sessionKey) {
/* 31 */     sessionKeyInstance.set(sessionKey);
/*    */   }
/*    */   public static void setUserName(String username) {
/* 34 */     userNameInstance.set(username);
/*    */   }
/*    */ 
/*    */   public static void remove() {
/* 38 */     instance.remove();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.threadvariable.MemberThread
 * JD-Core Version:    0.6.0
 */