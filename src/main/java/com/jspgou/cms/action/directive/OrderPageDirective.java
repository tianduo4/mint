/*     */ package com.jspgou.cms.action.directive;
/*     */ 
/*     */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import freemarker.core.Environment;
/*     */ import freemarker.template.ObjectWrapper;
/*     */ import freemarker.template.TemplateDirectiveBody;
/*     */ import freemarker.template.TemplateException;
/*     */ import freemarker.template.TemplateModel;
/*     */ import java.io.IOException;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ public class OrderPageDirective extends WebDirective
/*     */ {
/*     */   public static final String TPL_NAME = "ArticlePage";
/*     */   public static final String ALL = "all";
/*     */   public static final String PENDING = "pending";
/*     */   public static final String PROSESSING = "processing";
/*     */   public static final String DELIVERED = "delivered";
/*     */   public static final String COMPLETE = "complete";
/*     */   private OrderMng orderMng;
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*     */     throws TemplateException, IOException
/*     */   {
/*  53 */     ShopMember member = MemberThread.get();
/*  54 */     Website web = getWeb(env, params, this.websiteMng);
/*  55 */     Integer count = Integer.valueOf(getCount(params));
/*  56 */     Integer status = getInt("status", params);
/*  57 */     Integer paymentStatus = getInt("paymentStatus", params);
/*  58 */     Integer shippingStatus = getInt("shippingStatus", params);
/*  59 */     String userName = getString("userName", params);
/*  60 */     String productName = getString("productName", params);
/*  61 */     Long code = getLong("code", params);
/*  62 */     Long paymentId = getLong("paymentId", params);
/*  63 */     Long shippingId = getLong("shippingId", params);
/*  64 */     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
/*  65 */     String startTime = getString("startTime", params);
/*  66 */     String endTime = getString("endTime", params);
/*  67 */     Date start = null;
/*  68 */     Date end = null;
/*     */     try {
/*  70 */       if (!StringUtils.isBlank(startTime))
/*  71 */         start = df.parse(startTime);
/*     */       else {
/*  73 */         start = null;
/*     */       }
/*  75 */       if (!StringUtils.isBlank(endTime))
/*  76 */         end = df.parse(endTime);
/*     */       else
/*  78 */         end = null;
/*     */     }
/*     */     catch (ParseException e) {
/*  81 */       e.printStackTrace();
/*     */     }
/*  83 */     Double startOrderTotal = getDouble("startOrderTotal", params);
/*  84 */     Double endOrderTotal = getDouble("endOrderTotal", params);
/*     */ 
/*  88 */     Pagination pagination = this.orderMng.getPage(web.getId(), member.getId(), productName, userName, paymentId, shippingId, 
/*  89 */       start, end, startOrderTotal, endOrderTotal, status, paymentStatus, shippingStatus, code, getPageNo(env), count.intValue());
/*     */ 
/*  94 */     Map paramWrap = new HashMap(
/*  95 */       params);
/*  96 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/*  97 */       .wrap(pagination));
/*  98 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
/*     */ 
/* 100 */     Map origMap = 
/* 101 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 102 */     if (isInvokeTpl(params))
/* 103 */       includeTpl("shop", "ArticlePage", web, params, env);
/*     */     else {
/* 105 */       renderBody(env, loopVars, body);
/*     */     }
/* 107 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*     */   }
/*     */ 
/*     */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*     */   {
/* 112 */     body.render(env.getOut());
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setOrderMng(OrderMng orderMng)
/*     */   {
/* 120 */     this.orderMng = orderMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 125 */     this.websiteMng = websiteMng;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.OrderPageDirective
 * JD-Core Version:    0.6.0
 */