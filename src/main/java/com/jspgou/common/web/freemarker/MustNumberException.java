/*    */ package com.jspgou.common.web.freemarker;
/*    */ 
/*    */ import freemarker.template.TemplateModelException;
/*    */ 
/*    */ public class MustNumberException extends TemplateModelException
/*    */ {
/*    */   public MustNumberException(String paramName)
/*    */   {
/* 12 */     super("The \"" + paramName + "\" parameter must be a number.");
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.freemarker.MustNumberException
 * JD-Core Version:    0.6.0
 */