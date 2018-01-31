/*    */ package com.jspgou.common.web.freemarker;
/*    */ 
/*    */ import freemarker.template.TemplateModelException;
/*    */ 
/*    */ public class MustStringException extends TemplateModelException
/*    */ {
/*    */   public MustStringException(String paramName)
/*    */   {
/* 12 */     super("The \"" + paramName + "\" parameter must be a string.");
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.freemarker.MustStringException
 * JD-Core Version:    0.6.0
 */