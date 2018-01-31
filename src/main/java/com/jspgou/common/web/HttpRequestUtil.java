/*    */ package com.jspgou.common.web;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.http.HttpEntity;
/*    */ import org.apache.http.HttpResponse;
/*    */ import org.apache.http.client.ClientProtocolException;
/*    */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*    */ import org.apache.http.client.methods.HttpGet;
/*    */ import org.apache.http.client.methods.HttpPost;
/*    */ import org.apache.http.impl.client.DefaultHttpClient;
/*    */ import org.apache.http.message.BasicNameValuePair;
/*    */ 
/*    */ public class HttpRequestUtil
/*    */ {
/*    */   public static final String REQUEST_TYPE_GET = "get";
/*    */   public static final String REQUEST_TYPE_POST = "post";
/*    */ 
/*    */   public static String request(String uri, Map<String, String> params, String type, String encoding)
/*    */     throws ClientProtocolException, IOException
/*    */   {
/* 44 */     String result = "";
/* 45 */     DefaultHttpClient httpClient = new DefaultHttpClient();
/* 46 */     HttpResponse httpResponse = null;
/*    */ 
/* 49 */     if ("get".equals(type))
/*    */     {
/* 51 */       if (params != null) {
/* 52 */         if (uri.indexOf("?") != -1)
/* 53 */           uri = uri + "&";
/*    */         else {
/* 55 */           uri = uri + "?";
/*    */         }
/* 57 */         for (String key : params.keySet()) {
/* 58 */           uri = uri + key + "=" + (String)params.get(key) + "&";
/*    */         }
/*    */       }
/* 61 */       HttpGet httpGet = new HttpGet(uri);
/* 62 */       httpResponse = httpClient.execute(httpGet);
/*    */     }
/* 65 */     else if ("post".equals(type)) {
/* 66 */       HttpPost httpPost = new HttpPost(uri);
/*    */ 
/* 68 */       if (params != null) {
/* 69 */         Object paramList = new ArrayList();
/* 70 */         for (String key : params.keySet()) {
/* 71 */           if (key != null) {
/* 72 */             ((List)paramList).add(new BasicNameValuePair(key, (String)params.get(key)));
/*    */           }
/*    */         }
/* 75 */         UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List)paramList, "UTF-8");
/* 76 */         httpPost.setEntity(entity);
/*    */       }
/* 78 */       httpResponse = httpClient.execute(httpPost);
/*    */     }
/*    */ 
/* 82 */     HttpEntity entity = httpResponse.getEntity();
/* 83 */     if (entity != null) {
/* 84 */       InputStream is = entity.getContent();
/*    */ 
/* 86 */       byte[] buff = new byte[9192];
/*    */       int l;
/* 87 */       while ((l = is.read(buff)) != -1)
/*    */       {
/* 88 */         result = result + new String(buff, 0, l, encoding);
/*    */       }
/*    */     }
/* 91 */     return (String)result;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.HttpRequestUtil
 * JD-Core Version:    0.6.0
 */