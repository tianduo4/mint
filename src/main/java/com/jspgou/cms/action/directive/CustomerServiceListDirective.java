/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.CustomerServiceMng;
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
/*    */ public class CustomerServiceListDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "CustomerServiceList";
/*    */   private CustomerServiceMng customerServiceMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 39 */     Website web = getWeb(env, params, this.websiteMng);
/* 40 */     List list = this.customerServiceMng.getList();
/* 41 */     Map paramWrap = new HashMap(params);
/* 42 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
/* 43 */     Map origMap = 
/* 44 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 45 */     if (isInvokeTpl(params))
/* 46 */       includeTpl("shop", "CustomerServiceList", web, params, env);
/*    */     else {
/* 48 */       renderBody(env, loopVars, body);
/*    */     }
/* 50 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 55 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setCustomerServiceMng(CustomerServiceMng customerServiceMng)
/*    */   {
/* 63 */     this.customerServiceMng = customerServiceMng;
/*    */   }
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 68 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.CustomerServiceListDirective
 * JD-Core Version:    0.6.0
 */