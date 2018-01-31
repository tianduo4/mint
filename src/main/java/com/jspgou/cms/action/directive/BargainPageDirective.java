/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.manager.OrderItemMng;
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
/*    */ public class BargainPageDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "bargainPage";
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   @Autowired
/*    */   private OrderItemMng orderItemMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 40 */     Website web = getWeb(env, params, this.websiteMng);
/* 41 */     Long productId = getLong("productId", params);
/* 42 */     Pagination pagination = this.orderItemMng.getOrderItem(Integer.valueOf(getPageNo(env)), Integer.valueOf(getCount(params)), productId);
/* 43 */     Map paramWrap = new HashMap(
/* 44 */       params);
/* 45 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/* 46 */       .wrap(pagination));
/* 47 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
/* 48 */     Map origMap = 
/* 49 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 50 */     if (isInvokeTpl(params))
/* 51 */       includeTpl("shop", "bargainPage", web, params, env);
/*    */     else {
/* 53 */       renderBody(env, loopVars, body);
/*    */     }
/* 55 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 60 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng)
/*    */   {
/* 70 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.BargainPageDirective
 * JD-Core Version:    0.6.0
 */