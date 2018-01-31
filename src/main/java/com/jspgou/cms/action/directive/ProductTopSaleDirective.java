/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.entity.OrderItem;
/*    */ import com.jspgou.cms.manager.OrderItemMng;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.ObjectWrapper;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class ProductTopSaleDirective extends ProductAbstractDirective
/*    */ {
/*    */   public static final String TPL_NAME = "ProductList";
/*    */ 
/*    */   @Autowired
/*    */   private OrderItemMng orderItemMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 45 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 46 */     Calendar c = Calendar.getInstance();
/* 47 */     c.setTime(new Date());
/* 48 */     int i = c.get(7);
/* 49 */     c.add(5, -i);
/* 50 */     Date d = c.getTime();
/*    */ 
/* 52 */     c.add(5, -7);
/* 53 */     Date dd = c.getTime();
/*    */ 
/* 55 */     Website web = getWeb(env, params, this.websiteMng);
/* 56 */     Integer count = Integer.valueOf(getCount(params));
/* 57 */     List oiList = this.orderItemMng.getOrderItem(d, dd);
/* 58 */     List productList = new ArrayList();
/* 59 */     for (int i1 = 0; i1 < oiList.size(); i1++) {
/* 60 */       Object[] o = (Object[])oiList.get(i1);
/* 61 */       productList.add(((OrderItem)o[0]).getProduct());
/* 62 */       if (i1 == count.intValue() - 1) {
/*    */         break;
/*    */       }
/*    */     }
/* 66 */     Map paramWrap = new HashMap(
/* 67 */       params);
/* 68 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(productList));
/* 69 */     Map origMap = 
/* 70 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 71 */     if (isInvokeTpl(params))
/* 72 */       includeTpl("shop", "ProductList", web, params, env);
/*    */     else {
/* 74 */       renderBody(env, loopVars, body);
/*    */     }
/* 76 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.ProductTopSaleDirective
 * JD-Core Version:    0.6.0
 */