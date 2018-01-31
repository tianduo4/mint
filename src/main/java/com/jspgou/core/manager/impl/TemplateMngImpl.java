/*    */ package com.jspgou.core.manager.impl;
/*    */ 
/*    */ import com.jspgou.common.file.FileWrap;
/*    */ import com.jspgou.core.manager.TemplateMng;
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ import org.springframework.web.multipart.MultipartFile;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class TemplateMngImpl
/*    */   implements TemplateMng
/*    */ {
/* 19 */   private static final Logger log = LoggerFactory.getLogger(TemplateMngImpl.class);
/*    */ 
/* 25 */   private FileFilter resFilter = new FileFilter()
/*    */   {
/*    */     public boolean accept(File paramFile) {
/* 28 */       if (paramFile.isDirectory()) return true;
/* 29 */       String str = FilenameUtils.getExtension(paramFile.getName());
/* 30 */       return FileWrap.allowEdit(str);
/*    */     }
/* 25 */   };
/*    */ 
/*    */   public FileWrap getTplFileWrap(String s, String rootPath)
/*    */   {
/* 36 */     FileWrap filewrap = new FileWrap(new File(s), s);
/* 37 */     filewrap.setFilename(rootPath);
/* 38 */     return filewrap;
/*    */   }
/*    */ 
/*    */   public FileWrap getResFileWrap(String s, String rootPath)
/*    */   {
/* 43 */     FileWrap filewrap = new FileWrap(new File(s), s, this.resFilter);
/* 44 */     filewrap.setFilename(rootPath);
/* 45 */     return filewrap;
/*    */   }
/*    */ 
/*    */   public int uploadResourceFile(String s, MultipartFile[] files)
/*    */   {
/* 50 */     if ((files == null) || (files.length == 0)) return 0;
/* 51 */     File localFile = new File(s);
/* 52 */     int i = 0;
/*    */     try {
/* 54 */       for (MultipartFile file : files) {
/* 55 */         String str = file.getOriginalFilename();
/* 56 */         if ((file.isEmpty()) || (!allowUpload(FilenameUtils.getExtension(str))))
/*    */           continue;
/* 58 */         file.transferTo(new File(localFile, str));
/* 59 */         i++;
/*    */       }
/*    */     } catch (IOException e) {
/* 62 */       log.error("upload resource failed", e);
/*    */     }
/* 64 */     return i;
/*    */   }
/*    */ 
/*    */   public boolean allowUpload(String s) {
/* 68 */     return true;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.TemplateMngImpl
 * JD-Core Version:    0.6.0
 */