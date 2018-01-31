/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.ShopArticleMng;
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
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class ArticleListDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ArticleList";
/*    */   public static final String PARAM_CHANNEL_ID = "channelId";
/*    */   private ShopArticleMng shopArticleMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 45 */     Website web = getWeb(env, params, this.websiteMng);
/* 46 */     Integer channelId = getChannelId(params);
/* 47 */     List list = this.shopArticleMng.getListForTag(web.getId(), channelId, 0, getCount(params));
/* 48 */     Map paramWrap = new HashMap(
/* 49 */       params);
/* 50 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
/* 51 */     Map origMap = 
/* 52 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 53 */     if (isInvokeTpl(params))
/* 54 */       includeTpl("shop", "ArticleList", web, params, env);
/*    */     else {
/* 56 */       renderBody(env, loopVars, body);
/*    */     }
/* 58 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 63 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   private Integer getChannelId(Map<String, TemplateModel> params) throws TemplateException
/*    */   {
/* 68 */     TemplateModel parentId = (TemplateModel)params.get("channelId");
/* 69 */     if (parentId == null) {
/* 70 */       return null;
/*    */     }
/* 72 */     if ((parentId instanceof TemplateNumberModel)) {
/* 73 */       return Integer.valueOf(((TemplateNumberModel)parentId).getAsNumber().intValue());
/*    */     }
/* 75 */     throw new TemplateModelException("The 'channelId' parameter must be a number.");
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setShopArticleMng(ShopArticleMng shopArticleMng)
/*    */   {
/* 85 */     this.shopArticleMng = shopArticleMng;
/*    */   }
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 90 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.ArticleListDirective
 * JD-Core Version:    0.6.0
 */