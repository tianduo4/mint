/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.manager.ShopChannelMng;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
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
/*    */ public class FooterChannelDirective extends ProductAbstractDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ProductList";
/*    */ 
/*    */   @Autowired
/*    */   private ShopChannelMng manager;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 37 */     Website web = getWeb(env, params, this.websiteMng);
/* 38 */     Long beginId = getLong("beginId", params);
/* 39 */     Long endId = getLong("endId", params);
/* 40 */     List beanList = this.manager.getList(web.getId(), beginId, endId);
/* 41 */     Map paramWrap = new HashMap(
/* 42 */       params);
/* 43 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(beanList));
/* 44 */     Map origMap = 
/* 45 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 46 */     if (isInvokeTpl(params))
/* 47 */       includeTpl("shop", "ProductList", web, params, env);
/*    */     else {
/* 49 */       renderBody(env, loopVars, body);
/*    */     }
/* 51 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.FooterChannelDirective
 * JD-Core Version:    0.6.0
 */