/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.ThirdAccount;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseThirdAccount
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "ThirdAccount";
/*  18 */   public static String PROP_SOURCE = "source";
/*  19 */   public static String PROP_ACCOUNT_KEY = "accountKey";
/*  20 */   public static String PROP_THIRD_LOGIN_NAME = "thirdLoginName";
/*  21 */   public static String PROP_USERNAME = "username";
/*  22 */   public static String PROP_ID = "id";
/*     */ 
/*  58 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String accountKey;
/*     */   private String username;
/*     */   private String source;
/*     */   private String thirdLoginName;
/*     */ 
/*     */   public BaseThirdAccount()
/*     */   {
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseThirdAccount(Long id)
/*     */   {
/*  34 */     setId(id);
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseThirdAccount(Long id, String accountKey, String username, String source)
/*     */   {
/*  47 */     setId(id);
/*  48 */     setAccountKey(accountKey);
/*  49 */     setUsername(username);
/*  50 */     setSource(source);
/*  51 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  78 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  86 */     this.id = id;
/*  87 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getAccountKey()
/*     */   {
/*  97 */     return this.accountKey;
/*     */   }
/*     */ 
/*     */   public void setAccountKey(String accountKey)
/*     */   {
/* 105 */     this.accountKey = accountKey;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 113 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username)
/*     */   {
/* 121 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getSource()
/*     */   {
/* 129 */     return this.source;
/*     */   }
/*     */ 
/*     */   public void setSource(String source)
/*     */   {
/* 137 */     this.source = source;
/*     */   }
/*     */ 
/*     */   public String getThirdLoginName()
/*     */   {
/* 145 */     return this.thirdLoginName;
/*     */   }
/*     */ 
/*     */   public void setThirdLoginName(String thirdLoginName)
/*     */   {
/* 153 */     this.thirdLoginName = thirdLoginName;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 159 */     if (obj == null) return false;
/* 160 */     if (!(obj instanceof ThirdAccount)) return false;
/*     */ 
/* 162 */     ThirdAccount thirdAccount = (ThirdAccount)obj;
/* 163 */     if ((getId() == null) || (thirdAccount.getId() == null)) return false;
/* 164 */     return getId().equals(thirdAccount.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 169 */     if (-2147483648 == this.hashCode) {
/* 170 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 172 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 173 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 176 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 181 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseThirdAccount
 * JD-Core Version:    0.6.0
 */