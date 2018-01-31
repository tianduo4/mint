/*    */ package com.jspgou.plug.weixin.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.web.FrontUtils;
/*    */ import com.jspgou.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.plug.weixin.entity.Weixin;
/*    */ import com.jspgou.plug.weixin.manager.WeixinMng;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.DefaultObjectWrapper;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class WeixinDirective
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private WeixinMng manager;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 30 */     Website site = FrontUtils.getSite(env);
/* 31 */     Weixin entity = this.manager.find(site.getId());
/*    */ 
/* 33 */     Map paramWrap = new HashMap(
/* 34 */       params);
/* 35 */     paramWrap.put("tag_bean", DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(entity));
/* 36 */     Map origMap = 
/* 37 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 38 */     body.render(env.getOut());
/* 39 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.action.directive.WeixinDirective
 * JD-Core Version:    0.6.0
 */