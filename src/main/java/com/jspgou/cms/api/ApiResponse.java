/*    */ package com.jspgou.cms.api;
/*    */ 
/*    */ public class ApiResponse
/*    */ {
/*    */   private String body;
/*    */   private String message;
/*    */   private int code;
/*    */ 
/*    */   public ApiResponse(String body, String message, int code)
/*    */   {
/*  7 */     this.body = body;
/*  8 */     this.message = message;
/*  9 */     this.code = code;
/*    */   }
/*    */ 
/*    */   public String getBody()
/*    */   {
/* 16 */     return this.body;
/*    */   }
/*    */ 
/*    */   public void setBody(String body) {
/* 20 */     this.body = body;
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 27 */     return this.message;
/*    */   }
/*    */ 
/*    */   public void setMessage(String message) {
/* 31 */     this.message = message;
/*    */   }
/*    */ 
/*    */   public int getCode()
/*    */   {
/* 36 */     return this.code;
/*    */   }
/*    */ 
/*    */   public void setCode(int code) {
/* 40 */     this.code = code;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 45 */     return "{\"body\":" + this.body + ", \"message\":" + this.message + ", \"code\":" + this.code + "}";
/*    */   }
/*    */ 
/*    */   public String sourceToString() {
/* 49 */     return "{\"source\":" + this.body + ", \"message\":" + this.message + ", \"code\":" + this.code + "}";
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.ApiResponse
 * JD-Core Version:    0.6.0
 */