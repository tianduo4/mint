/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShopAdmin
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "ShopAdmin";
/*  24 */   public static String PROP_LASTNAME = "lastname";
/*  25 */   public static String PROP_WEBSITE = "website";
/*  26 */   public static String PROP_FIRSTNAME = "firstname";
/*  27 */   public static String PROP_ADMIN = "admin";
/*  28 */   public static String PROP_ID = "id";
/*     */ 
/*  60 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String firstname;
/*     */   private String lastname;
/*     */   private Admin admin;
/*     */   private Website website;
/*     */ 
/*     */   public BaseShopAdmin()
/*     */   {
/*  33 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopAdmin(Long id)
/*     */   {
/*  40 */     setId(id);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopAdmin(Long id, Website website)
/*     */   {
/*  51 */     setId(id);
/*  52 */     setWebsite(website);
/*  53 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  84 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  92 */     this.id = id;
/*  93 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getFirstname()
/*     */   {
/* 103 */     return this.firstname;
/*     */   }
/*     */ 
/*     */   public void setFirstname(String firstname)
/*     */   {
/* 111 */     this.firstname = firstname;
/*     */   }
/*     */ 
/*     */   public String getLastname()
/*     */   {
/* 119 */     return this.lastname;
/*     */   }
/*     */ 
/*     */   public void setLastname(String lastname)
/*     */   {
/* 127 */     this.lastname = lastname;
/*     */   }
/*     */ 
/*     */   public Admin getAdmin()
/*     */   {
/* 135 */     return this.admin;
/*     */   }
/*     */ 
/*     */   public void setAdmin(Admin admin)
/*     */   {
/* 143 */     this.admin = admin;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 151 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 159 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 166 */     if (obj == null) return false;
/* 167 */     if (!(obj instanceof ShopAdmin)) return false;
/*     */ 
/* 169 */     ShopAdmin shopAdmin = (ShopAdmin)obj;
/* 170 */     if ((getId() == null) || (shopAdmin.getId() == null)) return false;
/* 171 */     return getId().equals(shopAdmin.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 177 */     if (-2147483648 == this.hashCode) {
/* 178 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 180 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 181 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 184 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 190 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopAdmin
 * JD-Core Version:    0.6.0
 */