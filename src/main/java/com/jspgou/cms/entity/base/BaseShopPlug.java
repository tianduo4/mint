/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopPlug;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseShopPlug
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "CmsPlug";
/*  18 */   public static String PROP_NAME = "name";
/*  19 */   public static String PROP_FILE_CONFLICT = "fileConflict";
/*  20 */   public static String PROP_DESCRIPTION = "description";
/*  21 */   public static String PROP_USED = "used";
/*  22 */   public static String PROP_UPLOAD_TIME = "uploadTime";
/*  23 */   public static String PROP_UNINSTALL_TIME = "uninstallTime";
/*  24 */   public static String PROP_AUTHOR = "author";
/*  25 */   public static String PROP_ID = "id";
/*  26 */   public static String PROP_INSTALL_TIME = "installTime";
/*  27 */   public static String PROP_PLUG_PERMS = "plugPerms";
/*  28 */   public static String PROP_PATH = "path";
/*     */ 
/*  66 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String path;
/*     */   private String description;
/*     */   private String author;
/*     */   private Date uploadTime;
/*     */   private Date installTime;
/*     */   private Date uninstallTime;
/*     */   private boolean fileConflict;
/*     */   private boolean used;
/*     */   private String plugPerms;
/*     */   private boolean plugRepair;
/*     */ 
/*     */   public BaseShopPlug()
/*     */   {
/*  33 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopPlug(Long id)
/*     */   {
/*  40 */     setId(id);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopPlug(Long id, String name, String path, Date uploadTime, boolean fileConflict, boolean used)
/*     */   {
/*  55 */     setId(id);
/*  56 */     setName(name);
/*  57 */     setPath(path);
/*  58 */     setUploadTime(uploadTime);
/*  59 */     setFileConflict(fileConflict);
/*  60 */     setUsed(used);
/*  61 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  92 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 100 */     this.id = id;
/* 101 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 111 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 119 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 127 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/* 135 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 143 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 151 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 159 */     return this.author;
/*     */   }
/*     */ 
/*     */   public void setAuthor(String author)
/*     */   {
/* 167 */     this.author = author;
/*     */   }
/*     */ 
/*     */   public Date getUploadTime()
/*     */   {
/* 175 */     return this.uploadTime;
/*     */   }
/*     */ 
/*     */   public void setUploadTime(Date uploadTime)
/*     */   {
/* 183 */     this.uploadTime = uploadTime;
/*     */   }
/*     */ 
/*     */   public Date getInstallTime()
/*     */   {
/* 191 */     return this.installTime;
/*     */   }
/*     */ 
/*     */   public void setInstallTime(Date installTime)
/*     */   {
/* 199 */     this.installTime = installTime;
/*     */   }
/*     */ 
/*     */   public Date getUninstallTime()
/*     */   {
/* 207 */     return this.uninstallTime;
/*     */   }
/*     */ 
/*     */   public void setUninstallTime(Date uninstallTime)
/*     */   {
/* 215 */     this.uninstallTime = uninstallTime;
/*     */   }
/*     */ 
/*     */   public boolean isFileConflict()
/*     */   {
/* 223 */     return this.fileConflict;
/*     */   }
/*     */ 
/*     */   public void setFileConflict(boolean fileConflict)
/*     */   {
/* 231 */     this.fileConflict = fileConflict;
/*     */   }
/*     */ 
/*     */   public boolean isUsed()
/*     */   {
/* 239 */     return this.used;
/*     */   }
/*     */ 
/*     */   public void setUsed(boolean used)
/*     */   {
/* 247 */     this.used = used;
/*     */   }
/*     */ 
/*     */   public String getPlugPerms()
/*     */   {
/* 255 */     return this.plugPerms;
/*     */   }
/*     */ 
/*     */   public void setPlugPerms(String plugPerms)
/*     */   {
/* 263 */     this.plugPerms = plugPerms;
/*     */   }
/*     */ 
/*     */   public boolean getPlugRepair() {
/* 267 */     return this.plugRepair;
/*     */   }
/*     */ 
/*     */   public void setPlugRepair(boolean plugRepair) {
/* 271 */     this.plugRepair = plugRepair;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj) {
/* 275 */     if (obj == null) return false;
/* 276 */     if (!(obj instanceof ShopPlug)) return false;
/*     */ 
/* 278 */     ShopPlug shopPlug = (ShopPlug)obj;
/* 279 */     if ((getId() == null) || (shopPlug.getId() == null)) return false;
/* 280 */     return getId().equals(shopPlug.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 285 */     if (-2147483648 == this.hashCode) {
/* 286 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 288 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 289 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 292 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 297 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopPlug
 * JD-Core Version:    0.6.0
 */