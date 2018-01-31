/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.entity.ShopMember;
/*    */ import com.jspgou.cms.manager.CollectMng;
/*    */ import com.jspgou.cms.web.threadvariable.MemberThread;
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
/*    */ public class CollectPageDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ArticlePage";
/*    */   public static final String PARAM_CATEGORY_ID = "channelId";
/*    */   private CollectMng collectMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 47 */     ShopMember member = MemberThread.get();
/* 48 */     Website web = getWeb(env, params, this.websiteMng);
/* 49 */     Integer count = Integer.valueOf(getCount(params));
/* 50 */     Pagination pagination = this.collectMng.getList(count, Integer.valueOf(getPageNo(env)), member.getId());
/* 51 */     Map paramWrap = new HashMap(
/* 52 */       params);
/* 53 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/* 54 */       .wrap(pagination));
/* 55 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
/* 56 */     Map origMap = 
/* 57 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 58 */     if (isInvokeTpl(params))
/* 59 */       includeTpl("shop", "ArticlePage", web, params, env);
/*    */     else {
/* 61 */       renderBody(env, loopVars, body);
/*    */     }
/* 63 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 68 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setCollectMng(CollectMng collectMng)
/*    */   {
/* 76 */     this.collectMng = collectMng;
/*    */   }
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 81 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.CollectPageDirective
 * JD-Core Version:    0.6.0
 */