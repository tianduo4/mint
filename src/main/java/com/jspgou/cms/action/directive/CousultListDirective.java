/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.ConsultMng;
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
/*    */ public class CousultListDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "CousultList";
/*    */ 
/*    */   @Autowired
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   @Autowired
/*    */   private ConsultMng consultMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 29 */     Website web = getWeb(env, params, this.websiteMng);
/* 30 */     Integer count = Integer.valueOf(getCount(params));
/* 31 */     Long productId = getLong("productId", params);
/* 32 */     Pagination pagination = this.consultMng.getPage(productId, null, null, null, 
/* 33 */       null, getPageNo(env), count.intValue(), Boolean.valueOf(true));
/* 34 */     Map paramWrap = new HashMap(
/* 35 */       params);
/* 36 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/* 37 */       .wrap(pagination));
/* 38 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
/* 39 */     Map origMap = 
/* 40 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 41 */     if (isInvokeTpl(params))
/* 42 */       includeTpl("shop", "CousultList", web, params, env);
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
 * Qualified Name:     com.jspgou.cms.action.directive.CousultListDirective
 * JD-Core Version:    0.6.0
 */