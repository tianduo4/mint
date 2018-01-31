/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseProductPicture
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ProductPicture";
/*  20 */   public static String PROP_BIG_PICTURE_PATH = "bigPicturePath";
/*  21 */   public static String PROP_APP_PICTURE_PATH = "appPicturePath";
/*  22 */   public static String PROP_PICTURE_PATH = "picturePath";
/*     */   private String picturePath;
/*     */   private String bigPicturePath;
/*     */   private String appPicturePath;
/*     */ 
/*     */   public BaseProductPicture()
/*     */   {
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductPicture(String picturePath, String bigPicturePath, String appPicturePath)
/*     */   {
/*  38 */     setPicturePath(picturePath);
/*  39 */     setBigPicturePath(bigPicturePath);
/*  40 */     setAppPicturePath(appPicturePath);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getPicturePath()
/*     */   {
/*  62 */     return this.picturePath;
/*     */   }
/*     */ 
/*     */   public void setPicturePath(String picturePath)
/*     */   {
/*  70 */     this.picturePath = picturePath;
/*     */   }
/*     */ 
/*     */   public String getBigPicturePath()
/*     */   {
/*  78 */     return this.bigPicturePath;
/*     */   }
/*     */ 
/*     */   public void setBigPicturePath(String bigPicturePath)
/*     */   {
/*  86 */     this.bigPicturePath = bigPicturePath;
/*     */   }
/*     */ 
/*     */   public String getAppPicturePath()
/*     */   {
/*  94 */     return this.appPicturePath;
/*     */   }
/*     */ 
/*     */   public void setAppPicturePath(String appPicturePath)
/*     */   {
/* 102 */     this.appPicturePath = appPicturePath;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 112 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProductPicture
 * JD-Core Version:    0.6.0
 */