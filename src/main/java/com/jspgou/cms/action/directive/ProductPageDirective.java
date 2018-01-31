/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.manager.ProductMng;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.ObjectWrapper;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ProductPageDirective extends ProductAbstractDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ProductPage";
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 33 */     Website web = getWeb(env, params, this.websiteMng);
/* 34 */     Integer ctgId = getCategoryId(params);
/* 35 */     Long tagId = getTagId(params);
/* 36 */     Pagination pagination = this.productMng.getPageForTag(web.getId(), ctgId, 
/* 37 */       tagId, isRecommend(params), isSpecial(params), getPageNo(env), 
/* 38 */       getCount(params));
/* 39 */     Map paramWrap = new HashMap(
/* 40 */       params);
/* 41 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/* 42 */       .wrap(pagination));
/* 43 */     Map origMap = 
/* 44 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 45 */     if (isInvokeTpl(params))
/* 46 */       includeTpl("shop", "ProductPage", web, params, env);
/*    */     else {
/* 48 */       renderBody(env, loopVars, body);
/*    */     }
/* 50 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.ProductPageDirective
 * JD-Core Version:    0.6.0
 */