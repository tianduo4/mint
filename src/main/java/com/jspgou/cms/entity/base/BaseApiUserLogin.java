/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ApiUserLogin;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseApiUserLogin
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "ApiUserLogin";
/*  18 */   public static String PROP_LOGIN_COUNT = "loginCount";
/*  19 */   public static String PROP_LOGIN_TIME = "loginTime";
/*  20 */   public static String PROP_SESSION_KEY = "sessionKey";
/*  21 */   public static String PROP_USERNAME = "username";
/*  22 */   public static String PROP_ACTIVETIME = "activeTime";
/*  23 */   public static String PROP_ID = "id";
/*     */ 
/*  61 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String sessionKey;
/*     */   private String username;
/*     */   private Date loginTime;
/*     */   private Integer loginCount;
/*     */   private Date activeTime;
/*     */ 
/*     */   public BaseApiUserLogin()
/*     */   {
/*  28 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseApiUserLogin(Long id)
/*     */   {
/*  35 */     setId(id);
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseApiUserLogin(Long id, String username, Date loginTime, Integer loginCount, Date actvieTime)
/*     */   {
/*  49 */     setId(id);
/*  50 */     setUsername(username);
/*  51 */     setLoginTime(loginTime);
/*  52 */     setLoginCount(loginCount);
/*  53 */     setActiveTime(actvieTime);
/*  54 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  81 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  89 */     this.id = id;
/*  90 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getSessionKey()
/*     */   {
/* 100 */     return this.sessionKey;
/*     */   }
/*     */ 
/*     */   public void setSessionKey(String sessionKey)
/*     */   {
/* 108 */     this.sessionKey = sessionKey;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 116 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username)
/*     */   {
/* 124 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public Date getLoginTime()
/*     */   {
/* 132 */     return this.loginTime;
/*     */   }
/*     */ 
/*     */   public void setLoginTime(Date loginTime)
/*     */   {
/* 140 */     this.loginTime = loginTime;
/*     */   }
/*     */ 
/*     */   public Integer getLoginCount()
/*     */   {
/* 148 */     return this.loginCount;
/*     */   }
/*     */ 
/*     */   public void setLoginCount(Integer loginCount)
/*     */   {
/* 156 */     this.loginCount = loginCount;
/*     */   }
/*     */ 
/*     */   public Date getActiveTime()
/*     */   {
/* 161 */     return this.activeTime;
/*     */   }
/*     */ 
/*     */   public void setActiveTime(Date activeTime) {
/* 165 */     this.activeTime = activeTime;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj) {
/* 169 */     if (obj == null) return false;
/* 170 */     if (!(obj instanceof ApiUserLogin)) return false;
/*     */ 
/* 172 */     ApiUserLogin apiUserLogin = (ApiUserLogin)obj;
/* 173 */     if ((getId() == null) || (apiUserLogin.getId() == null)) return false;
/* 174 */     return getId().equals(apiUserLogin.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 179 */     if (-2147483648 == this.hashCode) {
/* 180 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 182 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 183 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 186 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 191 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseApiUserLogin
 * JD-Core Version:    0.6.0
 */