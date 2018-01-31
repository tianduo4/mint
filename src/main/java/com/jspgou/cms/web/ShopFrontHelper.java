/*     */ package com.jspgou.cms.web;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.web.threadvariable.GroupThread;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import com.jspgou.core.web.front.URLHelper;
/*     */ import com.jspgou.core.web.front.URLHelper.PageInfo;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.util.UrlPathHelper;
/*     */ 
/*     */ public abstract class ShopFrontHelper
/*     */ {
/*     */   public static final String TPL_ERROR_PAGE = "tpl.errorPage";
/*     */   public static final String SUCCESS_PAGE = "tpl.successPage";
/*     */ 
/*     */   public static void setDynamicPageData(HttpServletRequest request, ModelMap model, Website web, String location, String urlPrefix, String urlSuffix, int pageNo)
/*     */   {
/*  73 */     FrontHelper.setDynamicPageData(request, model, web, location, 
/*  74 */       urlPrefix, urlSuffix, pageNo);
/*  75 */     setShopDate(request, model);
/*     */   }
/*     */ 
/*     */   public static String showSuccess(HttpServletRequest request, ModelMap model, String nextUrl)
/*     */   {
/*  87 */     Website web = SiteUtils.getWeb(request);
/*  88 */     setCommonData(request, model, web, 1);
/*  89 */     if (!StringUtils.isBlank(nextUrl)) {
/*  90 */       model.put("nextUrl", nextUrl);
/*     */     }
/*  92 */     return web.getTplSys("common", MessageResolver.getMessage(request, 
/*  93 */       "tpl.successPage", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public static void frontPageData(HttpServletRequest request, Map<String, Object> map)
/*     */   {
/* 104 */     int pageNo = URLHelper.getPageNo(request);
/* 105 */     URLHelper.PageInfo info = URLHelper.getPageInfo(request);
/* 106 */     String href = info.getHref();
/* 107 */     String hrefFormer = info.getHrefFormer();
/* 108 */     String hrefLatter = info.getHrefLatter();
/* 109 */     frontPageData(pageNo, href, hrefFormer, hrefLatter, map);
/*     */   }
/*     */ 
/*     */   public static void frontPage(HttpServletRequest request, Map<String, Object> map)
/*     */   {
/* 120 */     int pageNo = URLHelper.getPageNo(request);
/* 121 */     URLHelper.PageInfo info = URLHelper.getPageInfo(request);
/* 122 */     String href = info.getHref();
/* 123 */     String hrefFormer = info.getHrefFormer();
/* 124 */     String hrefLatter = info.getHrefLatter();
/* 125 */     frontPageData(pageNo, href, hrefFormer, hrefLatter, map);
/*     */   }
/*     */ 
/*     */   public static void frontPageData(int pageNo, String href, String hrefFormer, String hrefLatter, Map<String, Object> map)
/*     */   {
/* 139 */     map.put("pageNo", Integer.valueOf(pageNo));
/* 140 */     map.put("href", href);
/* 141 */     map.put("hrefFormer", hrefFormer);
/* 142 */     map.put("hrefLatter", hrefLatter);
/*     */   }
/*     */ 
/*     */   public static void setCommonData(HttpServletRequest request, ModelMap model, Website web, int pageNo)
/*     */   {
/* 155 */     FrontHelper.setCommonData(request, model, web, pageNo);
/* 156 */     setShopDate(request, model);
/*     */   }
/*     */ 
/*     */   public static void setCommon(HttpServletRequest request, ModelMap model, Website web)
/*     */   {
/* 169 */     ShopMember member = (ShopMember)request.getAttribute("_member_key");
/* 170 */     if (member != null) {
/* 171 */       model.addAttribute("member", member);
/* 172 */       model.addAttribute("group", GroupThread.get());
/*     */     }
/* 174 */     model.addAttribute("config", request.getAttribute("_shop_config_key"));
/* 175 */     FrontHelper.setCommon(request, model, web);
/*     */   }
/*     */ 
/*     */   public static void setShopDate(HttpServletRequest request, ModelMap model)
/*     */   {
/* 185 */     model.addAttribute("config", request.getAttribute("_shop_config_key"));
/*     */ 
/* 188 */     ShopMember member = MemberThread.get();
/* 189 */     if (member != null) {
/* 190 */       model.addAttribute("sessionKey", MemberThread.getSessionKey());
/* 191 */       model.addAttribute("userName", MemberThread.getUserName());
/* 192 */       model.addAttribute("member", member);
/* 193 */       model.addAttribute("group", GroupThread.get());
/*     */     }
/* 195 */     Website site = com.jspgou.core.web.SiteUtils.getWeb(request);
/* 196 */     model.addAttribute("baseUrl", site.getUploadResUrlBuff());
/*     */   }
/*     */ 
/*     */   public static String showError(HttpServletRequest request, HttpServletResponse response, ModelMap model, WebErrors errors)
/*     */   {
/* 209 */     Website web = SiteUtils.getWeb(request);
/* 210 */     setCommonData(request, model, web, 1);
/* 211 */     errors.toModel(model);
/* 212 */     return web.getTplSys("common", MessageResolver.getMessage(request, 
/* 213 */       "tpl.errorPage", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public static String getLocation(HttpServletRequest request) {
/* 217 */     UrlPathHelper helper = new UrlPathHelper();
/* 218 */     StringBuffer buff = request.getRequestURL();
/* 219 */     String uri = request.getRequestURI();
/* 220 */     String origUri = helper.getOriginatingRequestUri(request);
/* 221 */     buff.replace(buff.length() - uri.length(), buff.length(), origUri);
/* 222 */     String queryString = helper.getOriginatingQueryString(request);
/* 223 */     if (queryString != null) {
/* 224 */       buff.append("?").append(queryString);
/*     */     }
/* 226 */     return buff.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.ShopFrontHelper
 * JD-Core Version:    0.6.0
 */