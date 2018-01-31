/*     */ package com.jspgou.common.web;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.HttpClient;
/*     */ import org.apache.http.client.HttpResponseException;
/*     */ import org.apache.http.client.ResponseHandler;
/*     */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.apache.http.impl.client.HttpClients;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ 
/*     */ public class HttpClientUtil
/*     */ {
/*     */   public static HttpClientUtil getInstance()
/*     */   {
/*  54 */     return SingletonHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */   public String get(String url) {
/*  58 */     CharsetHandler handler = new CharsetHandler("UTF-8");
/*  59 */     CloseableHttpClient client = null;
/*     */     try {
/*  61 */       HttpGet httpget = new HttpGet(new URI(url));
/*  62 */       HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
/*  63 */       client = httpClientBuilder.build();
/*  64 */       client = (CloseableHttpClient)wrapClient(client);
/*  65 */       String str = (String)client.execute(httpget, handler);
/*     */       return str;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       return "";
/*     */     } finally {
/*     */       try {
/*  71 */         if (client != null)
/*  72 */           client.close();
/*     */       }
/*     */       catch (IOException e) {
/*  75 */         e.printStackTrace();
/*     */       }
/*     */     }
///*  77 */     throw localObject;
/*     */   }
/*     */ 
/*     */   public static String postParams(String url, Map<String, String> params)
/*     */   {
/*  84 */     HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
/*     */ 
/*  86 */     CloseableHttpClient client = httpClientBuilder.build();
/*  87 */     HttpPost post = new HttpPost(url);
/*  88 */     CloseableHttpResponse res = null;
/*     */     try
/*     */     {
/*  92 */       List nvps = new ArrayList();
/*  93 */       Set<String> keySet = params.keySet();
/*  94 */       for (String key : keySet) {
/*  95 */         nvps.add(new BasicNameValuePair(key, (String)params.get(key)));
/*     */       }
/*  97 */       post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
/*  98 */       res = client.execute(post);
/*  99 */       HttpEntity entity = res.getEntity();
/* 100 */       String str1 = EntityUtils.toString(entity, "utf-8");
/*     */       return str1;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 104 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 107 */         res.close();
/* 108 */         client.close();
/*     */       } catch (IOException e) {
/* 110 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 113 */     return "";
/*     */   }
/*     */ 
/*     */   public static String post(String url, String params, String contentType)
/*     */   {
/* 120 */     HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
/*     */ 
/* 122 */     CloseableHttpClient client = httpClientBuilder.build();
/* 123 */     client = (CloseableHttpClient)wrapClient(client);
/*     */ 
/* 126 */     HttpPost post = new HttpPost(url);
/* 127 */     CloseableHttpResponse res = null;
/*     */     try
/*     */     {
/* 130 */       StringEntity s = new StringEntity(params, "UTF-8");
/* 131 */       if (StringUtils.isBlank(contentType)) {
/* 132 */         s.setContentType("application/json");
/*     */       }
/* 134 */       s.setContentType(contentType);
/* 135 */       s.setContentEncoding("utf-8");
/* 136 */       post.setEntity(s);
/* 137 */       res = client.execute(post);
/* 138 */       HttpEntity entity = res.getEntity();
/* 139 */       String str = EntityUtils.toString(entity, "utf-8");
/*     */       return str;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 143 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 146 */         res.close();
/* 147 */         client.close();
/*     */       } catch (IOException e) {
/* 149 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 152 */     return "";
/*     */   }
/*     */ 
/*     */   public static String post(String urlStr, String xmlInfo) {
/* 156 */     String line1 = "";
/*     */     try {
/* 158 */       URL url = new URL(urlStr);
/* 159 */       URLConnection con = url.openConnection();
/* 160 */       con.setDoOutput(true);
/*     */ 
/* 162 */       con.setRequestProperty("Cache-Control", "no-cache");
/* 163 */       con.setRequestProperty("Content-Type", "text/xml");
/*     */ 
/* 165 */       OutputStreamWriter out = new OutputStreamWriter(con
/* 166 */         .getOutputStream());
/* 167 */       out.write(new String(xmlInfo.getBytes("utf-8")));
/* 168 */       out.flush();
/* 169 */       out.close();
/* 170 */       BufferedReader br = new BufferedReader(
/* 171 */         new InputStreamReader(con
/* 171 */         .getInputStream()));
/* 172 */       String line = "";
/* 173 */       for (line = br.readLine(); line != null; line = br.readLine()) {
/* 174 */         line1 = line1 + line;
/*     */       }
/* 176 */       return new String(line1.getBytes(), "utf-8");
/*     */     } catch (MalformedURLException e) {
/* 178 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 180 */       e.printStackTrace();
/*     */     }
/* 182 */     return null;
/*     */   }
/*     */ 
/*     */   private static HttpClient wrapClient(HttpClient base)
/*     */   {
/*     */     try
/*     */     {
/* 214 */       SSLContext ctx = SSLContext.getInstance("TLSv1");
/* 215 */       X509TrustManager tm = new X509TrustManager()
/*     */       {
/*     */         public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException
/*     */         {
/*     */         }
/*     */ 
/*     */         public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
/*     */         }
/*     */ 
/*     */         public X509Certificate[] getAcceptedIssuers() {
/* 225 */           return null;
/*     */         }
/*     */       };
/* 228 */       ctx.init(null, new TrustManager[] { tm }, null);
/* 229 */       SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new String[] { "TLSv1" }, null, 
/* 230 */         SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
/* 231 */       CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
/* 232 */       return httpclient;
/*     */     } catch (Exception ex) {
/*     */     }
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */   private class CharsetHandler
/*     */     implements ResponseHandler<String>
/*     */   {
/*     */     private String charset;
/*     */ 
/*     */     public CharsetHandler(String charset)
/*     */     {
/* 189 */       this.charset = charset;
/*     */     }
/*     */ 
/*     */     public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException
/*     */     {
/* 194 */       StatusLine statusLine = response.getStatusLine();
/* 195 */       if (statusLine.getStatusCode() >= 300) {
/* 196 */         throw new HttpResponseException(statusLine.getStatusCode(), 
/* 197 */           statusLine.getReasonPhrase());
/*     */       }
/* 199 */       HttpEntity entity = response.getEntity();
/* 200 */       if (entity != null) {
/* 201 */         if (!StringUtils.isBlank(this.charset)) {
/* 202 */           return EntityUtils.toString(entity, this.charset);
/*     */         }
/* 204 */         return EntityUtils.toString(entity);
/*     */       }
/*     */ 
/* 207 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingletonHolder
/*     */   {
/*  48 */     private static final HttpClientUtil INSTANCE = new HttpClientUtil();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.HttpClientUtil
 * JD-Core Version:    0.6.0
 */