/*    */ package com.jspgou.common.util;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class CheckMobile
/*    */ {
/* 18 */   static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
/*    */ 
/* 24 */   static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
/*    */ 
/* 28 */   static Pattern phonePat = Pattern.compile(phoneReg, 2);
/* 29 */   static Pattern tablePat = Pattern.compile(tableReg, 2);
/*    */ 
/*    */   public static boolean check(String userAgent) {
/* 32 */     if (userAgent == null) {
/* 33 */       userAgent = "";
/*    */     }
/*    */ 
/* 36 */     Matcher matcherPhone = phonePat.matcher(userAgent);
/* 37 */     Matcher matcherTable = tablePat.matcher(userAgent);
/*    */ 
/* 40 */     return (matcherPhone.find()) || (matcherTable.find());
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.CheckMobile
 * JD-Core Version:    0.6.0
 */