/*     */ package com.jspgou.cms.ueditor.upload;
/*     */ 
/*     */ import com.jspgou.cms.ueditor.define.BaseState;
/*     */ import com.jspgou.cms.ueditor.define.State;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ public class StorageManager
/*     */ {
/*     */   public static final int BUFFER_SIZE = 8192;
/*     */ 
/*     */   public static State saveBinaryFile(byte[] data, String path)
/*     */   {
/*  23 */     File file = new File(path);
/*     */ 
/*  25 */     State state = valid(file);
/*     */ 
/*  27 */     if (!state.isSuccess()) {
/*  28 */       return state;
/*     */     }
/*     */     try
/*     */     {
/*  32 */       BufferedOutputStream bos = new BufferedOutputStream(
/*  33 */         new FileOutputStream(file));
/*  34 */       bos.write(data);
/*  35 */       bos.flush();
/*  36 */       bos.close();
/*     */     } catch (IOException ioe) {
/*  38 */       return new BaseState(false, 4);
/*     */     }
/*     */ 
/*  41 */     state = new BaseState(true, file.getAbsolutePath());
/*  42 */     state.putInfo("size", data.length);
/*  43 */     state.putInfo("title", file.getName());
/*  44 */     return state;
/*     */   }
/*     */ 
/*     */   public static State saveFileByInputStream(InputStream is, String path) {
/*  48 */     State state = null;
/*     */ 
/*  50 */     File tmpFile = getTmpFile();
/*     */ 
/*  52 */     byte[] dataBuf = new byte[2048];
/*  53 */     BufferedInputStream bis = new BufferedInputStream(is, 8192);
/*     */     try
/*     */     {
/*  56 */       BufferedOutputStream bos = new BufferedOutputStream(
/*  57 */         new FileOutputStream(tmpFile), 8192);
/*     */ 
/*  59 */       int count = 0;
/*  60 */       while ((count = bis.read(dataBuf)) != -1) {
/*  61 */         bos.write(dataBuf, 0, count);
/*     */       }
/*  63 */       bos.flush();
/*  64 */       bos.close();
/*     */ 
/*  66 */       state = saveTmpFile(tmpFile, path);
/*     */ 
/*  68 */       if (!state.isSuccess()) {
/*  69 */         tmpFile.delete();
/*     */       }
/*     */ 
/*  72 */       return state;
/*     */     } catch (IOException localIOException) {
/*     */     }
/*  75 */     return new BaseState(false, 4);
/*     */   }
/*     */ 
/*     */   private static File getTmpFile() {
/*  79 */     File tmpDir = FileUtils.getTempDirectory();
/*  80 */     String tmpFileName = (Math.random() * 10000.0D).replace(".", "");
/*  81 */     return new File(tmpDir, tmpFileName);
/*     */   }
/*     */ 
/*     */   private static State saveTmpFile(File tmpFile, String path) {
/*  85 */     State state = null;
/*  86 */     File targetFile = new File(path);
/*     */ 
/*  88 */     if (targetFile.canWrite())
/*  89 */       return new BaseState(false, 2);
/*     */     try
/*     */     {
/*  92 */       FileUtils.moveFile(tmpFile, targetFile);
/*     */     } catch (IOException e) {
/*  94 */       return new BaseState(false, 4);
/*     */     }
/*     */ 
/*  97 */     state = new BaseState(true);
/*  98 */     state.putInfo("size", targetFile.length());
/*  99 */     state.putInfo("title", targetFile.getName());
/*     */ 
/* 101 */     return state;
/*     */   }
/*     */ 
/*     */   private static State valid(File file) {
/* 105 */     File parentPath = file.getParentFile();
/*     */ 
/* 107 */     if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
/* 108 */       return new BaseState(false, 3);
/*     */     }
/*     */ 
/* 111 */     if (!parentPath.canWrite()) {
/* 112 */       return new BaseState(false, 2);
/*     */     }
/*     */ 
/* 115 */     return new BaseState(true);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.upload.StorageManager
 * JD-Core Version:    0.6.0
 */