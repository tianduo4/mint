/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.manager.ProductMng;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.ObjectWrapper;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class HistoryRecordDirective extends ProductAbstractDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ProductList";
/*    */ 
/*    */   @Autowired
/*    */   private ProductMng productMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 38 */     Website web = getWeb(env, params, this.websiteMng);
/* 39 */     HashSet set = new HashSet();
/* 40 */     Integer count = getInt("count", params);
/* 41 */     String historyProductIds = getString("historyProductIds", params);
/* 42 */     if ((historyProductIds != null) && (!historyProductIds.equals(""))) {
/* 43 */       String[] pids = historyProductIds.split(",");
/* 44 */       if (pids.length > 0) {
/* 45 */         for (int i = 0; i < pids.length; i++) {
/* 46 */           set.add(Long.valueOf(pids[i]));
/*    */         }
/*    */       }
/*    */     }
/* 50 */     List list = this.productMng.getHistoryProduct(set, count);
/* 51 */     Map paramWrap = new HashMap(
/* 52 */       params);
/* 53 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
/* 54 */     Map origMap = 
/* 55 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 56 */     if (isInvokeTpl(params))
/* 57 */       includeTpl("shop", "ProductList", web, params, env);
/*    */     else {
/* 59 */       renderBody(env, loopVars, body);
/*    */     }
/* 61 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.HistoryRecordDirective
 * JD-Core Version:    0.6.0
 */