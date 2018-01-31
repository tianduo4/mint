/*     */ package com.jspgou.common.web;
/*     */ 
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class CookieUtils
/*     */ {
/*     */   public static final String COOKIE_PAGE_SIZE = "_cookie_page_size";
/*     */   public static final int DEFAULT_SIZE = 20;
/*     */   public static final int MAX_SIZE = 200;
/*     */ 
/*     */   public static int getPageSize(HttpServletRequest request)
/*     */   {
/*  38 */     Assert.notNull(request);
/*  39 */     Cookie cookie = getCookie(request, "_cookie_page_size");
/*  40 */     int count = 0;
/*  41 */     if (cookie != null)
/*     */       try {
/*  43 */         count = Integer.parseInt(cookie.getValue());
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/*  47 */     if (count <= 0)
/*  48 */       count = 20;
/*  49 */     else if (count > 200) {
/*  50 */       count = 200;
/*     */     }
/*  52 */     return count;
/*     */   }
/*     */ 
/*     */   public static Cookie getCookie(HttpServletRequest request, String name)
/*     */   {
/*  65 */     Assert.notNull(request);
/*  66 */     Cookie[] cookies = request.getCookies();
/*  67 */     if ((cookies != null) && (cookies.length > 0)) {
/*  68 */       for (Cookie c : cookies) {
/*  69 */         if (c.getName().equals(name)) {
/*  70 */           return c;
/*     */         }
/*     */       }
/*     */     }
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String domain, String path)
/*     */   {
/*  80 */     Cookie cookie = new Cookie(name, value);
/*  81 */     if (expiry != null) {
/*  82 */       cookie.setMaxAge(expiry.intValue());
/*     */     }
/*  84 */     if (StringUtils.isNotBlank(domain)) {
/*  85 */       cookie.setDomain(domain);
/*     */     }
/*  87 */     cookie.setPath(path);
/*  88 */     response.addCookie(cookie);
/*  89 */     return cookie;
/*     */   }
/*     */ 
/*     */   public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String domain)
/*     */   {
/* 106 */     Cookie cookie = new Cookie(name, value);
/* 107 */     if (expiry != null) {
/* 108 */       cookie.setMaxAge(expiry.intValue());
/*     */     }
/* 110 */     if (StringUtils.isNotBlank(domain)) {
/* 111 */       cookie.setDomain(domain);
/*     */     }
/* 113 */     String ctx = request.getContextPath();
/* 114 */     cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
/* 115 */     response.addCookie(cookie);
/* 116 */     return cookie;
/*     */   }
/*     */ 
/*     */   public static void cancleCookie(HttpServletResponse response, String name, String domain)
/*     */   {
/* 128 */     Cookie cookie = new Cookie(name, null);
/* 129 */     cookie.setMaxAge(0);
/* 130 */     cookie.setPath("/");
/* 131 */     if (!StringUtils.isBlank(domain)) {
/* 132 */       cookie.setDomain(domain);
/*     */     }
/* 134 */     response.addCookie(cookie);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.CookieUtils
 * JD-Core Version:    0.6.0
 */