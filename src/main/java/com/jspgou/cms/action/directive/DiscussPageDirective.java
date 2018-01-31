/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.DiscussMng;
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
/*    */ public class DiscussPageDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "DiscussPage";
/*    */ 
/*    */   @Autowired
/*    */   private DiscussMng discussMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 40 */     Website web = getWeb(env, params, this.websiteMng);
/* 41 */     Integer count = Integer.valueOf(getCount(params));
/* 42 */     Long productId = getLong("productId", params);
/*    */ 
/* 44 */     Long memberId = getLong("memberId", params);
/* 45 */     String discussType = getString("discussType", params);
/* 46 */     Pagination pagination = this.discussMng.getPage(memberId, productId, discussType, null, null, null, null, getPageNo(env), count.intValue(), true);
/*    */ 
/* 48 */     Map paramWrap = new HashMap(
/* 49 */       params);
/* 50 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/* 51 */       .wrap(pagination));
/* 52 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
/* 53 */     Map origMap = 
/* 54 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 55 */     if (isInvokeTpl(params))
/* 56 */       includeTpl("shop", "DiscussPage", web, params, env);
/*    */     else {
/* 58 */       renderBody(env, loopVars, body);
/*    */     }
/* 60 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 65 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng)
/*    */   {
/* 75 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.DiscussPageDirective
 * JD-Core Version:    0.6.0
 */