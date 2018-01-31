/*    */ package com.jspgou.core.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.common.web.freemarker.MustNumberException;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import freemarker.template.TemplateNumberModel;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.text.DecimalFormat;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ProcessTimeDirective extends WebDirective
/*    */ {
/* 17 */   private static final DecimalFormat FORMAT = new DecimalFormat("0.000");
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] atemplatemodel, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 24 */     long time = getStartTime(env);
/* 25 */     if (time == -1L) {
/* 26 */       return;
/*    */     }
/* 28 */     time = System.currentTimeMillis() - time;
/* 29 */     Writer writer = env.getOut();
/* 30 */     writer.append("Processed in " + FORMAT.format((float)time / 1000.0F) + " second(s)");
/*    */   }
/*    */ 
/*    */   private long getStartTime(Environment env)
/*    */     throws TemplateModelException
/*    */   {
/* 37 */     TemplateModel templatemodel = env.getGlobalVariable("_start_time");
/* 38 */     if (templatemodel == null) {
/* 39 */       return -1L;
/*    */     }
/* 41 */     if ((templatemodel instanceof TemplateNumberModel)) {
/* 42 */       return ((TemplateNumberModel)templatemodel).getAsNumber().longValue();
/*    */     }
/* 44 */     throw new MustNumberException("_start_time");
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.directive.ProcessTimeDirective
 * JD-Core Version:    0.6.0
 */