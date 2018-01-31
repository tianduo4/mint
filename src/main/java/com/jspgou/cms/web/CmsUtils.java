/*     */ package com.jspgou.cms.web;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class CmsUtils
/*     */ {
/*     */   public static final String REQUEST_MEMBER_KEY = "_member_key";
/*     */   public static final String REQUEST_ADMIN_KEY = "_admin_key";
/*     */   public static final String REQUEST_WEBSITE_KEY = "_web_key";
/*     */   public static final String REQUEST_BASE_DOMAIN_KEY = "_base_domain_key";
/*     */ 
/*     */   public static ShopMember getMember(HttpServletRequest request)
/*     */   {
/*  42 */     return (ShopMember)request.getAttribute("_member_key");
/*     */   }
/*     */ 
/*     */   public static void setAdmin(HttpServletRequest request, ShopAdmin admin)
/*     */   {
/*  47 */     request.setAttribute("_admin_key", admin);
/*     */   }
/*     */ 
/*     */   public static ShopAdmin getAdmin(HttpServletRequest request) {
/*  51 */     return (ShopAdmin)request.getAttribute("_admin_key");
/*     */   }
/*     */ 
/*     */   public static Long getMemberId(HttpServletRequest request)
/*     */   {
/*  61 */     ShopMember member = getMember(request);
/*  62 */     if (member != null) {
/*  63 */       return member.getId();
/*     */     }
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */   public static void setMember(HttpServletRequest request, ShopMember member)
/*     */   {
/*  76 */     request.setAttribute("_member_key", member);
/*     */   }
/*     */ 
/*     */   public static Website getWebsite(HttpServletRequest request)
/*     */   {
/*  86 */     return (Website)request.getAttribute("_web_key");
/*     */   }
/*     */ 
/*     */   public static void setWebsite(HttpServletRequest request, Website web)
/*     */   {
/*  96 */     request.setAttribute("_web_key", web);
/*     */   }
/*     */ 
/*     */   public static Long getWebsiteId(HttpServletRequest request)
/*     */   {
/* 106 */     return getWebsite(request).getId();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.CmsUtils
 * JD-Core Version:    0.6.0
 */