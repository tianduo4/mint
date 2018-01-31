/*     */ package com.jspgou.cms.web;
/*     */ 
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import freemarker.core.Environment;
/*     */ import freemarker.template.AdapterTemplateModel;
/*     */ import freemarker.template.TemplateException;
/*     */ import freemarker.template.TemplateModel;
/*     */ import freemarker.template.TemplateModelException;
/*     */ import freemarker.template.TemplateNumberModel;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.ui.ModelMap;
/*     */ 
/*     */ public class FrontUtils
/*     */ {
/*     */   public static final String SITE = "site";
/*     */   public static final String PARAM_SYS_PAGE = "sysPage";
/*     */   public static final String PARAM_USER_PAGE = "userPage";
/*     */   public static final String COUNT = "count";
/*     */   public static final String FIRST = "first";
/*     */   public static final String PAGE_NO = "pageNo";
/*     */   public static final String MESSAGE = "message";
/*     */   public static final String ARGS = "args";
/*     */   public static final String RETURN_URL = "returnUrl";
/*     */   public static final String RES_EXP = "${res}";
/*     */   public static final String MOBILE_RES_TPL = "mobileRes";
/*     */ 
/*     */   public static Website getSite(Environment env)
/*     */     throws TemplateModelException
/*     */   {
/*  48 */     TemplateModel model = env.getGlobalVariable("site");
/*  49 */     if ((model instanceof AdapterTemplateModel)) {
/*  50 */       return (Website)((AdapterTemplateModel)model)
/*  51 */         .getAdaptedObject(Website.class);
/*     */     }
/*  53 */     throw new TemplateModelException("'site' not found in DataModel");
/*     */   }
/*     */ 
/*     */   public static String getTplPath(HttpServletRequest request, String solution, String dir, String name)
/*     */   {
/* 126 */     String equipment = (String)request.getAttribute("ua");
/*     */ 
/* 128 */     Website site = SiteUtils.getWeb(request);
/* 129 */     if ((StringUtils.isNotBlank(equipment)) && (equipment.equals("mobile"))) {
/* 130 */       solution = site.getMobileSolutionPath();
/*     */     }
/* 132 */     return solution + "/" + dir + "/" + MessageResolver.getMessage(request, name, new Object[0]) + ".html";
/*     */   }
/*     */ 
/*     */   public static int getCount(Map<String, TemplateModel> params)
/*     */     throws TemplateException
/*     */   {
/* 145 */     Integer count = DirectiveUtils.getInt("count", params);
/* 146 */     if ((count == null) || (count.intValue() <= 0) || (count.intValue() >= 5000)) {
/* 147 */       return 5000;
/*     */     }
/* 149 */     return count.intValue();
/*     */   }
/*     */ 
/*     */   public static int getFirst(Map<String, TemplateModel> params)
/*     */     throws TemplateException
/*     */   {
/* 162 */     Integer first = DirectiveUtils.getInt("first", params);
/* 163 */     if ((first == null) || (first.intValue() <= 0)) {
/* 164 */       return 0;
/*     */     }
/* 166 */     return first.intValue() - 1;
/*     */   }
/*     */ 
/*     */   public static int getPageNo(Environment env)
/*     */     throws TemplateException
/*     */   {
/* 178 */     TemplateModel pageNo = env.getGlobalVariable("pageNo");
/* 179 */     if ((pageNo instanceof TemplateNumberModel)) {
/* 180 */       return ((TemplateNumberModel)pageNo).getAsNumber().intValue();
/*     */     }
/* 182 */     throw new TemplateModelException("'pageNo' not found in DataModel.");
/*     */   }
/*     */ 
/*     */   public static void includePagination(Map<String, TemplateModel> params, Environment env)
/*     */     throws TemplateException, IOException
/*     */   {
/* 190 */     String sysPage = DirectiveUtils.getString("sysPage", params);
/* 191 */     String userPage = DirectiveUtils.getString("userPage", params);
/* 192 */     if (!StringUtils.isBlank(sysPage)) {
/* 193 */       String tpl = "/WEB-INF/t/gou_sys_defined/style_page/channel_" + sysPage + ".html";
/* 194 */       env.include(tpl, "UTF-8", true); } else {
/* 195 */       StringUtils.isBlank(userPage);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String showMessage(HttpServletRequest request, ModelMap model, String message)
/*     */   {
/* 215 */     Website web = SiteUtils.getWeb(request);
/* 216 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 217 */     model.put("message", message);
/* 218 */     return web.getTplSys("common", MessageResolver.getMessage(request, "tpl.messagePage", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public static String showLogin(HttpServletRequest request, ModelMap model)
/*     */   {
/* 232 */     StringBuilder buff = new StringBuilder("redirect:");
/* 233 */     buff.append("/login.jspx").append("?");
/* 234 */     buff.append("returnUrl").append("=");
/* 235 */     buff.append(RequestUtils.getLocation(request));
/* 236 */     return buff.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.FrontUtils
 * JD-Core Version:    0.6.0
 */