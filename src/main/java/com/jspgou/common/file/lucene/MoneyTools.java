/*    */ package com.jspgou.common.file.lucene;
/*    */ 
/*    */ import org.apache.lucene.document.NumberTools;
/*    */ import org.springframework.util.Assert;
/*    */ 
/*    */ public class MoneyTools
/*    */ {
/* 14 */   private static final Double MULTIPLE = new Double(1000.0D);
/*    */ 
/*    */   public static String moneyToString(Double bigdecimal)
/*    */   {
/* 24 */     Assert.notNull(bigdecimal);
/* 25 */     return NumberTools.longToString(new Double(bigdecimal.doubleValue() * MULTIPLE.doubleValue()).longValue());
/*    */   }
/*    */ 
/*    */   public static Double stringToMoney(String s)
/*    */   {
/* 35 */     Double bigdecimal = new Double(NumberTools.stringToLong(s));
/* 36 */     return Double.valueOf(bigdecimal.doubleValue() / MULTIPLE.doubleValue());
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.file.lucene.MoneyTools
 * JD-Core Version:    0.6.0
 */