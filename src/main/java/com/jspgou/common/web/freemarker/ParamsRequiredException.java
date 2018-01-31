/*    */ package com.jspgou.common.web.freemarker;
/*    */ 
/*    */ import freemarker.template.TemplateModelException;
/*    */ 
/*    */ public class ParamsRequiredException extends TemplateModelException
/*    */ {
/*    */   public ParamsRequiredException(String paramName)
/*    */   {
/* 12 */     super("The required \"" + paramName + "\" paramter is missing.");
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.freemarker.ParamsRequiredException
 * JD-Core Version:    0.6.0
 */