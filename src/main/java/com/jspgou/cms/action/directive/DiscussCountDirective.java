/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.DiscussMng;
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
/*    */ public class DiscussCountDirective extends WebDirective
/*    */ {
/*    */   private static final String TPL_NAME = "discussCount";
/*    */ 
/*    */   @Autowired
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   @Autowired
/*    */   private DiscussMng discussMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 31 */     Website web = getWeb(env, params, this.websiteMng);
/* 32 */     String discussType = getString("discussType", params);
/* 33 */     Long productId = getLong("productId", params);
/* 34 */     List list = this.discussMng.findByType(productId, discussType);
/* 35 */     Map paramWrap = new HashMap(params);
/* 36 */     paramWrap.put("discusses", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
/* 37 */     Map origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 38 */     if (isInvokeTpl(params))
/* 39 */       includeTpl("shop", "discussCount", web, params, env);
/*    */     else {
/* 41 */       renderBody(env, loopVars, body);
/*    */     }
/* 43 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 48 */     body.render(env.getOut());
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.DiscussCountDirective
 * JD-Core Version:    0.6.0
 */