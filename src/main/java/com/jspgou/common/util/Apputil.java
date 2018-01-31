/*    */ package com.jspgou.common.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class Apputil
/*    */ {
/*    */   public static Boolean getRequestBoolean(String requeststring)
/*    */   {
/* 14 */     Boolean requestBoolean = null;
/* 15 */     if (StringUtils.isNotBlank(requeststring)) {
/* 16 */       requestBoolean = Boolean.valueOf(Boolean.parseBoolean(requeststring));
/*    */     }
/* 18 */     return requestBoolean;
/*    */   }
/*    */ 
/*    */   public static Long getRequestLong(String requeststring) {
/* 22 */     Long requestLong = null;
/* 23 */     if (StringUtils.isNotBlank(requeststring)) {
/* 24 */       requestLong = Long.valueOf(Long.parseLong(requeststring));
/*    */     }
/* 26 */     return requestLong;
/*    */   }
/*    */ 
/*    */   public static Double getRequestDouble(String requeststring) {
/* 30 */     Double requestInteger = null;
/* 31 */     if (StringUtils.isNotBlank(requeststring)) {
/* 32 */       requestInteger = Double.valueOf(Double.parseDouble(requeststring));
/*    */     }
/* 34 */     return requestInteger;
/*    */   }
/*    */ 
/*    */   public static Integer getRequestInteger(String requeststring) {
/* 38 */     Integer requestInteger = null;
/* 39 */     if (StringUtils.isNotBlank(requeststring)) {
/* 40 */       requestInteger = Integer.valueOf(Integer.parseInt(requeststring));
/*    */     }
/* 42 */     return requestInteger;
/*    */   }
/*    */ 
/*    */   public static java.util.Date getRequestDate(String requeststring) {
/* 46 */     java.util.Date date = null;
/* 47 */     if (StringUtils.isNotBlank(requeststring)) {
/* 48 */       DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/*    */       try {
/* 50 */         date = format.parse(requeststring);
/*    */       } catch (ParseException e) {
/* 52 */         e.printStackTrace();
/*    */       }
/* 54 */       date = java.sql.Date.valueOf(requeststring);
/*    */     }
/* 56 */     return date;
/*    */   }
/*    */ 
/*    */   public static Integer getfirstResult(String requeststring) {
/* 60 */     Integer requestInteger = Integer.valueOf(0);
/* 61 */     if (StringUtils.isNotBlank(requeststring)) {
/* 62 */       requestInteger = Integer.valueOf(Integer.parseInt(requeststring));
/*    */     }
/* 64 */     return requestInteger;
/*    */   }
/*    */ 
/*    */   public static Integer getmaxResults(String requeststring) {
/* 68 */     Integer requestInteger = Integer.valueOf(10);
/* 69 */     if (StringUtils.isNotBlank(requeststring)) {
/* 70 */       requestInteger = Integer.valueOf(Integer.parseInt(requeststring));
/*    */     }
/* 72 */     return requestInteger;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.Apputil
 * JD-Core Version:    0.6.0
 */