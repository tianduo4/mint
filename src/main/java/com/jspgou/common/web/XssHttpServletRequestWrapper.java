/*     */ package com.jspgou.common.web;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletRequestWrapper;
/*     */ 
/*     */ public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper
/*     */ {
/*     */   public static final String UTF8 = "UTF-8";
/*     */   private String[] filterChars;
/*     */   private String[] replaceChars;
/*     */ 
/*     */   public XssHttpServletRequestWrapper(HttpServletRequest request, String filterChar, String replaceChar, String splitChar)
/*     */   {
/*  22 */     super(request);
/*  23 */     if ((filterChar != null) && (filterChar.length() > 0)) {
/*  24 */       this.filterChars = filterChar.split(splitChar);
/*     */     }
/*  26 */     if ((replaceChar != null) && (replaceChar.length() > 0))
/*  27 */       this.replaceChars = replaceChar.split(splitChar);
/*     */   }
/*     */ 
/*     */   public String getQueryString()
/*     */   {
/*  32 */     String value = super.getQueryString();
/*  33 */     if (value != null) {
/*  34 */       value = xssEncode(value);
/*     */     }
/*  36 */     return value;
/*     */   }
/*     */ 
/*     */   public String getParameter(String name)
/*     */   {
/*  46 */     String value = super.getParameter(xssEncode(name));
/*  47 */     if (value != null) {
/*  48 */       value = xssEncode(value);
/*     */     }
/*  50 */     return value;
/*     */   }
/*     */ 
/*     */   public String[] getParameterValues(String name)
/*     */   {
/*  55 */     String[] parameters = super.getParameterValues(name);
/*  56 */     if ((parameters == null) || (parameters.length == 0)) {
/*  57 */       return null;
/*     */     }
/*  59 */     for (int i = 0; i < parameters.length; i++) {
/*  60 */       parameters[i] = xssEncode(parameters[i]);
/*     */     }
/*  62 */     return parameters;
/*     */   }
/*     */ 
/*     */   public String getHeader(String name)
/*     */   {
/*  72 */     String value = super.getHeader(xssEncode(name));
/*  73 */     if (value != null) {
/*  74 */       value = xssEncode(value);
/*     */     }
/*  76 */     return value;
/*     */   }
/*     */ 
/*     */   private String xssEncode(String s)
/*     */   {
/*  86 */     if ((s == null) || (s.equals(""))) {
/*  87 */       return s;
/*     */     }
/*     */     try
/*     */     {
/*  91 */       s = URLDecoder.decode(s, "UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/*  94 */       e.printStackTrace();
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*  98 */     for (int i = 0; i < this.filterChars.length; i++) {
/*  99 */       if (s.contains(this.filterChars[i])) {
/* 100 */         s = s.replace(this.filterChars[i], this.replaceChars[i]);
/*     */       }
/*     */     }
/* 103 */     return s;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.XssHttpServletRequestWrapper
 * JD-Core Version:    0.6.0
 */