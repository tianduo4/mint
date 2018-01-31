/*     */ package com.jspgou.common.httpClient;
/*     */ 
/*     */ import org.apache.commons.httpclient.NameValuePair;
/*     */ 
/*     */ public class HttpRequest
/*     */ {
/*     */   public static final String METHOD_GET = "GET";
/*     */   public static final String METHOD_POST = "POST";
/*  24 */   private String url = null;
/*     */ 
/*  29 */   private String method = "POST";
/*     */ 
/*  31 */   private int timeout = 0;
/*     */ 
/*  33 */   private int connectionTimeout = 0;
/*     */ 
/*  38 */   private NameValuePair[] parameters = null;
/*     */ 
/*  43 */   private String queryString = null;
/*     */ 
/*  48 */   private String charset = "GBK";
/*     */   private String clientIp;
/*  58 */   private HttpResultType resultType = HttpResultType.BYTES;
/*     */ 
/*     */   public HttpRequest(HttpResultType resultType)
/*     */   {
/*  62 */     this.resultType = resultType;
/*     */   }
/*     */ 
/*     */   public String getClientIp()
/*     */   {
/*  69 */     return this.clientIp;
/*     */   }
/*     */ 
/*     */   public void setClientIp(String clientIp)
/*     */   {
/*  76 */     this.clientIp = clientIp;
/*     */   }
/*     */ 
/*     */   public NameValuePair[] getParameters() {
/*  80 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   public void setParameters(NameValuePair[] parameters) {
/*  84 */     this.parameters = parameters;
/*     */   }
/*     */ 
/*     */   public String getQueryString() {
/*  88 */     return this.queryString;
/*     */   }
/*     */ 
/*     */   public void setQueryString(String queryString) {
/*  92 */     this.queryString = queryString;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  96 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) {
/* 100 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public String getMethod() {
/* 104 */     return this.method;
/*     */   }
/*     */ 
/*     */   public void setMethod(String method) {
/* 108 */     this.method = method;
/*     */   }
/*     */ 
/*     */   public int getConnectionTimeout() {
/* 112 */     return this.connectionTimeout;
/*     */   }
/*     */ 
/*     */   public void setConnectionTimeout(int connectionTimeout) {
/* 116 */     this.connectionTimeout = connectionTimeout;
/*     */   }
/*     */ 
/*     */   public int getTimeout() {
/* 120 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   public void setTimeout(int timeout) {
/* 124 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public String getCharset()
/*     */   {
/* 131 */     return this.charset;
/*     */   }
/*     */ 
/*     */   public void setCharset(String charset)
/*     */   {
/* 138 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */   public HttpResultType getResultType() {
/* 142 */     return this.resultType;
/*     */   }
/*     */ 
/*     */   public void setResultType(HttpResultType resultType) {
/* 146 */     this.resultType = resultType;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.httpClient.HttpRequest
 * JD-Core Version:    0.6.0
 */