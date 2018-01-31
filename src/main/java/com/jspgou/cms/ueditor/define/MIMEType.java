/*    */ package com.jspgou.cms.ueditor.define;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MIMEType
/*    */ {
/*  8 */   public static final Map<String, String> types = new HashMap() { } ;
/*    */ 
/*    */   public static String getSuffix(String mime)
/*    */   {
/* 17 */     return (String)types.get(mime);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.define.MIMEType
 * JD-Core Version:    0.6.0
 */