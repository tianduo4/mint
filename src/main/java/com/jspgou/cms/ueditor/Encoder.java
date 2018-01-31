/*    */ package com.jspgou.cms.ueditor;
/*    */ 
/*    */ public class Encoder
/*    */ {
/*    */   public static String toUnicode(String input)
/*    */   {
/*  7 */     StringBuilder builder = new StringBuilder();
/*  8 */     char[] chars = input.toCharArray();
/*    */ 
/* 10 */     for (char ch : chars)
/*    */     {
/* 12 */       if (ch < 'Ā')
/* 13 */         builder.append(ch);
/*    */       else {
/* 15 */         builder.append("\\u" + Integer.toHexString(ch & 0xFFFF));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 20 */     return builder.toString();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.Encoder
 * JD-Core Version:    0.6.0
 */