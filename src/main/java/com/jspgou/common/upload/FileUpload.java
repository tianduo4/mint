/*     */ package com.jspgou.common.upload;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class FileUpload
/*     */ {
/*     */   public String uploadFile(String url, String filePath, String type)
/*     */     throws Exception
/*     */   {
/*  32 */     File file = new File(filePath);
/*  33 */     String result = null;
/*  34 */     if ((!file.exists()) || (!file.isFile())) {
/*  35 */       return "文件路径错误";
/*     */     }
/*     */ 
/*  40 */     if (StringUtils.isNotBlank(type)) {
/*  41 */       url = url + "&type=" + type;
/*     */     }
/*  43 */     URL urlObj = new URL(url);
/*  44 */     HttpURLConnection con = null;
/*     */ 
/*  47 */     trustAllHttpsCertificates();
/*  48 */     HostnameVerifier hv = new HostnameVerifier() {
/*     */       public boolean verify(String urlHostName, SSLSession session) {
/*  50 */         System.out.println("Warning: URL Host: " + urlHostName + " vs. " + 
/*  51 */           session.getPeerHost());
/*  52 */         return true;
/*     */       }
/*     */     };
/*  55 */     HttpsURLConnection.setDefaultHostnameVerifier(hv);
/*  56 */     con = (HttpURLConnection)urlObj.openConnection();
/*     */ 
/*  61 */     con.setRequestMethod("POST");
/*  62 */     con.setDoInput(true);
/*  63 */     con.setDoOutput(true);
/*  64 */     con.setUseCaches(false);
/*     */ 
/*  66 */     con.setRequestProperty("Connection", "Keep-Alive");
/*  67 */     con.setRequestProperty("Charset", "UTF-8");
/*     */ 
/*  70 */     String BOUNDARY = "----------" + System.currentTimeMillis();
/*  71 */     con.setRequestProperty("content-type", "multipart/form-data; boundary=" + BOUNDARY);
/*     */ 
/*  78 */     StringBuilder sb = new StringBuilder();
/*  79 */     sb.append("--");
/*  80 */     sb.append(BOUNDARY);
/*  81 */     sb.append("\r\n");
/*  82 */     sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + 
/*  83 */       file.getName() + "\"\r\n");
/*  84 */     sb.append("Content-Type:application/octet-stream\r\n\r\n");
/*  85 */     byte[] head = sb.toString().getBytes("utf-8");
/*     */ 
/*  87 */     OutputStream out = new DataOutputStream(con.getOutputStream());
/*  88 */     out.write(head);
/*     */ 
/*  91 */     DataInputStream in = new DataInputStream(new FileInputStream(file));
/*  92 */     int bytes = 0;
/*  93 */     byte[] bufferOut = new byte[1024];
/*  94 */     while ((bytes = in.read(bufferOut)) != -1) {
/*  95 */       out.write(bufferOut, 0, bytes);
/*     */     }
/*  97 */     in.close();
/*     */ 
/*  99 */     byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
/* 100 */     out.write(foot);
/* 101 */     out.flush();
/* 102 */     out.close();
/*     */     try
/*     */     {
/* 115 */       StringBuffer buffer = new StringBuffer();
/* 116 */       BufferedReader reader = new BufferedReader(
/* 117 */         new InputStreamReader(con.getInputStream(), "UTF-8"));
/* 118 */       String line = null;
/* 119 */       while ((line = reader.readLine()) != null)
/*     */       {
/* 121 */         buffer.append(line);
/*     */       }
/* 123 */       if (result == null) {
/* 124 */         result = buffer.toString();
/*     */       }
/* 126 */       return buffer.toString();
/*     */     } catch (Exception e) {
/* 128 */       System.out.println("发送POST请求出现异常！" + e);
/* 129 */       e.printStackTrace();
/*     */     }
/* 131 */     return result;
/*     */   }
/*     */ 
/*     */   private static void trustAllHttpsCertificates() throws Exception {
/* 135 */     TrustManager[] trustAllCerts = new TrustManager[1];
/* 136 */     TrustManager tm = new miTM();
/* 137 */     trustAllCerts[0] = tm;
/* 138 */     SSLContext sc = 
/* 139 */       SSLContext.getInstance("SSL");
/* 140 */     sc.init(null, trustAllCerts, null);
/* 141 */     HttpsURLConnection.setDefaultSSLSocketFactory(sc
/* 142 */       .getSocketFactory());
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/* 175 */     String filePath = "d:/mv1.jpg";
/* 176 */     String token = "Jdr_B5dQzbWlmmTAlMxbpOZiUfe100laWKeNjRgqfYAJ2GkgCdbQCQO4gAA6e0qd7uYM8fhhzx9ehQBCHlQvKQ";
/* 177 */     String result = null;
/* 178 */     FileUpload fileUpload = new FileUpload();
/* 179 */     result = fileUpload.uploadFile(token, filePath, "image");
/* 180 */     System.out.println(result);
/*     */   }
/*     */ 
/*     */   static class miTM
/*     */     implements TrustManager, X509TrustManager
/*     */   {
/*     */     public X509Certificate[] getAcceptedIssuers()
/*     */     {
/* 148 */       return null;
/*     */     }
/*     */ 
/*     */     public boolean isServerTrusted(X509Certificate[] certs)
/*     */     {
/* 153 */       return true;
/*     */     }
/*     */ 
/*     */     public boolean isClientTrusted(X509Certificate[] certs)
/*     */     {
/* 158 */       return true;
/*     */     }
/*     */ 
/*     */     public void checkServerTrusted(X509Certificate[] certs, String authType)
/*     */       throws CertificateException
/*     */     {
/*     */     }
/*     */ 
/*     */     public void checkClientTrusted(X509Certificate[] certs, String authType)
/*     */       throws CertificateException
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.upload.FileUpload
 * JD-Core Version:    0.6.0
 */