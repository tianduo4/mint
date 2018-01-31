/*    */ package com.jspgou.cms.action.directive.abs;
/*    */ 
/*    */ import com.jspgou.cms.web.FrontUtils;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class PaginationDirective extends WebDirective
/*    */ {
/*    */   public static final String PAGINATION_PATH = "/WEB-INF/tag/style_pagination/style";
/*    */   public static final String PARAM_SYTLE_NAME = "style";
/*    */   public static final String PARAM_CONTENT = "content";
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] atemplatemodel, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 41 */     Map model = DirectiveUtils.addParamsToVariable(env, params);
/* 42 */     String content = DirectiveUtils.getString("content", params);
/* 43 */     if ("1".equals(content)) {
/* 44 */       String sysPage = DirectiveUtils.getString("sysPage", params);
/* 45 */       String userPage = DirectiveUtils.getString("userPage", params);
/* 46 */       if (!StringUtils.isBlank(sysPage)) {
/* 47 */         String tpl = "/WEB-INF/t/gou_sys_defined/style_page/content_" + sysPage + ".html";
/* 48 */         env.include(tpl, "UTF-8", true);
/* 49 */       } else if (!StringUtils.isBlank(userPage)) {
/* 50 */         String tpl = "/WEB-INF/t/gou_sys_defined/style_page/content_" + userPage + ".html";
/* 51 */         env.include(tpl, "UTF-8", true);
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 56 */       FrontUtils.includePagination(params, env);
/*    */     }
/* 58 */     DirectiveUtils.removeParamsFromVariable(env, params, model);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.abs.PaginationDirective
 * JD-Core Version:    0.6.0
 */