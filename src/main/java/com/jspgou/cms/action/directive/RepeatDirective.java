/*     */ package com.jspgou.cms.action.directive;
/*     */ 
/*     */ import freemarker.core.Environment;
/*     */ import freemarker.template.SimpleNumber;
/*     */ import freemarker.template.TemplateBooleanModel;
/*     */ import freemarker.template.TemplateDirectiveBody;
/*     */ import freemarker.template.TemplateDirectiveModel;
/*     */ import freemarker.template.TemplateException;
/*     */ import freemarker.template.TemplateModel;
/*     */ import freemarker.template.TemplateModelException;
/*     */ import freemarker.template.TemplateNumberModel;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class RepeatDirective
/*     */   implements TemplateDirectiveModel
/*     */ {
/*     */   private static final String PARAM_NAME_COUNT = "count";
/*     */   private static final String PARAM_NAME_HR = "hr";
/*     */ 
/*     */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*     */     throws TemplateException, IOException
/*     */   {
/*  59 */     int countParam = 0;
/*  60 */     boolean countParamSet = false;
/*  61 */     boolean hrParam = false;
/*     */ 
/*  63 */     Iterator paramIter = params
/*  64 */       .entrySet().iterator();
/*  65 */     while (paramIter.hasNext()) {
/*  66 */       Map.Entry ent = 
/*  67 */         (Map.Entry)paramIter
/*  67 */         .next();
/*     */ 
/*  69 */       String paramName = (String)ent.getKey();
/*  70 */       TemplateModel paramValue = (TemplateModel)ent.getValue();
/*     */ 
/*  72 */       if (paramName.equals("count")) {
/*  73 */         if (!(paramValue instanceof TemplateNumberModel)) {
/*  74 */           throw new TemplateModelException("The \"hr\" parameter must be a number.");
/*     */         }
/*     */ 
/*  77 */         countParam = ((TemplateNumberModel)paramValue).getAsNumber()
/*  78 */           .intValue();
/*  79 */         countParamSet = true;
/*  80 */         if (countParam < 0) {
/*  81 */           throw new TemplateModelException("The \"hr\" parameter can't be negative.");
/*     */         }
/*     */       }
/*  84 */       else if (paramName.equals("hr")) {
/*  85 */         if (!(paramValue instanceof TemplateBooleanModel)) {
/*  86 */           throw new TemplateModelException("The \"hr\" parameter must be a boolean.");
/*     */         }
/*     */ 
/*  89 */         hrParam = ((TemplateBooleanModel)paramValue).getAsBoolean();
/*     */       } else {
/*  91 */         throw new TemplateModelException("Unsupported parameter: " + 
/*  92 */           paramName);
/*     */       }
/*     */     }
/*  95 */     if (!countParamSet) {
/*  96 */       throw new TemplateModelException("The required \"count\" paramteris missing.");
/*     */     }
/*     */ 
/* 100 */     if (loopVars.length > 1) {
/* 101 */       throw new TemplateModelException(
/* 102 */         "At most one loop variable is allowed.");
/*     */     }
/*     */ 
/* 110 */     Writer out = env.getOut();
/* 111 */     if (body != null)
/* 112 */       for (int i = 0; i < countParam; i++)
/*     */       {
/* 115 */         if ((hrParam) && (i != 0)) {
/* 116 */           out.write("<hr>");
/*     */         }
/*     */ 
/* 120 */         if (loopVars.length > 0) {
/* 121 */           loopVars[0] = new SimpleNumber(i + 1);
/*     */         }
/*     */ 
/* 126 */         body.render(env.getOut());
/*     */       }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.RepeatDirective
 * JD-Core Version:    0.6.0
 */