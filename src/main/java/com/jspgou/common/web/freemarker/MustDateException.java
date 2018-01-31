/*    */ package com.jspgou.common.web.freemarker;
/*    */ 
/*    */ import freemarker.template.TemplateModelException;
/*    */ 
/*    */ public class MustDateException extends TemplateModelException
/*    */ {
/*    */   public MustDateException(String paramName)
/*    */   {
/* 12 */     super("The \"" + paramName + "\" parameter must be a date.");
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.freemarker.MustDateException
 * JD-Core Version:    0.6.0
 */