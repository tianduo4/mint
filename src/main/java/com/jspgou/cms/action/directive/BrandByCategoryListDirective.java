/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.CategoryMng;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.ObjectWrapper;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class BrandByCategoryListDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "BrandList";
/*    */ 
/*    */   @Autowired
/*    */   private CategoryMng categoryMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 39 */     Website web = getWeb(env, params, this.websiteMng);
/* 40 */     Integer categoryId = getInt("categoryId", params);
/* 41 */     List list = this.categoryMng.getBrandByCate(categoryId);
/* 42 */     Map paramWrap = new HashMap(
/* 43 */       params);
/* 44 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
/* 45 */     Map origMap = 
/* 46 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 47 */     if (isInvokeTpl(params))
/* 48 */       includeTpl("shop", "BrandList", web, params, env);
/*    */     else {
/* 50 */       renderBody(env, loopVars, body);
/*    */     }
/* 52 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 57 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng)
/*    */   {
/* 66 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.BrandByCategoryListDirective
 * JD-Core Version:    0.6.0
 */