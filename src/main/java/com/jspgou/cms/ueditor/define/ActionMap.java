/*    */ package com.jspgou.cms.ueditor.define;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class ActionMap
/*    */ {
/* 26 */   public static final Map<String, Integer> mapping = new HashMap() {  } ;
/*    */   public static final int CONFIG = 0;
/*    */   public static final int UPLOAD_IMAGE = 1;
/*    */   public static final int UPLOAD_SCRAWL = 2;
/*    */   public static final int UPLOAD_VIDEO = 3;
/*    */   public static final int UPLOAD_FILE = 4;
/*    */   public static final int CATCH_IMAGE = 5;
/*    */   public static final int LIST_FILE = 6;
/*    */   public static final int LIST_IMAGE = 7;
/*    */ 
/* 39 */   public static int getType(String key) { return ((Integer)mapping.get(key)).intValue();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.define.ActionMap
 * JD-Core Version:    0.6.0
 */