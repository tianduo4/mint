/*     */ package com.jspgou.plug.weixin.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.plug.weixin.entity.Weixin;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseWeixin
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "Weixin";
/*  18 */   public static String PROP_SITE = "site";
/*  19 */   public static String PROP_ID = "id";
/*  20 */   public static String PROP_PIC = "pic";
/*     */ 
/*  40 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String welcome;
/*     */   private String pic;
/*     */   private String appKey;
/*     */   private String appSecret;
/*     */   private String token;
/*     */   private Website site;
/*     */ 
/*     */   public BaseWeixin()
/*     */   {
/*  25 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWeixin(Long id)
/*     */   {
/*  32 */     setId(id);
/*  33 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  63 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  71 */     this.id = id;
/*  72 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getWelcome()
/*     */   {
/*  77 */     return this.welcome;
/*     */   }
/*     */ 
/*     */   public void setWelcome(String welcome) {
/*  81 */     this.welcome = welcome;
/*     */   }
/*     */ 
/*     */   public String getPic()
/*     */   {
/*  88 */     return this.pic;
/*     */   }
/*     */ 
/*     */   public void setPic(String pic)
/*     */   {
/*  96 */     this.pic = pic;
/*     */   }
/*     */   public String getToken() {
/*  99 */     return this.token;
/*     */   }
/*     */ 
/*     */   public void setToken(String token) {
/* 103 */     this.token = token;
/*     */   }
/*     */ 
/*     */   public String getAppKey() {
/* 107 */     return this.appKey;
/*     */   }
/*     */   public void setAppKey(String appKey) {
/* 110 */     this.appKey = appKey;
/*     */   }
/*     */ 
/*     */   public String getAppSecret() {
/* 114 */     return this.appSecret;
/*     */   }
/*     */ 
/*     */   public void setAppSecret(String appSecret) {
/* 118 */     this.appSecret = appSecret;
/*     */   }
/*     */ 
/*     */   public Website getSite()
/*     */   {
/* 126 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(Website site)
/*     */   {
/* 134 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 140 */     if (obj == null) return false;
/* 141 */     if (!(obj instanceof Weixin)) return false;
/*     */ 
/* 143 */     Weixin weixin = (Weixin)obj;
/* 144 */     if ((getId() == null) || (weixin.getId() == null)) return false;
/* 145 */     return getId().equals(weixin.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 150 */     if (-2147483648 == this.hashCode) {
/* 151 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 153 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 154 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 157 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 162 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.entity.base.BaseWeixin
 * JD-Core Version:    0.6.0
 */