/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.manager.CmsResourceMng;
/*     */ import com.jspgou.common.file.FileWrap;
/*     */ import com.jspgou.common.util.Zipper;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.tools.zip.ZipEntry;
/*     */ import org.apache.tools.zip.ZipFile;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Service
/*     */ public class CmsResourceMngImpl
/*     */   implements CmsResourceMng
/*     */ {
/*     */   private static final String PLUG_PERMS = "plug.perms";
/*     */   private static final String PLUG_FILE_PREFIX = "WEB-INF/config";
/*  46 */   private static final Logger log = LoggerFactory.getLogger(CmsResourceMngImpl.class);
/*     */ 
/* 433 */   private FileFilter filter = new FileFilter() {
/*     */     public boolean accept(File file) {
/* 435 */       if (!file.isDirectory())
/*     */       {
/* 437 */         if (!FileWrap.editableExt(FilenameUtils.getExtension(file
/* 437 */           .getName())))
/* 435 */           return false; 
/* 435 */       }return true;
/*     */     }
/* 433 */   };
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   public List<FileWrap> listFile(String path, boolean dirAndEditable)
/*     */   {
/*  49 */     File parent = new File(this.realPathResolver.get(path));
/*  50 */     if (parent.exists())
/*     */     {
/*     */       File[] files;
/*  52 */       if (dirAndEditable)
/*  53 */         files = parent.listFiles(this.filter);
/*     */       else {
/*  55 */         files = parent.listFiles();
/*     */       }
/*  57 */       Arrays.sort(files, new FileWrap.FileComparator());
/*  58 */       List list = new ArrayList(files.length);
/*  59 */       for (File f : files) {
/*  60 */         list.add(new FileWrap(f, this.realPathResolver.get("")));
/*     */       }
/*  62 */       return list;
/*     */     }
/*  64 */     return new ArrayList(0);
/*     */   }
/*     */ 
/*     */   public boolean createDir(String path, String dirName)
/*     */   {
/*  69 */     File parent = new File(this.realPathResolver.get(path));
/*  70 */     parent.mkdirs();
/*  71 */     File dir = new File(parent, dirName);
/*  72 */     return dir.mkdir();
/*     */   }
/*     */ 
/*     */   public void saveFile(String path, MultipartFile file) throws IllegalStateException, IOException
/*     */   {
/*  77 */     File dest = new File(this.realPathResolver.get(path), file
/*  78 */       .getOriginalFilename());
/*  79 */     file.transferTo(dest);
/*     */   }
/*     */ 
/*     */   public void createFile(String path, String filename, String data) throws IOException
/*     */   {
/*  84 */     File parent = new File(this.realPathResolver.get(path));
/*  85 */     parent.mkdirs();
/*  86 */     File file = new File(parent, filename);
/*  87 */     FileUtils.writeStringToFile(file, data, "UTF-8");
/*     */   }
/*     */ 
/*     */   public String readFile(String name) throws IOException {
/*  91 */     File file = new File(this.realPathResolver.get(name));
/*  92 */     return FileUtils.readFileToString(file, "UTF-8");
/*     */   }
/*     */ 
/*     */   public void updateFile(String name, String data) throws IOException {
/*  96 */     File file = new File(this.realPathResolver.get(name));
/*  97 */     FileUtils.writeStringToFile(file, data, "UTF-8");
/*     */   }
/*     */ 
/*     */   public int delete(String[] names) {
/* 101 */     int count = 0;
/*     */ 
/* 103 */     for (String name : names) {
/* 104 */       File f = new File(this.realPathResolver.get(name));
/* 105 */       if (FileUtils.deleteQuietly(f)) {
/* 106 */         count++;
/*     */       }
/*     */     }
/* 109 */     return count;
/*     */   }
/*     */ 
/*     */   public void rename(String origName, String destName) {
/* 113 */     File orig = new File(this.realPathResolver.get(origName));
/* 114 */     File dest = new File(this.realPathResolver.get(destName));
/* 115 */     orig.renameTo(dest);
/*     */   }
/*     */ 
/*     */   public void copyTplAndRes(Website from, Website to) throws IOException {
/* 119 */     String fromSol = from.getTplSolution();
/* 120 */     String toSol = to.getTplSolution();
/* 121 */     File tplFrom = new File(this.realPathResolver.get(from.getTplPath()), 
/* 122 */       fromSol);
/* 123 */     File tplTo = new File(this.realPathResolver.get(to.getTplPath()), toSol);
/* 124 */     FileUtils.copyDirectory(tplFrom, tplTo);
/* 125 */     File resFrom = new File(this.realPathResolver.get(from.getResPath()), 
/* 126 */       fromSol);
/* 127 */     if (resFrom.exists()) {
/* 128 */       File resTo = new File(this.realPathResolver.get(to.getResPath()), toSol);
/* 129 */       FileUtils.copyDirectory(resFrom, resTo);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delTplAndRes(Website site) throws IOException {
/* 134 */     File tpl = new File(this.realPathResolver.get(site.getTplPath()));
/* 135 */     File res = new File(this.realPathResolver.get(site.getResPath()));
/* 136 */     FileUtils.deleteDirectory(tpl);
/* 137 */     FileUtils.deleteDirectory(res);
/*     */   }
/*     */ 
/*     */   public String[] getSolutions(String path) {
/* 141 */     File tpl = new File(this.realPathResolver.get(path));
/* 142 */     return tpl.list(new FilenameFilter() {
/*     */       public boolean accept(File dir, String name) {
/* 144 */         return dir.isDirectory();
/*     */       } } );
/*     */   }
/*     */ 
/*     */   public List<Zipper.FileEntry> export(Website site, String solution) {
/* 150 */     List fileEntrys = new ArrayList();
/* 151 */     File tpl = new File(this.realPathResolver.get(site.getTplPath()), solution);
/* 152 */     fileEntrys.add(new Zipper.FileEntry("", "", tpl));
/* 153 */     File res = new File(this.realPathResolver.get(site.getResPath()), solution);
/* 154 */     if (res.exists()) {
/* 155 */       for (File r : res.listFiles()) {
/* 156 */         fileEntrys.add(new Zipper.FileEntry("${res}", r));
/*     */       }
/*     */     }
/* 159 */     return fileEntrys;
/*     */   }
/*     */ 
/*     */   public void imoport(File file, Website site) throws IOException
/*     */   {
/* 164 */     String resRoot = site.getResPath();
/* 165 */     String tplRoot = site.getTplPath();
/*     */ 
/* 167 */     ZipFile zip = new ZipFile(file, "GBK");
/*     */ 
/* 173 */     byte[] buf = new byte[1024];
/*     */ 
/* 175 */     InputStream is = null;
/* 176 */     OutputStream os = null;
/* 177 */     String solution = null;
/*     */ 
/* 179 */     Enumeration en = zip.getEntries();
/* 180 */     while (en.hasMoreElements()) {
/* 181 */       String name = ((ZipEntry)en.nextElement()).getName();
/* 182 */       if (!name.startsWith("${res}")) {
/* 183 */         solution = name.substring(0, name.indexOf("/"));
/* 184 */         break;
/*     */       }
/*     */     }
/* 187 */     if (solution == null) {
/* 188 */       return;
/*     */     }
/* 190 */     en = zip.getEntries();
/* 191 */     while (en.hasMoreElements()) {
/* 192 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 193 */       if (!entry.isDirectory()) {
/* 194 */         String name = entry.getName();
/* 195 */         log.debug("unzip file：{}", name);
/*     */         String filename;
/* 197 */         if (name.startsWith("${res}"))
/* 198 */           filename = resRoot + "/" + solution + 
/* 199 */             name.substring("${res}".length());
/*     */         else {
/* 201 */           filename = tplRoot + "/" + name;
/*     */         }
/* 203 */         log.debug("解压地址：{}", filename);
/* 204 */         File outFile = new File(this.realPathResolver.get(filename));
/* 205 */         File pfile = outFile.getParentFile();
/* 206 */         if (!pfile.exists())
/* 207 */           pfile.mkdirs(); int len;
/*     */         try {
/* 210 */           is = zip.getInputStream(entry);
/* 211 */           os = new FileOutputStream(outFile);
/* 212 */           while ((len = is.read(buf)) != -1)
/*     */           {
/* 213 */             os.write(buf, 0, len);
/*     */           }
/*     */         } finally {
/* 216 */           if (is != null) {
/* 217 */             is.close();
/* 218 */             is = null;
/*     */           }
/* 220 */           if (os != null) {
/* 221 */             os.close();
/* 222 */             os = null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getPlugPerms(File file)
/*     */     throws IOException
/*     */   {
/* 294 */     ZipFile zip = new ZipFile(file, "GBK");
/*     */ 
/* 298 */     String plugPerms = "";
/* 299 */     Enumeration en = zip.getEntries();
/* 300 */     while (en.hasMoreElements()) {
/* 301 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 302 */       String name = entry.getName();
/* 303 */       if (!entry.isDirectory()) {
/* 304 */         name = entry.getName();
/* 305 */         String filename = name;
/*     */ 
/* 307 */         if ((filename.startsWith("WEB-INF/config")) && (filename.endsWith(".properties"))) {
/* 308 */           File propertyFile = new File(this.realPathResolver.get(filename));
/* 309 */           Properties p = new Properties();
/* 310 */           p.load(new FileInputStream(propertyFile));
/* 311 */           plugPerms = p.getProperty("plug.perms");
/*     */         }
/*     */       }
/*     */     }
/* 315 */     zip.close();
/* 316 */     return plugPerms;
/*     */   }
/*     */ 
/*     */   public void deleteZipFile(File file)
/*     */     throws IOException
/*     */   {
/* 322 */     ZipFile zip = new ZipFile(file, "GBK");
/*     */ 
/* 328 */     Enumeration en = zip.getEntries();
/* 329 */     while (en.hasMoreElements()) {
/* 330 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 331 */       if (!entry.isDirectory()) {
/* 332 */         String name = entry.getName();
/* 333 */         String filename = name;
/* 334 */         File directory = new File(this.realPathResolver.get(filename));
/* 335 */         if (directory.exists()) {
/* 336 */           directory.delete();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 341 */     en = zip.getEntries();
/* 342 */     while (en.hasMoreElements()) {
/* 343 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 344 */       if (entry.isDirectory()) {
/* 345 */         String name = entry.getName();
/* 346 */         String filename = name;
/* 347 */         File directory = new File(this.realPathResolver.get(filename));
/* 348 */         if (!directoryHasFile(directory)) {
/* 349 */           directory.delete();
/*     */         }
/*     */       }
/*     */     }
/* 353 */     zip.close();
/*     */   }
/*     */ 
/*     */   public String readFileFromZip(File file, String readFileName)
/*     */     throws IOException
/*     */   {
/* 359 */     ZipFile zip = new ZipFile(file, "GBK");
/*     */ 
/* 363 */     InputStream is = null;
/* 364 */     InputStreamReader reader = null;
/* 365 */     BufferedReader in = null;
/* 366 */     Enumeration en = zip.getEntries();
/* 367 */     StringBuffer buff = new StringBuffer();
/*     */ 
/* 369 */     while (en.hasMoreElements()) {
/* 370 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 371 */       String name = entry.getName();
/* 372 */       if (!entry.isDirectory()) {
/* 373 */         name = entry.getName();
/* 374 */         String filename = name;
/* 375 */         if (!filename.endsWith(readFileName)) continue; String line;
/*     */         try { is = zip.getInputStream(entry);
/* 378 */           reader = new InputStreamReader(is);
/* 379 */           in = new BufferedReader(reader);
/* 380 */           String line = in.readLine();
/* 381 */           while (StringUtils.isNotBlank(line)) {
/* 382 */             buff.append(line);
/* 383 */             line = in.readLine();
/*     */           }
/*     */         } finally {
/* 386 */           if (is != null) {
/* 387 */             is.close();
/* 388 */             is = null;
/*     */           }
/* 390 */           if (reader != null) {
/* 391 */             reader.close();
/* 392 */             reader = null;
/*     */           }
/* 394 */           if (in != null) {
/* 395 */             in.close();
/* 396 */             in = null;
/*     */           }
/*     */         }
/* 399 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 403 */     zip.close();
/* 404 */     return buff.toString();
/*     */   }
/*     */ 
/*     */   private void createFolder(File f) {
/* 408 */     File parent = f.getParentFile();
/* 409 */     if (!parent.exists()) {
/* 410 */       parent.mkdirs();
/* 411 */       createFolder(parent);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean directoryHasFile(File directory)
/*     */   {
/* 417 */     File[] files = directory.listFiles();
/* 418 */     if ((files != null) && (files.length > 0)) {
/* 419 */       for (File f : files) {
/* 420 */         if (directoryHasFile(f)) {
/* 421 */           return true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 426 */       return false;
/*     */     }
/* 428 */     return false;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setRealPathResolver(RealPathResolver realPathResolver)
/*     */   {
/* 447 */     this.realPathResolver = realPathResolver;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.CmsResourceMngImpl
 * JD-Core Version:    0.6.0
 */