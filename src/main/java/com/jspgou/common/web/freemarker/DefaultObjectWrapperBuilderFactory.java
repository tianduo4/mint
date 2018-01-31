/*    */ package com.jspgou.common.web.freemarker;
/*    */ 
/*    */ import freemarker.template.DefaultObjectWrapper;
/*    */ import freemarker.template.DefaultObjectWrapperBuilder;
/*    */ import freemarker.template.Version;
/*    */ 
/*    */ public class DefaultObjectWrapperBuilderFactory
/*    */ {
/* 11 */   private static final Version v = new Version(2, 3, 24);
/* 12 */   private static final DefaultObjectWrapperBuilder defaultObjectWrapperBuilder = new DefaultObjectWrapperBuilder(v);
/*    */ 
/*    */   public static DefaultObjectWrapperBuilder getInstance()
/*    */   {
/* 16 */     return defaultObjectWrapperBuilder;
/*    */   }
/*    */ 
/*    */   public static DefaultObjectWrapper getDefaultObjectWrapper() {
/* 20 */     return defaultObjectWrapperBuilder.build();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.freemarker.DefaultObjectWrapperBuilderFactory
 * JD-Core Version:    0.6.0
 */