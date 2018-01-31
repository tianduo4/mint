/*     */ package com.jspgou.cms.web;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.web.util.UrlPathHelper;
/*     */ 
/*     */ public class RequestUtils1
/*     */ {
/*  29 */   private static final Logger log = LoggerFactory.getLogger(RequestUtils1.class);
/*     */ 
/*     */   public static String getQueryParam(HttpServletRequest request, String name)
/*     */   {
/*  42 */     if (StringUtils.isBlank(name)) {
/*  43 */       return null;
/*     */     }
/*  45 */     if (request.getMethod().equalsIgnoreCase("POST")) {
/*  46 */       return request.getParameter(name);
/*     */     }
/*  48 */     String s = request.getQueryString();
/*  49 */     if (StringUtils.isBlank(s))
/*  50 */       return null;
/*     */     try
/*     */     {
/*  53 */       s = URLDecoder.decode(s, "UTF-8");
/*     */     } catch (UnsupportedEncodingException e) {
/*  55 */       log.error("encoding UTF-8 not support?", e);
/*     */     }
/*  57 */     String[] values = (String[])parseQueryString(s).get(name);
/*  58 */     if ((values != null) && (values.length > 0)) {
/*  59 */       return values[(values.length - 1)];
/*     */     }
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */   public static Map<String, Object> getQueryParams(HttpServletRequest request)
/*     */   {
/*     */     Map map;
/*     */     Map map;
/*  68 */     if (request.getMethod().equalsIgnoreCase("POST")) {
/*  69 */       map = request.getParameterMap();
/*     */     }
/*     */     else {
/*  72 */       String s = request.getQueryString();
/*  73 */       if (StringUtils.isBlank(s))
/*  74 */         return new HashMap();
/*     */       try
/*     */       {
/*  77 */         s = URLDecoder.decode(s, "UTF-8");
/*     */       } catch (UnsupportedEncodingException e) {
/*  79 */         log.error("encoding UTF-8 not support?", e);
/*     */       }
/*  81 */       map = parseQueryString(s);
/*     */     }
/*     */ 
/*  84 */     Map params = new HashMap(map.size());
/*     */ 
/*  86 */     for (Map.Entry entry : map.entrySet()) {
/*  87 */       int len = ((String[])entry.getValue()).length;
/*  88 */       if (len == 1)
/*  89 */         params.put((String)entry.getKey(), ((String[])entry.getValue())[0]);
/*  90 */       else if (len > 1) {
/*  91 */         params.put((String)entry.getKey(), entry.getValue());
/*     */       }
/*     */     }
/*  94 */     return params;
/*     */   }
/*     */ 
/*     */   public static Map<String, String[]> parseQueryString(String s)
/*     */   {
/* 127 */     String[] valArray = null;
/* 128 */     if (s == null) {
/* 129 */       throw new IllegalArgumentException();
/*     */     }
/* 131 */     Map ht = new HashMap();
/* 132 */     StringTokenizer st = new StringTokenizer(s, "&");
/* 133 */     while (st.hasMoreTokens()) {
/* 134 */       String pair = st.nextToken();
/* 135 */       int pos = pair.indexOf('=');
/* 136 */       if (pos == -1) {
/*     */         continue;
/*     */       }
/* 139 */       String key = pair.substring(0, pos);
/* 140 */       String val = pair.substring(pos + 1, pair.length());
/* 141 */       if (ht.containsKey(key)) {
/* 142 */         String[] oldVals = (String[])ht.get(key);
/* 143 */         valArray = new String[oldVals.length + 1];
/* 144 */         for (int i = 0; i < oldVals.length; i++) {
/* 145 */           valArray[i] = oldVals[i];
/*     */         }
/* 147 */         valArray[oldVals.length] = val;
/*     */       } else {
/* 149 */         valArray = new String[1];
/* 150 */         valArray[0] = val;
/*     */       }
/* 152 */       ht.put(key, valArray);
/*     */     }
/* 154 */     return ht;
/*     */   }
/*     */ 
/*     */   public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix)
/*     */   {
/* 159 */     return getRequestMap(request, prefix, false);
/*     */   }
/*     */ 
/*     */   public static Map<String, String> getRequestMapWithPrefix(HttpServletRequest request, String prefix)
/*     */   {
/* 164 */     return getRequestMap(request, prefix, true);
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix)
/*     */   {
/* 170 */     Map map = new HashMap();
/* 171 */     Enumeration names = request.getParameterNames();
/*     */ 
/* 173 */     while (names.hasMoreElements()) {
/* 174 */       String name = (String)names.nextElement();
/* 175 */       if (name.startsWith(prefix)) {
/* 176 */         String key = nameWithPrefix ? name : name.substring(prefix.length());
/* 177 */         String value = StringUtils.join(request.getParameterValues(name), ',');
/* 178 */         map.put(key, value);
/*     */       }
/*     */     }
/* 181 */     return map;
/*     */   }
/*     */ 
/*     */   public static String getIpAddr(HttpServletRequest request)
/*     */   {
/* 196 */     String ip = request.getHeader("X-Real-IP");
/* 197 */     if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip))) {
/* 198 */       return ip;
/*     */     }
/* 200 */     ip = request.getHeader("X-Forwarded-For");
/* 201 */     if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip)))
/*     */     {
/* 203 */       int index = ip.indexOf(',');
/* 204 */       if (index != -1) {
/* 205 */         return ip.substring(0, index);
/*     */       }
/* 207 */       return ip;
/*     */     }
/*     */ 
/* 210 */     return request.getRemoteAddr();
/*     */   }
/*     */ 
/*     */   public static String getLocation(HttpServletRequest request)
/*     */   {
/* 223 */     UrlPathHelper helper = new UrlPathHelper();
/* 224 */     StringBuffer buff = request.getRequestURL();
/* 225 */     String uri = request.getRequestURI();
/* 226 */     String origUri = helper.getOriginatingRequestUri(request);
/* 227 */     buff.replace(buff.length() - uri.length(), buff.length(), origUri);
/* 228 */     String queryString = helper.getOriginatingQueryString(request);
/* 229 */     if (queryString != null) {
/* 230 */       buff.append("?").append(queryString);
/*     */     }
/* 232 */     return buff.toString();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.RequestUtils1
 * JD-Core Version:    0.6.0
 */