/*    */ package com.jspgou.cms.ueditor.define;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class FileType
/*    */ {
/*    */   public static final String JPG = "JPG";
/* 10 */   private static final Map<String, String> types = new HashMap() { } ;
/*    */ 
/*    */   public static String getSuffix(String key)
/*    */   {
/* 17 */     return (String)types.get(key);
/*    */   }
/*    */ 
/*    */   public static String getSuffixByFilename(String filename)
/*    */   {
/* 27 */     return filename.substring(filename.lastIndexOf(".")).toLowerCase();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.define.FileType
 * JD-Core Version:    0.6.0
 */