/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseApiAccount
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "ApiAccount";
/*  18 */   public static String PROP_AES_KEY = "aesKey";
/*  19 */   public static String PROP_DISABLED = "disabled";
/*  20 */   public static String PROP_IV_KEY = "ivKey";
/*  21 */   public static String PROP_APP_ID = "appId";
/*  22 */   public static String PROP_ID = "id";
/*  23 */   public static String PROP_APP_KEY = "appKey";
/*     */ 
/*  61 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String appId;
/*     */   private String appKey;
/*     */   private String aesKey;
/*     */   private boolean disabled;
/*     */   private String ivKey;
/*     */ 
/*     */   public BaseApiAccount()
/*     */   {
/*  28 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseApiAccount(Long id)
/*     */   {
/*  35 */     setId(id);
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseApiAccount(Long id, String appId, String appKey, String aesKey, boolean disabled)
/*     */   {
/*  49 */     setId(id);
/*  50 */     setAppId(appId);
/*  51 */     setAppKey(appKey);
/*  52 */     setAesKey(aesKey);
/*  53 */     setDisabled(disabled);
/*  54 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  82 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  90 */     this.id = id;
/*  91 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getAppId()
/*     */   {
/* 101 */     return this.appId;
/*     */   }
/*     */ 
/*     */   public void setAppId(String appId)
/*     */   {
/* 109 */     this.appId = appId;
/*     */   }
/*     */ 
/*     */   public String getAppKey()
/*     */   {
/* 117 */     return this.appKey;
/*     */   }
/*     */ 
/*     */   public void setAppKey(String appKey)
/*     */   {
/* 125 */     this.appKey = appKey;
/*     */   }
/*     */ 
/*     */   public String getAesKey()
/*     */   {
/* 133 */     return this.aesKey;
/*     */   }
/*     */ 
/*     */   public void setAesKey(String aesKey)
/*     */   {
/* 141 */     this.aesKey = aesKey;
/*     */   }
/*     */ 
/*     */   public boolean getDisabled()
/*     */   {
/* 149 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(boolean disabled)
/*     */   {
/* 157 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public String getIvKey()
/*     */   {
/* 165 */     return this.ivKey;
/*     */   }
/*     */ 
/*     */   public void setIvKey(String ivKey)
/*     */   {
/* 173 */     this.ivKey = ivKey;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 179 */     if (obj == null) return false;
/* 180 */     if (!(obj instanceof ApiAccount)) return false;
/*     */ 
/* 182 */     ApiAccount apiAccount = (ApiAccount)obj;
/* 183 */     if ((getId() == null) || (apiAccount.getId() == null)) return false;
/* 184 */     return getId().equals(apiAccount.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 189 */     if (-2147483648 == this.hashCode) {
/* 190 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 192 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 193 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 196 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 201 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseApiAccount
 * JD-Core Version:    0.6.0
 */