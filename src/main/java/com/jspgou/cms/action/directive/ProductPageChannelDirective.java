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
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class ProductPageChannelDirective extends ProductAbstractDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ProductPage";
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 35 */     Website web = getWeb(env, params, this.websiteMng);
/* 36 */     Integer ctgId = getCategoryId(params);
/*    */ 
/* 39 */     String brandId = getString("brandId", params);
/* 40 */     Long tagId = getTagId(params);
/* 41 */     String[] names = StringUtils.split(getString("names", params), ',');
/* 42 */     String[] values = StringUtils.split(getString("values", params), ',');
/* 43 */     Integer orderBy = getInt("orderBy", params);
/* 44 */     Double startPrice = getDouble("startPrice", params);
/* 45 */     Double endPrice = getDouble("endPrice", params);
/* 46 */     Pagination pagination = this.productMng.getPageForTagChannel(brandId, web.getId(), ctgId, 
/* 47 */       tagId, isRecommend(params), names, values, isSpecial(params), orderBy.intValue(), startPrice, endPrice, getPageNo(env), 
/* 48 */       getCount(params));
/* 49 */     Map paramWrap = new HashMap(
/* 50 */       params);
/* 51 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/* 52 */       .wrap(pagination));
/* 53 */     Map origMap = 
/* 54 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 55 */     if (isInvokeTpl(params))
/* 56 */       includeTpl("shop", "ProductPage", web, params, env);
/*    */     else {
/* 58 */       renderBody(env, loopVars, body);
/*    */     }
/* 60 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.ProductPageChannelDirective
 * JD-Core Version:    0.6.0
 */