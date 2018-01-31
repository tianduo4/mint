/*     */ package com.jspgou.common.upload;
/*     */ 
/*     */ import com.jspgou.core.entity.Ftp;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.web.context.ServletContextAware;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ public class FileRepository
/*     */   implements Repository, ServletContextAware
/*     */ {
/*  22 */   private Logger log = LoggerFactory.getLogger(FileRepository.class);
/*     */   private ServletContext ctx;
/*     */ 
/*     */   public String upload(Website site, String ext, MultipartFile file)
/*     */     throws IOException
/*     */   {
/*  26 */     String url = "";
/*  27 */     Ftp ftp = site.getUploadFtp();
/*  28 */     if (ftp != null)
/*     */     {
/*  30 */       url = ftp.storeByExts("/u", ext, file.getInputStream());
/*     */     }
/*     */     else {
/*  33 */       url = storeByExt("/u", ext, file);
/*     */     }
/*  35 */     return url;
/*     */   }
/*     */ 
/*     */   public String storeByExt(String path, String ext, MultipartFile file)
/*     */     throws IOException
/*     */   {
/*  45 */     String fileName = UploadUtils.generateRamdonFilename(ext);
/*  46 */     String fileUrl = path + fileName;
/*  47 */     File dest = new File(getRealPath(path), fileName);
/*  48 */     dest = UploadUtils.getUniqueFile(dest);
/*  49 */     stores(file, dest);
/*  50 */     return fileUrl;
/*     */   }
/*     */ 
/*     */   public String storeByExts(String path, String ext, MultipartFile file) throws IOException
/*     */   {
/*  55 */     String fileName = UploadUtils.generateRamdonFilename(ext);
/*  56 */     String fileUrl = path + fileName;
/*  57 */     File dest = new File(getRealPath(path), fileName);
/*  58 */     dest = UploadUtils.getUniqueFile(dest);
/*  59 */     stores(file, dest);
/*  60 */     return fileUrl;
/*     */   }
/*     */ 
/*     */   public String storeByExt(String path, String ext, File file)
/*     */     throws IOException
/*     */   {
/*  68 */     String fileName = UploadUtils.generateRamdonFilename(ext);
/*  69 */     String fileUrl = path + fileName;
/*  70 */     File dest = new File(getRealPath(path), fileName);
/*  71 */     dest = UploadUtils.getUniqueFile(dest);
/*  72 */     store(file, dest);
/*  73 */     return fileUrl;
/*     */   }
/*     */ 
/*     */   private void stores(MultipartFile file, File dest) throws IOException {
/*     */     try {
/*  78 */       UploadUtils.checkDirAndCreate(dest.getParentFile());
/*  79 */       file.transferTo(dest);
/*     */     } catch (IOException e) {
/*  81 */       this.log.error("Transfer file error when upload file", e);
/*  82 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void store(File file, File dest) throws IOException
/*     */   {
/*     */     try {
/*  89 */       UploadUtils.checkDirAndCreate(dest.getParentFile());
/*  90 */       FileUtils.copyFile(file, dest);
/*     */     } catch (IOException e) {
/*  92 */       this.log.error("Transfer file error when upload file", e);
/*  93 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String storeByFilePath(String path, String filename, MultipartFile file) throws IOException
/*     */   {
/*  99 */     if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
/* 100 */       return "";
/*     */     }
/* 102 */     File dest = new File(getRealPath(path + filename));
/* 103 */     store1(file, dest);
/* 104 */     return path + filename;
/*     */   }
/*     */ 
/*     */   public void setServletContext(ServletContext servletContext)
/*     */   {
/* 109 */     this.ctx = servletContext;
/*     */   }
/*     */ 
/*     */   public boolean store(String s, InputStream inputstream)
/*     */     throws FileNotFoundException, IOException
/*     */   {
/* 115 */     IOUtils.copy(inputstream, new FileOutputStream(this.ctx.getRealPath(s)));
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   private void store1(MultipartFile file, File dest) throws IOException {
/*     */     try {
/* 121 */       UploadUtils.checkDirAndCreate(dest.getParentFile());
/* 122 */       file.transferTo(dest);
/*     */     } catch (IOException e) {
/* 124 */       this.log.error("Transfer file error when upload file", e);
/* 125 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean retrieve(String s, OutputStream outputstream)
/*     */   {
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   private String getRealPath(String name) {
/* 135 */     String realpath = this.ctx.getRealPath(name);
/* 136 */     if (realpath == null) {
/* 137 */       realpath = this.ctx.getRealPath("/") + name;
/*     */     }
/* 139 */     return realpath;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.upload.FileRepository
 * JD-Core Version:    0.6.0
 */