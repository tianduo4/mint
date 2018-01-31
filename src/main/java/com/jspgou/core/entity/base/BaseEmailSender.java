/*    */ package com.jspgou.core.entity.base;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class BaseEmailSender
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 21 */   public static String REF = "EmailSender";
/* 22 */   public static String PROP_PASSWORD = "password";
/* 23 */   public static String PROP_HOST = "host";
/* 24 */   public static String PROP_ENCODING = "encoding";
/* 25 */   public static String PROP_PERSONAL = "personal";
/* 26 */   public static String PROP_USERNAME = "username";
/*    */   private String host;
/*    */   private String encoding;
/*    */   private String username;
/*    */   private String password;
/*    */   private String personal;
/*    */ 
/*    */   public BaseEmailSender()
/*    */   {
/* 30 */     initialize();
/*    */   }
/*    */ 
/*    */   protected void initialize()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getHost()
/*    */   {
/* 43 */     return this.host;
/*    */   }
/*    */ 
/*    */   public void setHost(String host) {
/* 47 */     this.host = host;
/*    */   }
/*    */ 
/*    */   public String getEncoding() {
/* 51 */     return this.encoding;
/*    */   }
/*    */ 
/*    */   public void setEncoding(String encoding) {
/* 55 */     this.encoding = encoding;
/*    */   }
/*    */ 
/*    */   public String getUsername() {
/* 59 */     return this.username;
/*    */   }
/*    */ 
/*    */   public void setUsername(String username) {
/* 63 */     this.username = username;
/*    */   }
/*    */ 
/*    */   public String getPassword() {
/* 67 */     return this.password;
/*    */   }
/*    */ 
/*    */   public void setPassword(String password) {
/* 71 */     this.password = password;
/*    */   }
/*    */ 
/*    */   public String getPersonal() {
/* 75 */     return this.personal;
/*    */   }
/*    */ 
/*    */   public void setPersonal(String personal) {
/* 79 */     this.personal = personal;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 84 */     return super.toString();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseEmailSender
 * JD-Core Version:    0.6.0
 */