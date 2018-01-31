/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.ShopArticleMng;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.ObjectWrapper;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import freemarker.template.TemplateNumberModel;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class ArticlePageDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ArticlePage";
/*    */   public static final String PARAM_CHANNEL_ID = "channelId";
/*    */   private ShopArticleMng shopArticleMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 44 */     Website web = getWeb(env, params, this.websiteMng);
/* 45 */     Long channelId = getChannelId(params);
/* 46 */     Pagination pagination = this.shopArticleMng.getPageForTag(web.getId(), 
/* 47 */       channelId, getPageNo(env), getCount(params));
/* 48 */     Map paramWrap = new HashMap(
/* 49 */       params);
/* 50 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/* 51 */       .wrap(pagination));
/* 52 */     Map origMap = 
/* 53 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 54 */     if (isInvokeTpl(params))
/* 55 */       includeTpl("shop", "ArticlePage", web, params, env);
/*    */     else {
/* 57 */       renderBody(env, loopVars, body);
/*    */     }
/* 59 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 64 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   private Long getChannelId(Map<String, TemplateModel> params) throws TemplateException
/*    */   {
/* 69 */     TemplateModel parentId = (TemplateModel)params.get("channelId");
/* 70 */     if (parentId == null) {
/* 71 */       return null;
/*    */     }
/* 73 */     if ((parentId instanceof TemplateNumberModel)) {
/* 74 */       return Long.valueOf(((TemplateNumberModel)parentId).getAsNumber().longValue());
/*    */     }
/* 76 */     throw new TemplateModelException("The 'channelId' parameter must be a number.");
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setShopArticleMng(ShopArticleMng shopArticleMng)
/*    */   {
/* 86 */     this.shopArticleMng = shopArticleMng;
/*    */   }
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 91 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.ArticlePageDirective
 * JD-Core Version:    0.6.0
 */