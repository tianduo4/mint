/*    */ package com.jspgou.common.web.freemarker;
/*    */ 
/*    */ import com.jspgou.common.util.StrUtils;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TextCutDirective
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public static final String PARAM_S = "s";
/*    */   public static final String PARAM_LEN = "len";
/*    */   public static final String PARAM_APPEND = "append";
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 28 */     String s = DirectiveUtils.getString("s", params);
/* 29 */     Integer len = DirectiveUtils.getInt("len", params);
/* 30 */     String append = DirectiveUtils.getString("append", params);
/* 31 */     if (s != null) {
/* 32 */       Writer out = env.getOut();
/* 33 */       if (len != null)
/* 34 */         out.append(StrUtils.textCut(s, len.intValue(), append));
/*    */       else
/* 36 */         out.append(s);
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.freemarker.TextCutDirective
 * JD-Core Version:    0.6.0
 */