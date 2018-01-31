/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseApiUserLogin;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class ApiUserLogin extends BaseApiUserLogin
/*    */ {
/*  8 */   public static Short USER_STATUS_LOGIN = Short.valueOf(1);
/*  9 */   public static Short USER_STATUS_LOGOUT = Short.valueOf(2);
/* 10 */   public static Short USER_STATUS_LOGOVERTIME = Short.valueOf(3);
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ApiUserLogin()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ApiUserLogin(Long id)
/*    */   {
/* 22 */     super(id);
/*    */   }
/*    */ 
/*    */   public ApiUserLogin(Long id, String username, Date loginTime, Integer loginCount, Date activeTime)
/*    */   {
/* 40 */     super(id, 
/* 37 */       username, 
/* 38 */       loginTime, 
/* 39 */       loginCount, 
/* 40 */       activeTime);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ApiUserLogin
 * JD-Core Version:    0.6.0
 */