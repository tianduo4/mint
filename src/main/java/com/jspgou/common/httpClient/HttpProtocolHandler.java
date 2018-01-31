/*     */ package com.jspgou.common.httpClient;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.HttpConnectionManager;
/*     */ import org.apache.commons.httpclient.HttpException;
/*     */ import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
/*     */ import org.apache.commons.httpclient.NameValuePair;
/*     */ import org.apache.commons.httpclient.methods.GetMethod;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.methods.multipart.FilePart;
/*     */ import org.apache.commons.httpclient.methods.multipart.FilePartSource;
/*     */ import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
/*     */ import org.apache.commons.httpclient.methods.multipart.Part;
/*     */ import org.apache.commons.httpclient.methods.multipart.StringPart;
/*     */ import org.apache.commons.httpclient.params.HttpClientParams;
/*     */ import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
/*     */ import org.apache.commons.httpclient.params.HttpMethodParams;
/*     */ import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
/*     */ 
/*     */ public class HttpProtocolHandler
/*     */ {
/*  35 */   private static String DEFAULT_CHARSET = "GBK";
/*     */ 
/*  38 */   private int defaultConnectionTimeout = 8000;
/*     */ 
/*  41 */   private int defaultSoTimeout = 30000;
/*     */ 
/*  44 */   private int defaultIdleConnTimeout = 60000;
/*     */ 
/*  46 */   private int defaultMaxConnPerHost = 30;
/*     */ 
/*  48 */   private int defaultMaxTotalConn = 80;
/*     */   private static final long defaultHttpConnectionManagerTimeout = 3000L;
/*     */   private HttpConnectionManager connectionManager;
/*  58 */   private static HttpProtocolHandler httpProtocolHandler = new HttpProtocolHandler();
/*     */ 
/*     */   public static HttpProtocolHandler getInstance()
/*     */   {
/*  66 */     return httpProtocolHandler;
/*     */   }
/*     */ 
/*     */   private HttpProtocolHandler()
/*     */   {
/*  74 */     this.connectionManager = new MultiThreadedHttpConnectionManager();
/*  75 */     this.connectionManager.getParams().setDefaultMaxConnectionsPerHost(this.defaultMaxConnPerHost);
/*  76 */     this.connectionManager.getParams().setMaxTotalConnections(this.defaultMaxTotalConn);
/*     */ 
/*  78 */     IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
/*  79 */     ict.addConnectionManager(this.connectionManager);
/*  80 */     ict.setConnectionTimeout(this.defaultIdleConnTimeout);
/*     */ 
/*  82 */     ict.start();
/*     */   }
/*     */ 
/*     */   public HttpResponse execute(HttpRequest request, String strParaFileName, String strFilePath)
/*     */     throws HttpException, IOException
/*     */   {
/*  95 */     HttpClient httpclient = new HttpClient(this.connectionManager);
/*     */ 
/*  98 */     int connectionTimeout = this.defaultConnectionTimeout;
/*  99 */     if (request.getConnectionTimeout() > 0) {
/* 100 */       connectionTimeout = request.getConnectionTimeout();
/*     */     }
/* 102 */     httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
/*     */ 
/* 105 */     int soTimeout = this.defaultSoTimeout;
/* 106 */     if (request.getTimeout() > 0) {
/* 107 */       soTimeout = request.getTimeout();
/*     */     }
/* 109 */     httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
/*     */ 
/* 112 */     httpclient.getParams().setConnectionManagerTimeout(3000L);
/*     */ 
/* 114 */     String charset = request.getCharset();
/* 115 */     charset = charset == null ? DEFAULT_CHARSET : charset;
/* 116 */     HttpMethod method = null;
/*     */ 
/* 119 */     if (request.getMethod().equals("GET")) {
/* 120 */       method = new GetMethod(request.getUrl());
/* 121 */       method.getParams().setCredentialCharset(charset);
/*     */ 
/* 124 */       method.setQueryString(request.getQueryString());
/* 125 */     } else if ((strParaFileName.equals("")) && (strFilePath.equals("")))
/*     */     {
/* 127 */       method = new PostMethod(request.getUrl());
/* 128 */       ((PostMethod)method).addParameters(request.getParameters());
/* 129 */       method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
/*     */     }
/*     */     else
/*     */     {
/* 133 */       method = new PostMethod(request.getUrl());
/* 134 */       List parts = new ArrayList();
/* 135 */       for (int i = 0; i < request.getParameters().length; i++) {
/* 136 */         parts.add(new StringPart(request.getParameters()[i].getName(), request.getParameters()[i].getValue(), charset));
/*     */       }
/*     */ 
/* 139 */       parts.add(new FilePart(strParaFileName, new FilePartSource(new File(strFilePath))));
/*     */ 
/* 142 */       ((PostMethod)method).setRequestEntity(new MultipartRequestEntity((Part[])parts.toArray(new Part[0]), new HttpMethodParams()));
/*     */     }
/*     */ 
/* 146 */     method.addRequestHeader("User-Agent", "Mozilla/4.0");
/* 147 */     HttpResponse response = new HttpResponse();
/*     */     try
/*     */     {
/* 150 */       httpclient.executeMethod(method);
/* 151 */       if (request.getResultType().equals(HttpResultType.STRING))
/* 152 */         response.setStringResult(method.getResponseBodyAsString());
/* 153 */       else if (request.getResultType().equals(HttpResultType.BYTES)) {
/* 154 */         response.setByteResult(method.getResponseBody());
/*     */       }
/* 156 */       response.setResponseHeaders(method.getResponseHeaders());
/*     */     }
/*     */     catch (UnknownHostException ex)
/*     */     {
/*     */       return null;
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/*     */       return null;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */       return null;
/*     */     } finally {
/* 167 */       method.releaseConnection(); } method.releaseConnection();
/*     */ 
/* 169 */     return response;
/*     */   }
/*     */ 
/*     */   protected String toString(NameValuePair[] nameValues)
/*     */   {
/* 179 */     if ((nameValues == null) || (nameValues.length == 0)) {
/* 180 */       return "null";
/*     */     }
/*     */ 
/* 183 */     StringBuffer buffer = new StringBuffer();
/*     */ 
/* 185 */     for (int i = 0; i < nameValues.length; i++) {
/* 186 */       NameValuePair nameValue = nameValues[i];
/*     */ 
/* 188 */       if (i == 0)
/* 189 */         buffer.append(nameValue.getName() + "=" + nameValue.getValue());
/*     */       else {
/* 191 */         buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
/*     */       }
/*     */     }
/*     */ 
/* 195 */     return buffer.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.httpClient.HttpProtocolHandler
 * JD-Core Version:    0.6.0
 */