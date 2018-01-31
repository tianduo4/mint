/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.WebserviceAuth;
/*     */ import com.jspgou.cms.entity.WebserviceCallRecord;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseWebserviceAuth
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "WebserviceAuth";
/*  18 */   public static String PROP_ENABLE = "enable";
/*  19 */   public static String PROP_PASSWORD = "password";
/*  20 */   public static String PROP_USERNAME = "username";
/*  21 */   public static String PROP_ID = "id";
/*  22 */   public static String PROP_SYSTEM = "system";
/*     */ 
/*  60 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String username;
/*     */   private String password;
/*     */   private String system;
/*     */   private boolean enable;
/*     */   private Set<WebserviceCallRecord> webserviceCallRecords;
/*     */ 
/*     */   public BaseWebserviceAuth()
/*     */   {
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWebserviceAuth(Integer id)
/*     */   {
/*  34 */     setId(id);
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWebserviceAuth(Integer id, String username, String password, String system, boolean enable)
/*     */   {
/*  48 */     setId(id);
/*  49 */     setUsername(username);
/*  50 */     setPassword(password);
/*  51 */     setSystem(system);
/*  52 */     setEnable(enable);
/*  53 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  83 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  91 */     this.id = id;
/*  92 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 102 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username)
/*     */   {
/* 110 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/* 118 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password)
/*     */   {
/* 126 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public String getSystem()
/*     */   {
/* 134 */     return this.system;
/*     */   }
/*     */ 
/*     */   public void setSystem(String system)
/*     */   {
/* 142 */     this.system = system;
/*     */   }
/*     */ 
/*     */   public boolean isEnable()
/*     */   {
/* 150 */     return this.enable;
/*     */   }
/*     */ 
/*     */   public void setEnable(boolean enable)
/*     */   {
/* 158 */     this.enable = enable;
/*     */   }
/*     */ 
/*     */   public Set<WebserviceCallRecord> getWebserviceCallRecords()
/*     */   {
/* 166 */     return this.webserviceCallRecords;
/*     */   }
/*     */ 
/*     */   public void setWebserviceCallRecords(Set<WebserviceCallRecord> webserviceCallRecords)
/*     */   {
/* 174 */     this.webserviceCallRecords = webserviceCallRecords;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 181 */     if (obj == null) return false;
/* 182 */     if (!(obj instanceof WebserviceAuth)) return false;
/*     */ 
/* 184 */     WebserviceAuth webserviceAuth = (WebserviceAuth)obj;
/* 185 */     if ((getId() == null) || (webserviceAuth.getId() == null)) return false;
/* 186 */     return getId().equals(webserviceAuth.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 192 */     if (-2147483648 == this.hashCode) {
/* 193 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 195 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 196 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 199 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 205 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseWebserviceAuth
 * JD-Core Version:    0.6.0
 */