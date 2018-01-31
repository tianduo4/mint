/*     */ package com.jspgou.cms.action.directive;
/*     */ 
/*     */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ShopScoreMng;
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
/*     */ public class ScorePageDirective extends WebDirective
/*     */ {
/*     */   public static final String TPL_NAME = "ShopScorePage";
/*     */   private ShopScoreMng shopScoreMng;
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*     */     throws TemplateException, IOException
/*     */   {
/*  46 */     ShopMember member = MemberThread.get();
/*  47 */     Website web = getWeb(env, params, this.websiteMng);
/*  48 */     Integer count = Integer.valueOf(getCount(params));
/*  49 */     Boolean status = getBool("status", params);
/*  50 */     Boolean useStatus = getBool("useStatus", params);
/*  51 */     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
/*  52 */     String startTime = getString("startTime", params);
/*  53 */     String endTime = getString("endTime", params);
/*  54 */     Date start = null;
/*  55 */     Date end = null;
/*     */     try {
/*  57 */       if (!StringUtils.isBlank(startTime))
/*  58 */         start = df.parse(startTime);
/*     */       else {
/*  60 */         start = null;
/*     */       }
/*  62 */       if (!StringUtils.isBlank(endTime))
/*  63 */         end = df.parse(endTime);
/*     */       else
/*  65 */         end = null;
/*     */     }
/*     */     catch (ParseException e) {
/*  68 */       e.printStackTrace();
/*     */     }
/*  70 */     Pagination pagination = this.shopScoreMng.getPage(member.getId(), status, useStatus, 
/*  71 */       start, end, Integer.valueOf(getPageNo(env)), count);
/*  72 */     Map paramWrap = new HashMap(
/*  73 */       params);
/*  74 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
/*  75 */       .wrap(pagination));
/*  76 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
/*  77 */     Map origMap = 
/*  78 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/*  79 */     if (isInvokeTpl(params))
/*  80 */       includeTpl("shop", "ShopScorePage", web, params, env);
/*     */     else {
/*  82 */       renderBody(env, loopVars, body);
/*     */     }
/*  84 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*     */   }
/*     */ 
/*     */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*     */   {
/*  89 */     body.render(env.getOut());
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setShopScoreMng(ShopScoreMng shopScoreMng)
/*     */   {
/*  97 */     this.shopScoreMng = shopScoreMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 102 */     this.websiteMng = websiteMng;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.ScorePageDirective
 * JD-Core Version:    0.6.0
 */