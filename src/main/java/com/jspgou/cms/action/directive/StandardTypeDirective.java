/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.StandardTypeMng;
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
/*    */ public class StandardTypeDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "StandardType";
/*    */ 
/*    */   @Autowired
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   @Autowired
/*    */   private StandardTypeMng standardTypeMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 33 */     Website web = getWeb(env, params, this.websiteMng);
/* 34 */     Integer categoryId = getInt("categoryId", params);
/* 35 */     List standardTypeList = this.standardTypeMng
/* 36 */       .getList(categoryId);
/* 37 */     Map paramWrap = new HashMap(
/* 38 */       params);
/* 39 */     paramWrap.put("tag_list", 
/* 40 */       ObjectWrapper.DEFAULT_WRAPPER.wrap(standardTypeList));
/* 41 */     Map origMap = 
/* 42 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 43 */     if (isInvokeTpl(params))
/* 44 */       includeTpl("shop", "StandardType", web, params, env);
/*    */     else {
/* 46 */       renderBody(env, loopVars, body);
/*    */     }
/* 48 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 53 */     body.render(env.getOut());
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.StandardTypeDirective
 * JD-Core Version:    0.6.0
 */