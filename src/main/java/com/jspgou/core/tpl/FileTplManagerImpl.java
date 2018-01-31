/*     */ package com.jspgou.core.tpl;
/*     */ 
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ public class FileTplManagerImpl
/*     */   implements TplManager
/*     */ {
/*  24 */   private static Logger log = LoggerFactory.getLogger(FileTplManagerImpl.class);
/*     */   private String root;
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   public int delete(String[] names)
/*     */   {
/*  29 */     int count = 0;
/*  30 */     for (String name : names) {
/*  31 */       File f = new File(this.realPathResolver.get(name));
/*  32 */       if (f.isDirectory()) {
/*  33 */         if (FileUtils.deleteQuietly(f)) {
/*  34 */           count++;
/*     */         }
/*     */       }
/*  37 */       else if (f.delete()) {
/*  38 */         count++;
/*     */       }
/*     */     }
/*     */ 
/*  42 */     return count;
/*     */   }
/*     */ 
/*     */   public Tpl get(String name)
/*     */   {
/*  47 */     File f = new File(this.realPathResolver.get(name));
/*  48 */     if (f.exists()) {
/*  49 */       return new FileTpl(f, this.root);
/*     */     }
/*  51 */     return null;
/*     */   }
/*     */ 
/*     */   public List<Tpl> getListByPrefix(String prefix)
/*     */   {
/*  57 */     File f = new File(this.realPathResolver.get(prefix));
/*  58 */     String name = prefix.substring(prefix.lastIndexOf("/") + 1);
/*     */     File parent;
/*     */     File parent;
/*  60 */     if (prefix.endsWith("/")) {
/*  61 */       name = "";
/*  62 */       parent = f;
/*     */     } else {
/*  64 */       parent = f.getParentFile();
/*     */     }
/*  66 */     if (parent.exists()) {
/*  67 */       File[] files = parent.listFiles(new PrefixFilter(name));
/*  68 */       if (files != null) {
/*  69 */         List list = new ArrayList();
/*  70 */         for (File file : files) {
/*  71 */           list.add(new FileTpl(file, this.root));
/*     */         }
/*  73 */         return list;
/*     */       }
/*  75 */       return new ArrayList(0);
/*     */     }
/*     */ 
/*  78 */     return new ArrayList(0);
/*     */   }
/*     */ 
/*     */   public List<String> getNameListByPrefix(String prefix)
/*     */   {
/*  84 */     List list = getListByPrefix(prefix);
/*  85 */     List result = new ArrayList(list.size());
/*  86 */     for (Tpl tpl : list) {
/*  87 */       result.add(tpl.getName());
/*     */     }
/*  89 */     return result;
/*     */   }
/*     */ 
/*     */   public List<Tpl> getChild(String path)
/*     */   {
/*  94 */     File file = new File(this.realPathResolver.get(path));
/*  95 */     File[] child = file.listFiles();
/*  96 */     if (child != null) {
/*  97 */       List list = new ArrayList(child.length);
/*  98 */       for (File f : child) {
/*  99 */         list.add(new FileTpl(f, this.root));
/*     */       }
/* 101 */       return list;
/*     */     }
/* 103 */     return new ArrayList(0);
/*     */   }
/*     */ 
/*     */   public void rename(String orig, String dist)
/*     */   {
/* 109 */     String os = this.realPathResolver.get(orig);
/* 110 */     File of = new File(os);
/* 111 */     String ds = this.realPathResolver.get(dist);
/* 112 */     File df = new File(ds);
/*     */     try {
/* 114 */       if (of.isDirectory())
/* 115 */         FileUtils.moveDirectory(of, df);
/*     */       else
/* 117 */         FileUtils.moveFile(of, df);
/*     */     }
/*     */     catch (IOException e) {
/* 120 */       log.error("Move template error: " + orig + " to " + dist, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void save(String name, String source, boolean isDirectory)
/*     */   {
/* 126 */     String real = this.realPathResolver.get(name);
/* 127 */     File f = new File(real);
/* 128 */     if (isDirectory)
/* 129 */       f.mkdirs();
/*     */     else
/*     */       try {
/* 132 */         FileUtils.writeStringToFile(f, source, "UTF-8");
/*     */       } catch (IOException e) {
/* 134 */         log.error("Save template error: " + name, e);
/* 135 */         throw new RuntimeException(e);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void save(String path, MultipartFile file)
/*     */   {
/* 142 */     File f = new File(this.realPathResolver.get(path), file
/* 143 */       .getOriginalFilename());
/*     */     try {
/* 145 */       file.transferTo(f);
/*     */     } catch (IllegalStateException e) {
/* 147 */       log.error("upload template error!", e);
/*     */     } catch (IOException e) {
/* 149 */       log.error("upload template error!", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update(String name, String source)
/*     */   {
/* 155 */     String real = this.realPathResolver.get(name);
/* 156 */     File f = new File(real);
/*     */     try {
/* 158 */       FileUtils.writeStringToFile(f, source, "UTF-8");
/*     */     } catch (IOException e) {
/* 160 */       log.error("Save template error: " + name, e);
/* 161 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setRealPathResolver(RealPathResolver realPathResolver)
/*     */   {
/* 171 */     this.realPathResolver = realPathResolver;
/* 172 */     this.root = realPathResolver.get("");
/*     */   }
/*     */ 
/*     */   private static class PrefixFilter
/*     */     implements FileFilter
/*     */   {
/*     */     private String prefix;
/*     */ 
/*     */     public PrefixFilter(String prefix)
/*     */     {
/* 183 */       this.prefix = prefix;
/*     */     }
/*     */ 
/*     */     public boolean accept(File file)
/*     */     {
/* 188 */       String name = file.getName();
/* 189 */       return (file.isFile()) && (name.startsWith(this.prefix));
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.tpl.FileTplManagerImpl
 * JD-Core Version:    0.6.0
 */