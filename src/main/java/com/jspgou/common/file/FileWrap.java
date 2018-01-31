/*     */ package com.jspgou.common.file;
/*     */ 
/*     */ import com.jspgou.common.image.ImageUtils;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class FileWrap
/*     */ {
/*  32 */   public static final String[] EDITABLE_EXT = { "html", "htm", 
/*  33 */     "css", "js", "txt" };
/*     */   private File file;
/*     */   private String rootPath;
/*     */   private FileFilter filter;
/*     */   private List<FileWrap> child;
/*     */   private String filename;
/*     */ 
/*     */   public JSONObject convertToJson()
/*     */     throws JSONException
/*     */   {
/*  42 */     JSONObject json = new JSONObject();
/*  43 */     json.put("name", getName());
/*  44 */     json.put("path", getPath());
/*  45 */     json.put("filename", getFilename());
/*  46 */     json.put("edit", isEditable());
/*  47 */     json.put("ext", getExtension());
/*  48 */     json.put("lastModifiedDate", DateUtils.parseDateToTimeStr(getLastModifiedDate()));
/*  49 */     json.put("size", getSize());
/*  50 */     json.put("isDirectory", isDirectory());
/*  51 */     json.put("isImage", isImage());
/*  52 */     json.put("isFile", isFile());
/*  53 */     return json;
/*     */   }
/*     */ 
/*     */   public static boolean allowEdit(String s) {
/*  57 */     s = s.toLowerCase();
/*  58 */     String[] as = EDITABLE_EXT;
/*  59 */     int i = as.length;
/*  60 */     for (int j = 0; j < i; j++) {
/*  61 */       String s1 = as[j];
/*  62 */       if (s1.equals(s))
/*  63 */         return true;
/*     */     }
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToTreeJson(FileWrap ob) throws JSONException
/*     */   {
/*  70 */     JSONObject json = new JSONObject();
/*  71 */     json.put("name", ob.getFilename());
/*  72 */     json.put("path", ob.getName());
/*  73 */     json.put("isEditable", ob.isEditable());
/*  74 */     List childs = ob.getChild();
/*  75 */     if (childs.size() > 0) {
/*  76 */       JSONArray childJson = new JSONArray();
/*  77 */       int j = 0;
/*  78 */       for (int i = 0; i < childs.size(); i++) {
/*  79 */         FileWrap f = (FileWrap)childs.get(i);
/*  80 */         if ((f.isDirectory()) || (f.isEditable())) {
/*  81 */           JSONObject obj = new JSONObject();
/*  82 */           obj = convertToTreeJson(f);
/*  83 */           childJson.put(j, obj);
/*     */         } else {
/*  85 */           j--;
/*     */         }
/*  87 */         j++;
/*     */       }
/*  89 */       json.put("child", childJson);
/*     */     } else {
/*  91 */       json.put("child", "");
/*     */     }
/*  93 */     return json;
/*     */   }
/*     */ 
/*     */   public FileWrap(File file)
/*     */   {
/* 103 */     this(file, null);
/*     */   }
/*     */ 
/*     */   public FileWrap(File file, String rootPath)
/*     */   {
/* 115 */     this(file, rootPath, null);
/*     */   }
/*     */ 
/*     */   public FileWrap(File file, String rootPath, FileFilter filter)
/*     */   {
/* 129 */     this.file = file;
/* 130 */     this.rootPath = rootPath;
/* 131 */     this.filter = filter;
/*     */   }
/*     */ 
/*     */   public static boolean editableExt(String ext)
/*     */   {
/* 142 */     ext = ext.toLowerCase(Locale.ENGLISH);
/* 143 */     for (String s : EDITABLE_EXT) {
/* 144 */       if (s.equals(ext)) {
/* 145 */         return true;
/*     */       }
/*     */     }
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 157 */     String path = this.file.getAbsolutePath();
/* 158 */     String relPath = path.substring(this.rootPath.length());
/*     */ 
/* 160 */     if (!relPath.startsWith(File.separator)) {
/* 161 */       relPath = File.separator + relPath;
/*     */     }
/* 163 */     return relPath.replace(File.separator, "/");
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 172 */     String name = getName();
/* 173 */     return name.substring(0, name.lastIndexOf('/'));
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/* 184 */     return this.filename != null ? this.filename : this.file.getName();
/*     */   }
/*     */ 
/*     */   public String getExtension()
/*     */   {
/* 193 */     return FilenameUtils.getExtension(this.file.getName());
/*     */   }
/*     */ 
/*     */   public long getLastModified()
/*     */   {
/* 202 */     return this.file.lastModified();
/*     */   }
/*     */ 
/*     */   public Date getLastModifiedDate()
/*     */   {
/* 211 */     return new Timestamp(this.file.lastModified());
/*     */   }
/*     */ 
/*     */   public long getSize()
/*     */   {
/* 220 */     return this.file.length() / 1024L + 1L;
/*     */   }
/*     */ 
/*     */   public String getIco()
/*     */   {
/* 238 */     if (this.file.isDirectory()) {
/* 239 */       return "folder";
/*     */     }
/* 241 */     String ext = getExtension().toLowerCase();
/* 242 */     if ((ext.equals("jpg")) || (ext.equals("jpeg")))
/* 243 */       return "jpg";
/* 244 */     if (ext.equals("png"))
/* 245 */       return "png";
/* 246 */     if (ext.equals("gif"))
/* 247 */       return "gif";
/* 248 */     if ((ext.equals("html")) || (ext.equals("htm")))
/* 249 */       return "html";
/* 250 */     if (ext.equals("swf"))
/* 251 */       return "swf";
/* 252 */     if (ext.equals("txt")) {
/* 253 */       return "txt";
/*     */     }
/* 255 */     return "unknow";
/*     */   }
/*     */ 
/*     */   public List<FileWrap> getChild()
/*     */   {
/* 267 */     if (this.child != null)
/* 268 */       return this.child;
/*     */     File[] files;
/*     */     File[] files;
/* 271 */     if (this.filter == null)
/* 272 */       files = getFile().listFiles();
/*     */     else {
/* 274 */       files = getFile().listFiles(this.filter);
/*     */     }
/* 276 */     List list = new ArrayList();
/* 277 */     if (files != null) {
/* 278 */       Arrays.sort(files, new FileComparator());
/* 279 */       for (File f : files) {
/* 280 */         FileWrap fw = new FileWrap(f, this.rootPath, this.filter);
/* 281 */         list.add(fw);
/*     */       }
/*     */     }
/* 284 */     return list;
/*     */   }
/*     */ 
/*     */   public File getFile()
/*     */   {
/* 293 */     return this.file;
/*     */   }
/*     */ 
/*     */   public boolean isImage()
/*     */   {
/* 302 */     if (isDirectory()) {
/* 303 */       return false;
/*     */     }
/* 305 */     String ext = getExtension();
/* 306 */     return ImageUtils.isValidImageExt(ext);
/*     */   }
/*     */ 
/*     */   public boolean isEditable()
/*     */   {
/* 315 */     if (isDirectory()) {
/* 316 */       return false;
/*     */     }
/* 318 */     String ext = getExtension().toLowerCase();
/* 319 */     for (String s : EDITABLE_EXT) {
/* 320 */       if (s.equals(ext)) {
/* 321 */         return true;
/*     */       }
/*     */     }
/* 324 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isDirectory()
/*     */   {
/* 333 */     return this.file.isDirectory();
/*     */   }
/*     */ 
/*     */   public boolean isFile()
/*     */   {
/* 342 */     return this.file.isFile();
/*     */   }
/*     */ 
/*     */   public void setFile(File file)
/*     */   {
/* 351 */     this.file = file;
/*     */   }
/*     */ 
/*     */   public void setFilename(String filename)
/*     */   {
/* 360 */     this.filename = filename;
/*     */   }
/*     */ 
/*     */   public void setChild(List<FileWrap> child)
/*     */   {
/* 369 */     this.child = child;
/*     */   }
/*     */ 
/*     */   public static class FileComparator
/*     */     implements Comparator<File>
/*     */   {
/*     */     public int compare(File o1, File o2)
/*     */     {
/* 380 */       if ((o1.isDirectory()) && (!o2.isDirectory()))
/* 381 */         return -1;
/* 382 */       if ((!o1.isDirectory()) && (o2.isDirectory())) {
/* 383 */         return 1;
/*     */       }
/* 385 */       return o1.compareTo(o2);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.file.FileWrap
 * JD-Core Version:    0.6.0
 */