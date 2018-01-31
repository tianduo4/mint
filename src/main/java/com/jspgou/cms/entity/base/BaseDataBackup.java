/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.DataBackup;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseDataBackup
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "DataBackup";
/*  20 */   public static String PROP_PASSWORD = "password";
/*  21 */   public static String PROP_USERNAME = "username";
/*  22 */   public static String PROP_ADDRESS = "address";
/*  23 */   public static String PROP_ID = "id";
/*  24 */   public static String PROP_DATA_BASE_NAME = "dataBaseName";
/*  25 */   public static String PROP_DATA_BASE_PATH = "dataBasePath";
/*     */ 
/*  65 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String dataBasePath;
/*     */   private String address;
/*     */   private String dataBaseName;
/*     */   private String username;
/*     */   private String password;
/*     */ 
/*     */   public BaseDataBackup()
/*     */   {
/*  30 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseDataBackup(Integer id)
/*     */   {
/*  37 */     setId(id);
/*  38 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseDataBackup(Integer id, String dataBasePath, String address, String dataBaseName, String username, String password)
/*     */   {
/*  52 */     setId(id);
/*  53 */     setDataBasePath(dataBasePath);
/*  54 */     setAddress(address);
/*  55 */     setDataBaseName(dataBaseName);
/*  56 */     setUsername(username);
/*  57 */     setPassword(password);
/*  58 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  86 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  94 */     this.id = id;
/*  95 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getDataBasePath()
/*     */   {
/* 105 */     return this.dataBasePath;
/*     */   }
/*     */ 
/*     */   public void setDataBasePath(String dataBasePath)
/*     */   {
/* 113 */     this.dataBasePath = dataBasePath;
/*     */   }
/*     */ 
/*     */   public String getAddress()
/*     */   {
/* 121 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String address)
/*     */   {
/* 129 */     this.address = address;
/*     */   }
/*     */ 
/*     */   public String getDataBaseName()
/*     */   {
/* 137 */     return this.dataBaseName;
/*     */   }
/*     */ 
/*     */   public void setDataBaseName(String dataBaseName)
/*     */   {
/* 145 */     this.dataBaseName = dataBaseName;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 153 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username)
/*     */   {
/* 161 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/* 169 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password)
/*     */   {
/* 177 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 184 */     if (obj == null) return false;
/* 185 */     if (!(obj instanceof DataBackup)) return false;
/*     */ 
/* 187 */     DataBackup dataBackup = (DataBackup)obj;
/* 188 */     if ((getId() == null) || (dataBackup.getId() == null)) return false;
/* 189 */     return getId().equals(dataBackup.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 195 */     if (-2147483648 == this.hashCode) {
/* 196 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 198 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 199 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 202 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 208 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseDataBackup
 * JD-Core Version:    0.6.0
 */