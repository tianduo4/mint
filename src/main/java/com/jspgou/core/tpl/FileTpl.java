/*     */ package com.jspgou.core.tpl;
/*     */ 
/*     */ import com.jspgou.common.file.FileWrap.FileComparator;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class FileTpl
/*     */   implements Tpl
/*     */ {
/*     */   private File file;
/*     */   private String root;
/*     */   private List<FileTpl> child;
/*     */ 
/*     */   public JSONObject convertToJson()
/*     */     throws JSONException
/*     */   {
/*  27 */     JSONObject json = new JSONObject();
/*  28 */     json.put("name", getName());
/*  29 */     json.put("path", getPath());
/*  30 */     json.put("filename", getFilename());
/*  31 */     if (StringUtils.isNotBlank(getSource()))
/*  32 */       json.put("source", getSource());
/*     */     else {
/*  34 */       json.put("source", "");
/*     */     }
/*  36 */     json.put("length", getLength());
/*  37 */     json.put("lastModifiedDate", DateUtils.parseDateToTimeStr(getLastModifiedDate()));
/*  38 */     json.put("size", getSize());
/*  39 */     json.put("isDirectory", isDirectory());
/*  40 */     return json;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToTreeJson(FileTpl ob) throws JSONException
/*     */   {
/*  45 */     JSONObject json = new JSONObject();
/*  46 */     json.put("name", ob.getFilename());
/*  47 */     json.put("path", ob.getName());
/*  48 */     json.put("isDirectory", ob.isDirectory());
/*  49 */     List childs = ob.getChild();
/*  50 */     if (childs.size() > 0) {
/*  51 */       JSONArray childJson = new JSONArray();
/*  52 */       for (int i = 0; i < childs.size(); i++) {
/*  53 */         FileTpl f = (FileTpl)childs.get(i);
/*  54 */         JSONObject obj = new JSONObject();
/*  55 */         obj = convertToTreeJson(f);
/*  56 */         childJson.put(i, obj);
/*     */       }
/*  58 */       json.put("child", childJson);
/*     */     } else {
/*  60 */       json.put("child", "");
/*     */     }
/*  62 */     return json;
/*     */   }
/*     */ 
/*     */   public FileTpl(File file, String root) {
/*  66 */     this.file = file;
/*  67 */     this.root = root;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  71 */     String ap = this.file.getAbsolutePath().substring(this.root.length());
/*  72 */     ap = ap.replace(File.separatorChar, '/');
/*     */ 
/*  74 */     if (!ap.startsWith("/")) {
/*  75 */       ap = "/" + ap;
/*     */     }
/*  77 */     return ap;
/*     */   }
/*     */ 
/*     */   public String getPath() {
/*  81 */     String name = getName();
/*  82 */     return name.substring(0, name.lastIndexOf('/'));
/*     */   }
/*     */ 
/*     */   public String getFilename() {
/*  86 */     return this.file.getName();
/*     */   }
/*     */ 
/*     */   public String getSource() {
/*  90 */     if (this.file.isDirectory())
/*  91 */       return null;
/*     */     try
/*     */     {
/*  94 */       return FileUtils.readFileToString(this.file, "UTF-8"); } catch (IOException e) {
/*     */     }
/*  96 */     throw new RuntimeException(e);
/*     */   }
/*     */ 
/*     */   public long getLastModified()
/*     */   {
/* 101 */     return this.file.lastModified();
/*     */   }
/*     */ 
/*     */   public Date getLastModifiedDate() {
/* 105 */     return new Timestamp(getLastModified());
/*     */   }
/*     */ 
/*     */   public long getLength() {
/* 109 */     return this.file.length();
/*     */   }
/*     */ 
/*     */   public int getSize() {
/* 113 */     return (int)(getLength() / 1024L) + 1;
/*     */   }
/*     */ 
/*     */   public boolean isDirectory() {
/* 117 */     return this.file.isDirectory();
/*     */   }
/*     */ 
/*     */   public File getFile()
/*     */   {
/* 126 */     return this.file;
/*     */   }
/*     */ 
/*     */   public List<FileTpl> getChild()
/*     */   {
/* 136 */     if (this.child != null) {
/* 137 */       return this.child;
/*     */     }
/*     */ 
/* 140 */     File[] files = getFile().listFiles();
/* 141 */     List list = new ArrayList();
/* 142 */     if (files != null) {
/* 143 */       Arrays.sort(files, new FileWrap.FileComparator());
/* 144 */       for (File f : files) {
/* 145 */         FileTpl fw = new FileTpl(f, this.root);
/* 146 */         list.add(fw);
/*     */       }
/*     */     }
/* 149 */     return list;
/*     */   }
/*     */ 
/*     */   public void setChild(List<FileTpl> child)
/*     */   {
/* 159 */     this.child = child;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.tpl.FileTpl
 * JD-Core Version:    0.6.0
 */