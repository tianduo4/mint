/*    */ package com.jspgou.cms.api;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class CommonUtils extends StringUtils
/*    */ {
/*    */   public static String parseStr(String str)
/*    */   {
/* 12 */     return isNotBlank(str) ? str : "";
/*    */   }
/*    */ 
/*    */   public static Object parseId(Object id) {
/* 16 */     if (id != null) {
/* 17 */       return id;
/*    */     }
/* 19 */     return "";
/*    */   }
/*    */ 
/*    */   public static String parseDate(Date date, String formate) {
/* 23 */     if (date != null) {
/* 24 */       SimpleDateFormat sdf = new SimpleDateFormat(formate);
/* 25 */       return sdf.format(date);
/*    */     }
/* 27 */     return "";
/*    */   }
/*    */ 
/*    */   public static int parseInteger(Integer i) {
/* 31 */     return i != null ? i.intValue() : 0;
/*    */   }
/*    */ 
/*    */   public static Long parseLong(Long l) {
/* 35 */     return Long.valueOf(l != null ? l.longValue() : 0L);
/*    */   }
/*    */ 
/*    */   public static Double parseDouble(Double d) {
/* 39 */     return Double.valueOf(d != null ? d.doubleValue() : 0.0D);
/*    */   }
/*    */ 
/*    */   public static Boolean parseBoolean(Boolean b) {
/* 43 */     return Boolean.valueOf(b != null ? b.booleanValue() : false);
/*    */   }
/*    */ 
/*    */   public static BigDecimal parseBigDecimal(BigDecimal d) {
/* 47 */     return d != null ? d : new BigDecimal(0.0D);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.CommonUtils
 * JD-Core Version:    0.6.0
 */