/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.manager.CategoryMng;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
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
/*    */ public class ProductTypeofCategoryListDirective extends ProductAbstractDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ProductList";
/*    */ 
/*    */   @Autowired
/*    */   private CategoryMng categoryMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 37 */     Website web = getWeb(env, params, this.websiteMng);
/*    */ 
/* 39 */     List list = this.categoryMng.getListBypType(web.getId(), getPtypeId(params), Integer.valueOf(getCount(params)));
/* 40 */     Map paramWrap = new HashMap(
/* 41 */       params);
/* 42 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
/* 43 */     Map origMap = 
/* 44 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 45 */     if (isInvokeTpl(params))
/* 46 */       includeTpl("shop", "ProductList", web, params, env);
/*    */     else {
/* 48 */       renderBody(env, loopVars, body);
/*    */     }
/* 50 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.ProductTypeofCategoryListDirective
 * JD-Core Version:    0.6.0
 */