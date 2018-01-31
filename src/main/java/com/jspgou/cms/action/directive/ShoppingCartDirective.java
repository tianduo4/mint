/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.web.servlet.support.RequestContext;
/*    */ 
/*    */ public class ShoppingCartDirective extends WebDirective
/*    */ {
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 34 */     Website web = getWeb(env, params, this.websiteMng);
/* 35 */     RequestContext ctx = getContext(env);
/* 36 */     Map origMap = 
/* 37 */       DirectiveUtils.addParamsToVariable(env, params);
/* 38 */     includeTpl(web, ctx, env, null);
/* 39 */     DirectiveUtils.removeParamsFromVariable(env, params, origMap);
/*    */   }
/*    */ 
/*    */   private void includeTpl(Website web, RequestContext ctx, Environment env, HttpServletRequest request) throws IOException, TemplateException
/*    */   {
/* 44 */     String tpl = web
/* 45 */       .getTplSys("shop", ctx.getMessage("tpl.shoppingCart"), request);
/* 46 */     env.include(tpl, "UTF-8", true);
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng)
/*    */   {
/* 53 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.ShoppingCartDirective
 * JD-Core Version:    0.6.0
 */