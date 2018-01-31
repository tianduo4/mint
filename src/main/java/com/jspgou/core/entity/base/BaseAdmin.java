/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.Role;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseAdmin
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  24 */   public static String REF = "Admin";
/*  25 */   public static String PROP_WEBSITE = "website";
/*  26 */   public static String PROP_CREATE_TIME = "createTime";
/*  27 */   public static String PROP_DISABLED = "disabled";
/*  28 */   public static String PROP_USER = "user";
/*  29 */   public static String PROP_ID = "id";
/*     */ 
/*  62 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Date createTime;
/*     */   private Boolean disabled;
/*     */   private Boolean viewonlyAdmin;
/*     */   private User user;
/*     */   private Website website;
/*     */   private Set<Role> roles;
/*     */ 
/*     */   public BaseAdmin()
/*     */   {
/*  33 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseAdmin(Long id)
/*     */   {
/*  40 */     setId(id);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseAdmin(Long id, User user, Website website, Date date, Boolean disabled)
/*     */   {
/*  52 */     setId(id);
/*  53 */     setUser(user);
/*  54 */     setWebsite(website);
/*  55 */     setCreateTime(date);
/*  56 */     setDisabled(disabled);
/*  57 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  75 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id) {
/*  79 */     this.id = id;
/*  80 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime() {
/*  84 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime) {
/*  88 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled() {
/*  92 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Boolean disabled) {
/*  96 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public User getUser() {
/* 100 */     return this.user;
/*     */   }
/*     */ 
/*     */   public void setUser(User user) {
/* 104 */     this.user = user;
/*     */   }
/*     */ 
/*     */   public Website getWebsite() {
/* 108 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website) {
/* 112 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 118 */     if (obj == null) return false;
/* 119 */     if (!(obj instanceof Admin)) return false;
/*     */ 
/* 121 */     Admin admin = (Admin)obj;
/* 122 */     if ((getId() == null) || (admin.getId() == null)) return false;
/* 123 */     return getId().equals(admin.getId());
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
/*     */ 
/*     */   public void setRoles(Set<Role> roles) {
/* 145 */     this.roles = roles;
/*     */   }
/*     */ 
/*     */   public Set<Role> getRoles() {
/* 149 */     return this.roles;
/*     */   }
/*     */ 
/*     */   public void setViewonlyAdmin(Boolean viewonlyAdmin) {
/* 153 */     this.viewonlyAdmin = viewonlyAdmin;
/*     */   }
/*     */ 
/*     */   public Boolean getViewonlyAdmin() {
/* 157 */     return this.viewonlyAdmin;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseAdmin
 * JD-Core Version:    0.6.0
 */