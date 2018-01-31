/*     */ package com.jspgou.common.web;
/*     */ 
/*     */ import java.io.PrintStream;
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
/*     */ public class RequestUtils
/*     */ {
/*  26 */   private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);
/*     */ 
/*     */   public static String getRequestAgreement(HttpServletRequest request)
/*     */   {
/*  30 */     return request.getRequestURL() != null ? request.getRequestURL().substring(0, request.getRequestURL().indexOf("://") + 3) : "http://";
/*     */   }
/*     */ 
/*     */   public static String getQueryParam(HttpServletRequest request, String name)
/*     */   {
/*  45 */     String s = request.getQueryString();
/*  46 */     if (StringUtils.isBlank(s))
/*  47 */       return null;
/*     */     try
/*     */     {
/*  50 */       s = URLDecoder.decode(s, "UTF-8");
/*     */     } catch (UnsupportedEncodingException e) {
/*  52 */       log.error("encoding UTF-8 not support?", e);
/*     */     }
/*  54 */     if (StringUtils.isBlank(s)) {
/*  55 */       return null;
/*     */     }
/*  57 */     String[] as = StringUtils.split(s, "&");
/*  58 */     String[] as1 = as;
/*  59 */     int i = as1.length;
/*  60 */     for (int j = 0; j < i; j++) {
/*  61 */       String s2 = as1[j];
/*  62 */       String[] as2 = StringUtils.split(s2, "=");
/*  63 */       int k = as2.length;
/*  64 */       if ((k >= 1) && (as2[0].equals(name))) {
/*  65 */         if (k == 2) {
/*  66 */           return as2[1];
/*     */         }
/*  68 */         return "";
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */   public static Map<String, Object> getQueryParams(HttpServletRequest request)
/*     */   {
/*     */     Map map;
/*     */     Map map;
/*  81 */     if (request.getMethod().equalsIgnoreCase("POST")) {
/*  82 */       map = request.getParameterMap();
/*     */     } else {
/*  84 */       String s = request.getQueryString();
/*  85 */       if (StringUtils.isBlank(s))
/*  86 */         return new HashMap();
/*     */       try
/*     */       {
/*  89 */         s = URLDecoder.decode(s, "UTF-8");
/*     */       } catch (UnsupportedEncodingException e) {
/*  91 */         log.error("encoding UTF-8 not support?", e);
/*     */       }
/*  93 */       map = parseQueryString(s);
/*     */     }
/*     */ 
/*  96 */     Map params = new HashMap(map.size());
/*     */ 
/*  98 */     for (Map.Entry entry : map.entrySet()) {
/*  99 */       int len = ((String[])entry.getValue()).length;
/* 100 */       if (len == 1)
/* 101 */         params.put((String)entry.getKey(), ((String[])entry.getValue())[0]);
/* 102 */       else if (len > 1) {
/* 103 */         params.put((String)entry.getKey(), entry.getValue());
/*     */       }
/*     */     }
/* 106 */     return params;
/*     */   }
/*     */ 
/*     */   public static Map<String, String[]> parseQueryString(String s)
/*     */   {
/* 139 */     String[] valArray = null;
/* 140 */     if (s == null) {
/* 141 */       throw new IllegalArgumentException();
/*     */     }
/* 143 */     Map ht = new HashMap();
/* 144 */     StringTokenizer st = new StringTokenizer(s, "&");
/* 145 */     while (st.hasMoreTokens()) {
/* 146 */       String pair = st.nextToken();
/* 147 */       int pos = pair.indexOf('=');
/* 148 */       if (pos == -1) {
/*     */         continue;
/*     */       }
/* 151 */       String key = pair.substring(0, pos);
/* 152 */       String val = pair.substring(pos + 1, pair.length());
/* 153 */       if (ht.containsKey(key)) {
/* 154 */         String[] oldVals = (String[])ht.get(key);
/* 155 */         valArray = new String[oldVals.length + 1];
/* 156 */         for (int i = 0; i < oldVals.length; i++) {
/* 157 */           valArray[i] = oldVals[i];
/*     */         }
/* 159 */         valArray[oldVals.length] = val;
/*     */       } else {
/* 161 */         valArray = new String[1];
/* 162 */         valArray[0] = val;
/*     */       }
/* 164 */       ht.put(key, valArray);
/*     */     }
/* 166 */     return ht;
/*     */   }
/*     */ 
/*     */   public static String getLocation(HttpServletRequest request)
/*     */   {
/* 178 */     UrlPathHelper helper = new UrlPathHelper();
/* 179 */     StringBuffer buff = request.getRequestURL();
/* 180 */     String uri = request.getRequestURI();
/* 181 */     String origUri = helper.getOriginatingRequestUri(request);
/* 182 */     buff.replace(buff.length() - uri.length(), buff.length(), origUri);
/* 183 */     String queryString = helper.getOriginatingQueryString(request);
/* 184 */     if (queryString != null) {
/* 185 */       buff.append("?").append(queryString);
/*     */     }
/* 187 */     return buff.toString();
/*     */   }
/*     */ 
/*     */   public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix)
/*     */   {
/* 192 */     return getRequestMap(request, prefix, false);
/*     */   }
/*     */ 
/*     */   public static String getIpAddr(HttpServletRequest request)
/*     */   {
/* 207 */     String ip = request.getHeader("X-Real-IP");
/* 208 */     if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip))) {
/* 209 */       return ip;
/*     */     }
/* 211 */     ip = request.getHeader("X-Forwarded-For");
/* 212 */     if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip)))
/*     */     {
/* 214 */       int index = ip.indexOf(',');
/* 215 */       if (index != -1) {
/* 216 */         return ip.substring(0, index);
/*     */       }
/* 218 */       return ip;
/*     */     }
/*     */ 
/* 221 */     return request.getRemoteAddr();
/*     */   }
/*     */ 
/*     */   private static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix)
/*     */   {
/* 229 */     Map map = new HashMap();
/* 230 */     Enumeration names = request.getParameterNames();
/*     */ 
/* 232 */     while (names.hasMoreElements()) {
/* 233 */       String name = (String)names.nextElement();
/* 234 */       if (name.startsWith(prefix)) {
/* 235 */         String key = nameWithPrefix ? name : name.substring(prefix.length());
/* 236 */         String value = StringUtils.join(request.getParameterValues(name), ',');
/* 237 */         map.put(key, value);
/*     */       }
/*     */     }
/* 240 */     return map;
/*     */   }
/*     */ 
/*     */   public static Map<String, String> getSignMap(HttpServletRequest request)
/*     */   {
/* 245 */     Map param = new HashMap();
/* 246 */     Enumeration penum = request.getParameterNames();
/* 247 */     while (penum.hasMoreElements()) {
/* 248 */       String pKey = (String)penum.nextElement();
/* 249 */       String value = request.getParameter(pKey);
/*     */ 
/* 251 */       if ((pKey.equals("sign")) || (pKey.equals("uploadFile")) || 
/* 252 */         (!StringUtils.isNotBlank(value))) continue;
/* 253 */       param.put(pKey, value);
/*     */     }
/*     */ 
/* 256 */     return param;
/*     */   }
/*     */   public static void main(String[] args) {
/* 259 */     System.out.println(StringUtils.split("", "=").length);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.RequestUtils
 * JD-Core Version:    0.6.0
 */