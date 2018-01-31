/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.GiftMng;
/*    */ import com.jspgou.common.page.Pagination;
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
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class GiftPageDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ArticlePage";
/*    */   public static final String PARAM_CATEGORY_ID = "channelId";
/*    */   private GiftMng giftMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 43 */     Website web = getWeb(env, params, this.websiteMng);
/* 44 */     Pagination pagination = this.giftMng.getPageGift(getPageNo(env), getCount(params));
/* 45 */     Map paramWrap = new HashMap(
/* 46 */       params);
/* 47 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/* 48 */       .wrap(pagination));
/* 49 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
/* 50 */     Map origMap = 
/* 51 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 52 */     if (isInvokeTpl(params))
/* 53 */       includeTpl("shop", "ArticlePage", web, params, env);
/*    */     else {
/* 55 */       renderBody(env, loopVars, body);
/*    */     }
/* 57 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 62 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setGiftMng(GiftMng giftMng)
/*    */   {
/* 70 */     this.giftMng = giftMng;
/*    */   }
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 75 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.GiftPageDirective
 * JD-Core Version:    0.6.0
 */