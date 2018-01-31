/*     */ package com.jspgou.cms.service.impl;
/*     */ 
/*     */ import com.jspgou.cms.service.ImageSvc;
/*     */ import com.jspgou.common.file.FileNameUtils;
/*     */ import com.jspgou.common.image.ImageUtils;
/*     */ import com.jspgou.common.upload.FileRepository;
/*     */ import com.jspgou.common.upload.UploadUtils;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.entity.Ftp;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ public class ImageSvcImpl
/*     */   implements ImageSvc
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   protected FileRepository fileRepository;
/*     */ 
/*     */   @Autowired
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   public String crawlImg(String imgUrl, Website site)
/*     */   {
/*  33 */     HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
/*  34 */     CloseableHttpClient client = httpClientBuilder.build();
/*  35 */     String outFileName = "";
/*  36 */     String fileUrl = "";
/*     */ 
/*  38 */     label318: 
/*     */     try { if (validUrl(imgUrl)) {
/*  39 */         URI uri = new URI(imgUrl);
/*  40 */         HttpGet httpget = new HttpGet(uri);
/*  41 */         HttpResponse response = client.execute(httpget);
/*  42 */         InputStream is = null;
/*  43 */         OutputStream os = null;
/*  44 */         HttpEntity entity = null;
/*  45 */         entity = response.getEntity();
/*  46 */         is = entity.getContent();
/*  47 */         String ctx = site.getContextPath();
/*  48 */         String ext = FileNameUtils.getFileSufix(imgUrl);
/*     */ 
/*  61 */         if (site.getUploadFtp() != null) {
/*  62 */           Ftp ftp = site.getUploadFtp();
/*  63 */           String ftpUrl = ftp.getUrl();
/*  64 */           fileUrl = ftp.storeByExt("/u", ext, is);
/*     */ 
/*  66 */           fileUrl = ftpUrl + fileUrl;
/*  67 */           break label318;
/*     */         }
/*     */ 
/*  70 */         String fileName = UploadUtils.generateRamdonFilename(FileNameUtils.getFileSufix(imgUrl));
/*  71 */         File outFile = new File(this.realPathResolver.get("/u"), fileName);
/*  72 */         UploadUtils.checkDirAndCreate(outFile.getParentFile());
/*  73 */         outFileName = "/u" + fileName;
/*  74 */         os = new FileOutputStream(outFile);
/*  75 */         IOUtils.copy(is, os);
/*     */ 
/*  77 */         if ((ctx != null) && (StringUtils.isNotBlank(ctx))) {
/*  78 */           fileUrl = ctx + outFileName;
/*  79 */           break label318;
/*  80 */         }fileUrl = outFileName;
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */       try
/*     */       {
/*  87 */         client.close();
/*     */       } catch (IOException e) {
/*  89 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  87 */         client.close();
/*     */       } catch (IOException e) {
/*  89 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  92 */     return fileUrl;
/*     */   }
/*     */ 
/*     */   public String crawlImg(String imgUrl, String ctx, boolean uploadToDb, String dbFileUri, Ftp ftp, String uploadPath) {
/*  96 */     HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
/*  97 */     CloseableHttpClient client = httpClientBuilder.build();
/*  98 */     String outFileName = "";
/*  99 */     String fileUrl = "";
/*     */ 
/* 101 */     label277: 
/*     */     try { if (validUrl(imgUrl)) {
/* 102 */         HttpGet httpget = new HttpGet(new URI(imgUrl));
/* 103 */         HttpResponse response = client.execute(httpget);
/* 104 */         InputStream is = null;
/* 105 */         OutputStream os = null;
/* 106 */         HttpEntity entity = null;
/* 107 */         entity = response.getEntity();
/* 108 */         is = entity.getContent();
/* 109 */         String ext = FileNameUtils.getFileSufix(imgUrl);
/*     */ 
/* 121 */         if (ftp != null) {
/* 122 */           String ftpUrl = ftp.getUrl();
/* 123 */           fileUrl = ftp.storeByExt(uploadPath, ext, is);
/*     */ 
/* 125 */           fileUrl = ftpUrl + fileUrl;
/* 126 */           break label277;
/* 127 */         }outFileName = UploadUtils.generateFilename(uploadPath, FileNameUtils.getFileSufix(imgUrl));
/* 128 */         File outFile = new File(this.realPathResolver.get(outFileName));
/* 129 */         UploadUtils.checkDirAndCreate(outFile.getParentFile());
/* 130 */         os = new FileOutputStream(outFile);
/* 131 */         IOUtils.copy(is, os);
/*     */ 
/* 133 */         if ((ctx != null) && (StringUtils.isNotBlank(ctx))) {
/* 134 */           fileUrl = ctx + outFileName;
/* 135 */           break label277;
/* 136 */         }fileUrl = outFileName;
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */       try
/*     */       {
/* 143 */         client.close();
/*     */       } catch (IOException e) {
/* 145 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 143 */         client.close();
/*     */       } catch (IOException e) {
/* 145 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 148 */     return fileUrl;
/*     */   }
/*     */ 
/*     */   private boolean validUrl(String imgUrl)
/*     */   {
/* 153 */     boolean isImage = true;
/*     */     try {
/* 155 */       URL url = new URL(imgUrl);
/* 156 */       URLConnection urlConnection = url.openConnection();
/* 157 */       InputStream inputStream = urlConnection.getInputStream();
/* 158 */       isImage = ImageUtils.isImage(inputStream);
/* 159 */       inputStream.close();
/*     */     } catch (Exception e) {
/* 161 */       isImage = false;
/*     */     }
/* 163 */     return isImage;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.service.impl.ImageSvcImpl
 * JD-Core Version:    0.6.0
 */