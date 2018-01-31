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
/*    */ public class CousultPageDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "CousultPage";
/*    */ 
/*    */   @Autowired
/*    */   private ConsultMng cousultMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 33 */     Website web = getWeb(env, params, this.websiteMng);
/* 34 */     Integer count = Integer.valueOf(getCount(params));
/* 35 */     Long memberId = getLong("memberId", params);
/* 36 */     Pagination pagination = this.cousultMng.getPageByMember(memberId, getPageNo(env), count.intValue(), true);
/* 37 */     Map paramWrap = new HashMap(
/* 38 */       params);
/* 39 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/* 40 */       .wrap(pagination));
/* 41 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
/* 42 */     Map origMap = 
/* 43 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 44 */     if (isInvokeTpl(params))
/* 45 */       includeTpl("shop", "CousultPage", web, params, env);
/*    */     else {
/* 47 */       renderBody(env, loopVars, body);
/*    */     }
/* 49 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 54 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng)
/*    */   {
/* 64 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.CousultPageDirective
 * JD-Core Version:    0.6.0
 */