/*     */ package com.jspgou.core.web.front;
/*     */ 
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.web.util.UrlPathHelper;
/*     */ 
/*     */ public class URLHelper
/*     */ {
/*     */   public static final String INDEX = "index";
/*     */ 
/*     */   public static int getPageNo(HttpServletRequest request)
/*     */   {
/*  26 */     return getPageNo(getURI(request));
/*     */   }
/*     */ 
/*     */   public static String[] getPaths(HttpServletRequest request)
/*     */   {
/*  36 */     return getPaths(getURI(request));
/*     */   }
/*     */ 
/*     */   public static String[] getParams(HttpServletRequest request)
/*     */   {
/*  46 */     return getParams(getURI(request));
/*     */   }
/*     */ 
/*     */   private static String getURI(HttpServletRequest request) {
/*  50 */     UrlPathHelper helper = new UrlPathHelper();
/*  51 */     String uri = helper.getOriginatingRequestUri(request);
/*  52 */     String ctx = helper.getOriginatingContextPath(request);
/*  53 */     if (!StringUtils.isBlank(ctx)) {
/*  54 */       return uri.substring(ctx.length());
/*     */     }
/*  56 */     return uri;
/*     */   }
/*     */ 
/*     */   public static PageInfo getPageInfo(HttpServletRequest request)
/*     */   {
/*  67 */     UrlPathHelper helper = new UrlPathHelper();
/*  68 */     String uri = helper.getOriginatingRequestUri(request);
/*  69 */     String queryString = helper.getOriginatingQueryString(request);
/*  70 */     return getPageInfo(uri, queryString);
/*     */   }
/*     */ 
/*     */   public static int getPageNo(String uri)
/*     */   {
/*  81 */     if (uri == null) {
/*  82 */       throw new IllegalArgumentException("URI can not be null");
/*     */     }
/*  84 */     if (!uri.startsWith("/")) {
/*  85 */       throw new IllegalArgumentException("URI must start width '/'");
/*     */     }
/*  87 */     int pageNo = 1;
/*  88 */     int bi = uri.indexOf("_");
/*  89 */     int mi = uri.indexOf("-");
/*  90 */     int pi = uri.indexOf(".");
/*  91 */     if (bi != -1)
/*     */     {
/*     */       String pageNoStr;
/*  93 */       if (mi != -1) {
/*  94 */         pageNoStr = uri.substring(bi + 1, mi);
/*     */       }
/*     */       else
/*     */       {
/*  96 */         if (pi != -1)
/*  97 */           pageNoStr = uri.substring(bi + 1, pi);
/*     */         else
/*  99 */           pageNoStr = uri.substring(bi + 1);
/*     */       }
/*     */       try
/*     */       {
/* 103 */         pageNo = Integer.valueOf(pageNoStr).intValue();
/*     */       } catch (Exception localException) {
/*     */       }
/*     */     }
/* 107 */     return pageNo;
/*     */   }
/*     */ 
/*     */   public static String[] getPaths(String uri)
/*     */   {
/* 118 */     if (uri == null) {
/* 119 */       throw new IllegalArgumentException("URI can not be null");
/*     */     }
/* 121 */     if (!uri.startsWith("/")) {
/* 122 */       throw new IllegalArgumentException("URI must start width '/'");
/*     */     }
/* 124 */     int bi = uri.indexOf("_");
/* 125 */     int mi = uri.indexOf("-");
/* 126 */     int pi = uri.indexOf(".");
/*     */     String pathStr;
/* 129 */     if (bi != -1) {
/* 130 */       pathStr = uri.substring(0, bi);
/*     */     }
/*     */     else
/*     */     {
/* 131 */       if (mi != -1) {
/* 132 */         pathStr = uri.substring(0, mi);
/*     */       }
/*     */       else
/*     */       {
/* 133 */         if (pi != -1)
/* 134 */           pathStr = uri.substring(0, pi);
/*     */         else
/* 136 */           pathStr = uri; 
/*     */       }
/*     */     }
/* 138 */     String[] paths = StringUtils.split(pathStr, '/');
/* 139 */     return paths;
/*     */   }
/*     */ 
/*     */   public static String[] getParams(String uri)
/*     */   {
/* 150 */     if (uri == null) {
/* 151 */       throw new IllegalArgumentException("URI can not be null");
/*     */     }
/* 153 */     if (!uri.startsWith("/")) {
/* 154 */       throw new IllegalArgumentException("URI must start width '/'");
/*     */     }
/* 156 */     int mi = uri.indexOf("-");
/* 157 */     int pi = uri.indexOf(".");
/*     */     String[] params;
/* 159 */     if (mi != -1)
/*     */     {
/*     */       String paramStr;
/* 161 */       if (pi != -1)
/* 162 */         paramStr = uri.substring(mi, pi);
/*     */       else {
/* 164 */         paramStr = uri.substring(mi);
/*     */       }
/* 166 */       params = new String[StringUtils.countMatches(paramStr, "-")];
/* 167 */       int fromIndex = 1;
/* 168 */       int nextIndex = 0;
/* 169 */       int i = 0;
/* 170 */       while ((nextIndex = paramStr.indexOf("-", fromIndex)) != -1) {
/* 171 */         params[(i++)] = paramStr.substring(fromIndex, nextIndex);
/* 172 */         fromIndex = nextIndex + 1;
/*     */       }
/* 174 */       params[(i++)] = paramStr.substring(fromIndex);
/*     */     } else {
/* 176 */       params = new String[0];
/*     */     }
/* 178 */     return params;
/*     */   }
/*     */ 
/*     */   public static PageInfo getPageInfo(String uri, String queryString)
/*     */   {
/* 191 */     if (uri == null) {
/* 192 */       return null;
/*     */     }
/* 194 */     if (!uri.startsWith("/")) {
/* 195 */       throw new IllegalArgumentException("URI must start width '/'");
/*     */     }
/* 197 */     int bi = uri.indexOf("_");
/* 198 */     int mi = uri.indexOf("-");
/* 199 */     int pi = uri.indexOf(".");
/* 200 */     int lastSpt = uri.lastIndexOf("/") + 1;
/*     */     String url;
/* 202 */     if (!StringUtils.isBlank(queryString))
/* 203 */       url = uri + "?" + queryString;
/*     */     else
/* 205 */       url = uri;
/*     */     String urlFormer;
/* 209 */     if (bi != -1) {
/* 210 */       urlFormer = uri.substring(lastSpt, bi);
/*     */     }
/*     */     else
/*     */     {
/* 211 */       if (mi != -1) {
/* 212 */         urlFormer = uri.substring(lastSpt, mi);
/*     */       }
/*     */       else
/*     */       {
/* 213 */         if (pi != -1)
/* 214 */           urlFormer = uri.substring(lastSpt, pi);
/*     */         else
/* 216 */           urlFormer = uri.substring(lastSpt);
/*     */       }
/*     */     }
/*     */     String urlLater;
/* 220 */     if (mi != -1) {
/* 221 */       urlLater = url.substring(mi);
/*     */     }
/*     */     else
/*     */     {
/* 222 */       if (pi != -1)
/* 223 */         urlLater = url.substring(pi);
/*     */       else
/* 225 */         urlLater = url.substring(uri.length());
/*     */     }
/* 227 */     String href = url.substring(lastSpt);
/* 228 */     return new PageInfo(href, urlFormer, urlLater);
/*     */   }
/*     */ 
/*     */   public static class PageInfo
/*     */   {
/*     */     private String href;
/*     */     private String hrefFormer;
/*     */     private String hrefLatter;
/*     */ 
/*     */     public PageInfo(String href, String hrefFormer, String hrefLatter)
/*     */     {
/* 254 */       this.href = href;
/* 255 */       this.hrefFormer = hrefFormer;
/* 256 */       this.hrefLatter = hrefLatter;
/*     */     }
/*     */ 
/*     */     public String getHref() {
/* 260 */       return this.href;
/*     */     }
/*     */ 
/*     */     public void setHref(String href) {
/* 264 */       this.href = href;
/*     */     }
/*     */ 
/*     */     public String getHrefFormer() {
/* 268 */       return this.hrefFormer;
/*     */     }
/*     */ 
/*     */     public void setHrefFormer(String hrefFormer) {
/* 272 */       this.hrefFormer = hrefFormer;
/*     */     }
/*     */ 
/*     */     public String getHrefLatter() {
/* 276 */       return this.hrefLatter;
/*     */     }
/*     */ 
/*     */     public void setHrefLatter(String hrefLatter) {
/* 280 */       this.hrefLatter = hrefLatter;
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.front.URLHelper
 * JD-Core Version:    0.6.0
 */