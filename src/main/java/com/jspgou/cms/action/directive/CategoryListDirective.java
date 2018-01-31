/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.entity.Category;
/*    */ import com.jspgou.cms.manager.CategoryMng;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.ObjectWrapper;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class CategoryListDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "category_list";
/*    */   private CategoryMng categoryMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 41 */     Long webId = getWebId(params);
/*    */     Website web;
/* 43 */     if (webId == null)
/* 44 */       web = getWeb(env, params, this.websiteMng);
/*    */     else {
/* 46 */       web = this.websiteMng.findById(webId);
/*    */     }
/* 48 */     if (web == null) {
/* 49 */       throw new TemplateModelException("webId=" + webId + " not exist!");
/*    */     }
/* 51 */     Integer parentId = DirectiveUtils.getInt("parentId", params);
/*    */     List list;
/* 53 */     if (parentId != null) {
/* 54 */       Category category = this.categoryMng.findById(parentId);
/* 55 */       if (category != null)
/* 56 */         list = new ArrayList(category.getChild());
/*    */       else
/* 58 */         list = new ArrayList();
/*    */     }
/*    */     else {
/* 61 */       list = this.categoryMng.getTopListForTag(web.getId());
/*    */     }
/*    */ 
/* 64 */     Map paramsWrap = new HashMap(
/* 65 */       params);
/* 66 */     paramsWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
/* 67 */     Map origMap = 
/* 68 */       DirectiveUtils.addParamsToVariable(env, paramsWrap);
/* 69 */     if (isInvokeTpl(params))
/* 70 */       includeTpl("tag", "category_list", web, params, env);
/*    */     else {
/* 72 */       renderBody(env, loopVars, body);
/*    */     }
/* 74 */     DirectiveUtils.removeParamsFromVariable(env, paramsWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 79 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setCategoryMng(CategoryMng categoryMng)
/*    */   {
/* 87 */     this.categoryMng = categoryMng;
/*    */   }
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 92 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.CategoryListDirective
 * JD-Core Version:    0.6.0
 */