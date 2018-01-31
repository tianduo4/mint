/*     */ package com.jspgou.core.web.front;
/*     */ 
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.ui.ModelMap;
/*     */ 
/*     */ public abstract class FrontHelper
/*     */ {
/*     */   public static final String TPL_PAGE_NOT_FOUND = "tpl.pageNotFound";
/*     */   public static final String TPL_SUCCESS_PAGE = "tpl.successPage";
/*     */   public static final String TPL_ERROR_PAGE = "tpl.errorPage";
/*     */   public static final String TPL_MESSAGE_PAGE = "tpl.messagePage";
/*     */ 
/*     */   public static String pageNotFound(Website website, ModelMap modelmap, HttpServletRequest request)
/*     */   {
/*  26 */     setCommonData(request, modelmap, website, 1);
/*  27 */     return website.getTplSys("common", MessageResolver.getMessage(request, "tpl.pageNotFound", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public static String showSuccess(String s, String s1, Website website, ModelMap modelmap, HttpServletRequest request)
/*     */   {
/*  32 */     setCommonData(request, modelmap, website, 1);
/*  33 */     modelmap.addAttribute("message", s);
/*  34 */     if (!StringUtils.isBlank(s1))
/*  35 */       modelmap.addAttribute("backUrl", s1);
/*  36 */     return website.getTplSys("common", MessageResolver.getMessage(request, "tpl.successPage", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public static String showError(WebErrors weberrors, Website website, ModelMap modelmap, HttpServletRequest request)
/*     */   {
/*  42 */     setCommonData(request, modelmap, website, 1);
/*  43 */     weberrors.toModel(modelmap);
/*  44 */     return website.getTplSys("common", MessageResolver.getMessage(request, "tpl.errorPage", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public static String showMessage(String s, Website website, ModelMap modelmap, HttpServletRequest request)
/*     */   {
/*  49 */     setCommonData(request, modelmap, website, 1);
/*  50 */     modelmap.addAttribute("message", s);
/*  51 */     return website.getTplSys("common", MessageResolver.getMessage(request, "tpl.messagePage", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public static void setDynamicPageData(HttpServletRequest request, ModelMap model, Website web, String location, String urlPrefix, String urlSuffix, int pageNo)
/*     */   {
/*  56 */     model.addAttribute("urlPrefix", urlPrefix);
/*  57 */     model.addAttribute("urlSuffix", urlSuffix);
/*  58 */     setCommonData(request, model, web, pageNo, location);
/*     */   }
/*     */ 
/*     */   private static void setCommonData(HttpServletRequest request, ModelMap modelmap, Website website, int i, String s)
/*     */   {
/*  63 */     modelmap.addAttribute("location", s);
/*  64 */     modelmap.addAttribute("web", website);
/*  65 */     String s1 = (String)request.getAttribute("_base_domain_key");
/*  66 */     if (s1 != null) {
/*  67 */       modelmap.addAttribute("baseDomain", s1);
/*     */     }
/*  69 */     String s2 = (String)request.getAttribute("_login_url");
/*  70 */     if (s2 != null) {
/*  71 */       modelmap.addAttribute("loginUrl", getLoginUrl(s2, request.getContextPath(), s));
/*     */     }
/*  73 */     modelmap.addAttribute("root", website.getResBaseUrl());
/*  74 */     modelmap.addAttribute("sysResRoot", website.getFrontResUrl());
/*  75 */     modelmap.addAttribute("pageNo", Integer.valueOf(i));
/*  76 */     modelmap.addAttribute("_start_time", request.getAttribute("_start_time"));
/*     */   }
/*     */ 
/*     */   public static void setCommonData(HttpServletRequest request, ModelMap modelmap, Website website, int i)
/*     */   {
/*  81 */     StringBuffer originalURL = request.getRequestURL();
/*  82 */     Map parameters = request.getParameterMap();
/*  83 */     if ((parameters != null) && (parameters.size() > 0)) {
/*  84 */       originalURL.append("?");
/*  85 */       for (Iterator iter = parameters.keySet().iterator(); iter.hasNext(); ) {
/*  86 */         String key = (String)iter.next();
/*  87 */         String[] values = (String[])parameters.get(key);
/*  88 */         for (int j = 0; j < values.length; j++) {
/*  89 */           originalURL.append(key).append("=").append(values[j]).append("&");
/*     */         }
/*     */       }
/*     */     }
/*  93 */     setCommonData(request, modelmap, website, i, originalURL.toString());
/*     */   }
/*     */ 
/*     */   public static void setCommon(HttpServletRequest request, ModelMap modelmap, Website website)
/*     */   {
/*  98 */     String location = RequestUtils.getLocation(request);
/*  99 */     setCommon(request, modelmap, website, location);
/*     */   }
/*     */ 
/*     */   private static void setCommon(HttpServletRequest request, ModelMap modelmap, Website website, String location)
/*     */   {
/* 104 */     modelmap.addAttribute("location", location);
/* 105 */     modelmap.addAttribute("web", website);
/* 106 */     String baseDomain = (String)request.getAttribute("_base_domain_key");
/* 107 */     if (baseDomain != null) {
/* 108 */       modelmap.addAttribute("baseDomain", baseDomain);
/*     */     }
/* 110 */     String loginUrl = (String)request.getAttribute("_login_url");
/* 111 */     if (loginUrl != null) {
/* 112 */       modelmap.addAttribute("loginUrl", getLoginUrl(loginUrl, request.getContextPath(), location));
/*     */     }
/* 114 */     modelmap.addAttribute("root", website.getResBaseUrl());
/* 115 */     modelmap.addAttribute("_start_time", request.getAttribute("_start_time"));
/*     */   }
/*     */ 
/*     */   public static String getLoginUrl(String s, String s1, String s2) {
/* 119 */     StringBuilder stringbuilder = new StringBuilder();
/* 120 */     if (!s.startsWith("http")) {
/* 121 */       stringbuilder.append(s1);
/* 122 */       if (!s.startsWith("/")) {
/* 123 */         stringbuilder.append("/");
/*     */       }
/*     */     }
/* 126 */     stringbuilder.append(s).append("?returnUrl=").append(s2);
/* 127 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   public static String getLoginUrl(HttpServletRequest request) {
/* 131 */     StringBuffer originalURL = request.getRequestURL();
/* 132 */     Map parameters = request.getParameterMap();
/* 133 */     if ((parameters != null) && (parameters.size() > 0)) {
/* 134 */       originalURL.append("?");
/* 135 */       for (Iterator iter = parameters.keySet().iterator(); iter.hasNext(); ) {
/* 136 */         String key = (String)iter.next();
/* 137 */         String[] values = (String[])parameters.get(key);
/* 138 */         for (int i = 0; i < values.length; i++) {
/* 139 */           originalURL.append(key).append("=").append(values[i]).append("&");
/*     */         }
/*     */       }
/*     */     }
/* 143 */     return getLoginUrl((String)request.getAttribute("_login_url"), "", originalURL.toString());
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.front.FrontHelper
 * JD-Core Version:    0.6.0
 */