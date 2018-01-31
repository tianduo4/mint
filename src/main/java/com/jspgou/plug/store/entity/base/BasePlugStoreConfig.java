/*     */ package com.jspgou.plug.store.entity.base;
/*     */ 
/*     */ import com.jspgou.plug.store.entity.PlugStoreConfig;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BasePlugStoreConfig
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "PlugStoreConfig";
/*  18 */   public static String PROP_MAINTAIN_COMPANY = "maintainCompany";
/*  19 */   public static String PROP_SERVER_URL = "serverUrl";
/*  20 */   public static String PROP_MAINTAIN_TEL = "maintainTel";
/*  21 */   public static String PROP_PASSWOD = "passwod";
/*  22 */   public static String PROP_ID = "id";
/*     */ 
/*  56 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String serverUrl;
/*     */   private String password;
/*     */ 
/*     */   public BasePlugStoreConfig()
/*     */   {
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePlugStoreConfig(Integer id)
/*     */   {
/*  34 */     setId(id);
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePlugStoreConfig(Integer id, String serverUrl, String passwod)
/*     */   {
/*  46 */     setId(id);
/*  47 */     setServerUrl(serverUrl);
/*  48 */     setPassword(passwod);
/*  49 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  74 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  82 */     this.id = id;
/*  83 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getServerUrl()
/*     */   {
/*  93 */     return this.serverUrl;
/*     */   }
/*     */ 
/*     */   public void setServerUrl(String serverUrl)
/*     */   {
/* 101 */     this.serverUrl = serverUrl;
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/* 109 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password) {
/* 113 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 119 */     if (obj == null) return false;
/* 120 */     if (!(obj instanceof PlugStoreConfig)) return false;
/*     */ 
/* 122 */     PlugStoreConfig plugStoreConfig = (PlugStoreConfig)obj;
/* 123 */     if ((getId() == null) || (plugStoreConfig.getId() == null)) return false;
/* 124 */     return getId().equals(plugStoreConfig.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 129 */     if (-2147483648 == this.hashCode) {
/* 130 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 132 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 133 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 136 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 141 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.store.entity.base.BasePlugStoreConfig
 * JD-Core Version:    0.6.0
 */