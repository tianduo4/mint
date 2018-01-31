/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopPlug;
/*     */ import com.jspgou.cms.manager.ResourceMng;
/*     */ import com.jspgou.cms.manager.ShopPlugMng;
/*     */ import com.jspgou.common.file.FileWrap;
/*     */ import com.jspgou.common.file.FileWrap.FileComparator;
/*     */ import com.jspgou.common.util.Zipper;
import com.jspgou.common.util.Zipper.FileEntry;
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
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class ResourceMngImpl
/*     */   implements ResourceMng
/*     */ {
/*     */   private static final String PLUG_PERMS = "plug.perms";
/*  53 */   private static final Logger log = LoggerFactory.getLogger(ResourceMngImpl.class);
/*     */ 
/* 233 */   private FileFilter filter = new FileFilter()
/*     */   {
/*     */     public boolean accept(File file) {
/* 236 */       return (file.isDirectory()) || (FileWrap.editableExt(FilenameUtils.getExtension(file.getName())));
/*     */     }
/* 233 */   };
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   @Autowired
/*     */   private ShopPlugMng plugMng;
/*     */ 
/*     */   public List<FileWrap> listFile(String path, boolean dirAndEditable)
/*     */   {
/*  57 */     File parent = new File(this.realPathResolver.get(path));
/*  58 */     if (parent.exists())
/*     */     {
/*     */       File[] files;
/*     */       File[] files;
/*  60 */       if (dirAndEditable)
/*  61 */         files = parent.listFiles(this.filter);
/*     */       else {
/*  63 */         files = parent.listFiles();
/*     */       }
/*  65 */       Arrays.sort(files, new FileWrap.FileComparator());
/*  66 */       List list = new ArrayList(files.length);
/*  67 */       for (File f : files) {
/*  68 */         list.add(new FileWrap(f, this.realPathResolver.get("")));
/*     */       }
/*  70 */       return list;
/*     */     }
/*  72 */     return new ArrayList(0);
/*     */   }
/*     */ 
/*     */   public boolean createDir(String path, String dirName)
/*     */   {
/*  78 */     File parent = new File(this.realPathResolver.get(path));
/*  79 */     parent.mkdirs();
/*  80 */     File dir = new File(parent, dirName);
/*  81 */     return dir.mkdir();
/*     */   }
/*     */ 
/*     */   public void saveFile(String path, MultipartFile file)
/*     */     throws IllegalStateException, IOException
/*     */   {
/*  87 */     File dest = new File(this.realPathResolver.get(path), file
/*  88 */       .getOriginalFilename());
/*  89 */     file.transferTo(dest);
/*     */   }
/*     */ 
/*     */   public void createFile(String path, String filename, String data)
/*     */     throws IOException
/*     */   {
/*  95 */     File parent = new File(this.realPathResolver.get(path));
/*  96 */     parent.mkdirs();
/*  97 */     File file = new File(parent, filename);
/*  98 */     FileUtils.writeStringToFile(file, data, "UTF-8");
/*     */   }
/*     */ 
/*     */   public String readFile(String name) throws IOException
/*     */   {
/* 103 */     File file = new File(this.realPathResolver.get(name));
/* 104 */     return FileUtils.readFileToString(file, "UTF-8");
/*     */   }
/*     */ 
/*     */   public void updateFile(String name, String data) throws IOException
/*     */   {
/* 109 */     File file = new File(this.realPathResolver.get(name));
/* 110 */     FileUtils.writeStringToFile(file, data, "UTF-8");
/*     */   }
/*     */ 
/*     */   public int delete(String[] names)
/*     */   {
/* 115 */     int count = 0;
/*     */ 
/* 117 */     for (String name : names) {
/* 118 */       File f = new File(this.realPathResolver.get(name));
/* 119 */       if (FileUtils.deleteQuietly(f)) {
/* 120 */         count++;
/*     */       }
/*     */     }
/* 123 */     return count;
/*     */   }
/*     */ 
/*     */   public void rename(String origName, String destName)
/*     */   {
/* 128 */     File orig = new File(this.realPathResolver.get(origName));
/* 129 */     File dest = new File(this.realPathResolver.get(destName));
/* 130 */     orig.renameTo(dest);
/*     */   }
/*     */ 
/*     */   public String[] getSolutions(String path)
/*     */   {
/* 139 */     File tpl = new File(this.realPathResolver.get(path));
/* 140 */     return tpl.list(new FilenameFilter()
/*     */     {
/*     */       public boolean accept(File dir, String name) {
/* 143 */         return dir.isDirectory();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public List<Zipper.FileEntry> export(Website site, String solution) {
/* 150 */     List fileEntrys = new ArrayList();
/* 151 */     File tpl = new File(this.realPathResolver.get(site.getTplPath()), solution);
/* 152 */     fileEntrys.add(new Zipper.FileEntry("", "", tpl));
/* 153 */     File res = new File(this.realPathResolver.get("/r/gou/www"));
/* 154 */     if (res.exists()) {
/* 155 */       for (File r : res.listFiles()) {
/* 156 */         fileEntrys.add(new Zipper.FileEntry("${res}", r));
/*     */       }
/*     */     }
/* 159 */     return fileEntrys;
/*     */   }
/*     */ 
/*     */   public void imoport(File file, Website site)
/*     */     throws IOException
/*     */   {
/* 165 */     String resRoot = "/r/gou/www";
/* 166 */     String tplRoot = "/WEB-INF/t/gou";
/*     */ 
/* 168 */     ZipFile zip = new ZipFile(file, "GBK");
/*     */ 
/* 174 */     byte[] buf = new byte[1024];
/*     */ 
/* 176 */     InputStream is = null;
/* 177 */     OutputStream os = null;
/* 178 */     String solution = null;
/*     */ 
/* 180 */     Enumeration en = zip.getEntries();
/* 181 */     while (en.hasMoreElements()) {
/* 182 */       String name = ((ZipEntry)en.nextElement()).getName();
/* 183 */       if (!name.startsWith("${res}")) {
/* 184 */         solution = name.substring(0, name.indexOf("/"));
/* 185 */         break;
/*     */       }
/*     */     }
/* 188 */     if (solution == null) {
/* 189 */       return;
/*     */     }
/* 191 */     en = zip.getEntries();
/* 192 */     while (en.hasMoreElements()) {
/* 193 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 194 */       if (!entry.isDirectory()) {
/* 195 */         String name = entry.getName();
/* 196 */         log.debug("unzip file：{}", name);
/*     */         String filename;
/*     */         String filename;
/* 198 */         if (name.startsWith("${res}"))
/* 199 */           filename = resRoot + name.substring("${res}".length());
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
/*     */           int len;
/* 212 */           while ((len = is.read(buf)) != -1)
/*     */           {
/*     */             int len;
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
/*     */   @Autowired
/*     */   public void setRealPathResolver(RealPathResolver realPathResolver)
/*     */   {
/* 245 */     this.realPathResolver = realPathResolver;
/*     */   }
/*     */ 
/*     */   private void createFolder(File f) {
/* 249 */     File parent = f.getParentFile();
/* 250 */     if (!parent.exists()) {
/* 251 */       parent.mkdirs();
/* 252 */       createFolder(parent);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unZipFile(File file)
/*     */     throws IOException
/*     */   {
/* 260 */     ZipFile zip = new ZipFile(file, "GBK");
/*     */ 
/* 266 */     byte[] buf = new byte[1024];
/*     */ 
/* 268 */     InputStream is = null;
/* 269 */     OutputStream os = null;
/* 270 */     Enumeration en = zip.getEntries();
/* 271 */     while (en.hasMoreElements()) {
/* 272 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 273 */       String name = entry.getName();
/* 274 */       if (!entry.isDirectory()) {
/* 275 */         name = entry.getName();
/* 276 */         String filename = name;
/* 277 */         File outFile = new File(this.realPathResolver.get(filename));
/* 278 */         if (outFile.exists()) {
/*     */           break;
/*     */         }
/* 281 */         File pfile = outFile.getParentFile();
/* 282 */         if (!pfile.exists())
/*     */         {
/* 284 */           createFolder(outFile);
/*     */         }int len;
/*     */         try { is = zip.getInputStream(entry);
/* 288 */           os = new FileOutputStream(outFile);
/*     */           int len;
/* 289 */           while ((len = is.read(buf)) != -1)
/*     */           {
/*     */             int len;
/* 290 */             os.write(buf, 0, len);
/*     */           }
/*     */         } finally {
/* 293 */           if (is != null) {
/* 294 */             is.close();
/* 295 */             is = null;
/*     */           }
/* 297 */           if (os != null) {
/* 298 */             os.close();
/* 299 */             os = null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 304 */     zip.close();
/*     */   }
/*     */ 
/*     */   private boolean directoryHasFile(File directory)
/*     */   {
/* 309 */     File[] files = directory.listFiles();
/* 310 */     if ((files != null) && (files.length > 0)) {
/* 311 */       for (File f : files) {
/* 312 */         if (directoryHasFile(f)) {
/* 313 */           return true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 318 */       return false;
/*     */     }
/* 320 */     return false;
/*     */   }
/*     */ 
/*     */   public void deleteZipFile(File file)
/*     */     throws IOException
/*     */   {
/* 328 */     ZipFile zip = new ZipFile(file, "GBK");
/*     */ 
/* 334 */     Enumeration en = zip.getEntries();
/* 335 */     while (en.hasMoreElements()) {
/* 336 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 337 */       if (!entry.isDirectory()) {
/* 338 */         String name = entry.getName();
/* 339 */         String filename = name;
/* 340 */         File directory = new File(this.realPathResolver.get(filename));
/* 341 */         if (directory.exists()) {
/* 342 */           directory.delete();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 347 */     en = zip.getEntries();
/* 348 */     while (en.hasMoreElements()) {
/* 349 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 350 */       if (entry.isDirectory()) {
/* 351 */         String name = entry.getName();
/* 352 */         String filename = name;
/* 353 */         File directory = new File(this.realPathResolver.get(filename));
/* 354 */         if (!directoryHasFile(directory)) {
/* 355 */           directory.delete();
/*     */         }
/*     */       }
/*     */     }
/* 359 */     zip.close();
/*     */   }
/*     */ 
/*     */   public String readFileFromZip(File file, String readFileName)
/*     */     throws IOException
/*     */   {
/* 367 */     ZipFile zip = new ZipFile(file, "GBK");
/*     */ 
/* 371 */     InputStream is = null;
/* 372 */     InputStreamReader reader = null;
/* 373 */     BufferedReader in = null;
/* 374 */     Enumeration en = zip.getEntries();
/* 375 */     StringBuffer buff = new StringBuffer();
/*     */ 
/* 377 */     while (en.hasMoreElements()) {
/* 378 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 379 */       String name = entry.getName();
/* 380 */       if (!entry.isDirectory()) {
/* 381 */         name = entry.getName();
/* 382 */         String filename = name;
/* 383 */         if (!filename.endsWith(readFileName)) continue; String line;
/*     */         try { is = zip.getInputStream(entry);
/* 386 */           reader = new InputStreamReader(is);
/* 387 */           in = new BufferedReader(reader);
/* 388 */           String line = in.readLine();
/* 389 */           while (StringUtils.isNotBlank(line)) {
/* 390 */             buff.append(line);
/* 391 */             line = in.readLine();
/*     */           }
/*     */         } finally {
/* 394 */           if (is != null) {
/* 395 */             is.close();
/* 396 */             is = null;
/*     */           }
/* 398 */           if (reader != null) {
/* 399 */             reader.close();
/* 400 */             reader = null;
/*     */           }
/* 402 */           if (in != null) {
/* 403 */             in.close();
/* 404 */             in = null;
/*     */           }
/*     */         }
/* 407 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 411 */     zip.close();
/* 412 */     return buff.toString();
/*     */   }
/*     */ 
/*     */   public void installPlug(File zipFile, ShopPlug plug)
/*     */     throws IOException
/*     */   {
/* 419 */     ZipFile zip = new ZipFile(zipFile, "GBK");
/*     */ 
/* 425 */     byte[] buf = new byte[1024];
/*     */ 
/* 427 */     InputStream is = null;
/* 428 */     OutputStream os = null;
/* 429 */     Enumeration en = zip.getEntries();
/* 430 */     StringBuffer buff = new StringBuffer();
/* 431 */     while (en.hasMoreElements()) {
/* 432 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 433 */       String name = entry.getName();
/* 434 */       if (!entry.isDirectory()) {
/* 435 */         name = entry.getName();
/* 436 */         String filename = name;
/* 437 */         File outFile = new File(this.realPathResolver.get(filename));
/* 438 */         if (outFile.exists()) {
/*     */           break;
/*     */         }
/* 441 */         File pfile = outFile.getParentFile();
/* 442 */         if (!pfile.exists())
/*     */         {
/* 444 */           createFolder(outFile);
/*     */         }int len;
/*     */         try { is = zip.getInputStream(entry);
/* 448 */           os = new FileOutputStream(outFile);
/*     */           int len;
/* 449 */           while ((len = is.read(buf)) != -1)
/*     */           {
/*     */             int len;
/* 450 */             os.write(buf, 0, len);
/*     */           }
/*     */         } finally {
/* 453 */           if (is != null) {
/* 454 */             is.close();
/* 455 */             is = null;
/*     */           }
/* 457 */           if (os != null) {
/* 458 */             os.close();
/* 459 */             os = null;
/*     */           }
/*     */         }
/*     */ 
/* 463 */         if ((filename.toLowerCase().endsWith(".properties")) && (!filename.toLowerCase().contains("messages"))) {
/* 464 */           Properties p = new Properties();
/* 465 */           p.load(new FileInputStream(outFile));
/* 466 */           Set pKeys = p.keySet();
/* 467 */           for (Iterator localIterator = pKeys.iterator(); localIterator.hasNext(); ) { Object pk = localIterator.next();
/* 468 */             if (pk.toString().startsWith("plug.perms")) {
/* 469 */               buff.append(p.getProperty((String)pk) + ";");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 475 */     zip.close();
/* 476 */     plug.setPlugPerms(buff.toString());
/* 477 */     this.plugMng.update(plug);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ResourceMngImpl
 * JD-Core Version:    0.6.0
 */