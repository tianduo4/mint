/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.PaymentPluginsMng;
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
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class PluginsListDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "PluginsList";
/*    */   public static final String PLATFORM = "platform";
/*    */ 
/*    */   @Autowired
/*    */   private PaymentPluginsMng paymentMng;
/*    */ 
/*    */   @Autowired
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 33 */     Website web = getWeb(env, params, this.websiteMng);
/* 34 */     String platform = getString("platform", params);
/* 35 */     List paylist = this.paymentMng.getList1(platform);
/* 36 */     Map paramWrap = new HashMap(
/* 37 */       params);
/* 38 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(paylist));
/* 39 */     Map origMap = 
/* 40 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 41 */     if (isInvokeTpl(params))
/* 42 */       includeTpl("shop", "PluginsList", web, params, env);
/*    */     else {
/* 44 */       renderBody(env, loopVars, body);
/*    */     }
/* 46 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 51 */     body.render(env.getOut());
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.PluginsListDirective
 * JD-Core Version:    0.6.0
 */