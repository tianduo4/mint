/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.tools.zip.ZipEntry;
/*     */ import org.apache.tools.zip.ZipOutputStream;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class Zipper
/*     */ {
/*  23 */   private static final Logger log = LoggerFactory.getLogger(Zipper.class);
/*     */ 
/* 130 */   private byte[] buf = new byte[1024];
/*     */   private ZipOutputStream zipOut;
/*     */ 
/*     */   public static void zip(OutputStream out, List<FileEntry> fileEntrys, String encoding)
/*     */   {
/*  31 */     new Zipper(out, fileEntrys, encoding);
/*     */   }
/*     */ 
/*     */   public static void zip(OutputStream out, List<FileEntry> fileEntrys)
/*     */   {
/*  39 */     new Zipper(out, fileEntrys, null);
/*     */   }
/*     */ 
/*     */   protected Zipper(OutputStream out, List<FileEntry> fileEntrys, String encoding)
/*     */   {
/*  54 */     Assert.notEmpty(fileEntrys);
/*  55 */     long begin = System.currentTimeMillis();
/*  56 */     log.debug("开始制作压缩包");
/*     */     try {
/*     */       try {
/*  59 */         this.zipOut = new ZipOutputStream(out);
/*  60 */         if (!StringUtils.isBlank(encoding)) {
/*  61 */           log.debug("using encoding: {}", encoding);
/*  62 */           this.zipOut.setEncoding(encoding);
/*     */         } else {
/*  64 */           log.debug("using default encoding");
/*     */         }
/*  66 */         for (FileEntry fe : fileEntrys)
/*  67 */           zip(fe.getFile(), fe.getFilter(), fe.getZipEntry(), fe
/*  68 */             .getPrefix());
/*     */       }
/*     */       finally {
/*  71 */         this.zipOut.close();
/*     */       }
/*     */     } catch (IOException e) {
/*  74 */       throw new RuntimeException("制作压缩包时，出现IO异常！", e);
/*     */     }
/*  76 */     long end = System.currentTimeMillis();
/*  77 */     log.info("制作压缩包成功。耗时：{}ms。", Long.valueOf(end - begin));
/*     */   }
/*     */ 
/*     */   private void zip(File srcFile, FilenameFilter filter, ZipEntry pentry, String prefix)
/*     */     throws IOException
/*     */   {
/*  92 */     if (srcFile.isDirectory())
/*     */     {
/*     */       ZipEntry entry;
/*  93 */       if (pentry == null)
/*  94 */         entry = new ZipEntry(srcFile.getName());
/*     */       else {
/*  96 */         entry = new ZipEntry(pentry.getName() + "/" + srcFile.getName());
/*     */       }
/*  98 */       File[] files = srcFile.listFiles(filter);
/*  99 */       for (File f : files)
/* 100 */         zip(f, filter, entry, prefix);
/*     */     }
/*     */     else
/*     */     {
/*     */       ZipEntry entry;
/* 103 */       if (pentry == null)
/* 104 */         entry = new ZipEntry(prefix + srcFile.getName());
/*     */       else {
/* 106 */         entry = new ZipEntry(pentry.getName() + "/" + prefix + 
/* 107 */           srcFile.getName());
/*     */       }
/*     */       try
/*     */       {
/* 111 */         log.debug("读取文件：{}", srcFile.getAbsolutePath());
/* 112 */         FileInputStream in = new FileInputStream(srcFile);
/*     */         try {
/* 114 */           this.zipOut.putNextEntry(entry);
/*     */           int len;
/* 116 */           while ((len = in.read(this.buf)) > 0)
/*     */           {
/* 117 */             this.zipOut.write(this.buf, 0, len);
/*     */           }
/* 119 */           this.zipOut.closeEntry();
/*     */         } finally {
/* 121 */           in.close();
/*     */         }
/*     */       } catch (FileNotFoundException e) {
/* 124 */         throw new RuntimeException("制作压缩包时，源文件不存在：" + 
/* 125 */           srcFile.getAbsolutePath(), e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class FileEntry {
/*     */     private FilenameFilter filter;
/*     */     private String parent;
/*     */     private File file;
/*     */     private String prefix;
/*     */ 
/*     */     public FileEntry(String parent, String prefix, File file, FilenameFilter filter) {
/* 141 */       this.parent = parent;
/* 142 */       this.prefix = prefix;
/* 143 */       this.file = file;
/* 144 */       this.filter = filter;
/*     */     }
/*     */ 
/*     */     public FileEntry(String parent, File file) {
/* 148 */       this.parent = parent;
/* 149 */       this.file = file;
/*     */     }
/*     */ 
/*     */     public FileEntry(String parent, String prefix, File file) {
/* 153 */       this(parent, prefix, file, null);
/*     */     }
/*     */ 
/*     */     public ZipEntry getZipEntry() {
/* 157 */       if (StringUtils.isBlank(this.parent)) {
/* 158 */         return null;
/*     */       }
/* 160 */       return new ZipEntry(this.parent);
/*     */     }
/*     */ 
/*     */     public FilenameFilter getFilter()
/*     */     {
/* 165 */       return this.filter;
/*     */     }
/*     */ 
/*     */     public void setFilter(FilenameFilter filter) {
/* 169 */       this.filter = filter;
/*     */     }
/*     */ 
/*     */     public String getParent() {
/* 173 */       return this.parent;
/*     */     }
/*     */ 
/*     */     public void setParent(String parent) {
/* 177 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */     public File getFile() {
/* 181 */       return this.file;
/*     */     }
/*     */ 
/*     */     public void setFile(File file) {
/* 185 */       this.file = file;
/*     */     }
/*     */ 
/*     */     public String getPrefix() {
/* 189 */       if (this.prefix == null) {
/* 190 */         return "";
/*     */       }
/* 192 */       return this.prefix;
/*     */     }
/*     */ 
/*     */     public void setPrefix(String prefix)
/*     */     {
/* 197 */       this.prefix = prefix;
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.Zipper
 * JD-Core Version:    0.6.0
 */