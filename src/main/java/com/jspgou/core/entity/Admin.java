/*     */ package com.jspgou.core.entity;
/*     */ 
/*     */ import com.jspgou.core.entity.base.BaseAdmin;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Admin extends BaseAdmin
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public void init()
/*     */   {
/*  15 */     if (getCreateTime() == null)
/*  16 */       setCreateTime(new Date());
/*  17 */     if (getDisabled() == null)
/*  18 */       setDisabled(Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   public Set<String> getRolesPerms() {
/*  22 */     Set roles = getRoles();
/*  23 */     if (roles == null) {
/*  24 */       return null;
/*     */     }
/*  26 */     Set allPerms = new HashSet();
/*  27 */     for (Role role : getRoles()) {
/*  28 */       allPerms.addAll(role.getPerms());
/*     */     }
/*  30 */     return allPerms;
/*     */   }
/*     */ 
/*     */   public Integer[] getRoleIds() {
/*  34 */     Set roles = getRoles();
/*  35 */     return Role.fetchIds(roles);
/*     */   }
/*     */ 
/*     */   public void addToRoles(Role role) {
/*  39 */     if (role == null) {
/*  40 */       return;
/*     */     }
/*  42 */     Set set = getRoles();
/*  43 */     if (set == null) {
/*  44 */       set = new HashSet();
/*  45 */       setRoles(set);
/*     */     }
/*  47 */     set.add(role);
/*     */   }
/*     */ 
/*     */   public boolean isSuper() {
/*  51 */     Set roles = getRoles();
/*  52 */     if (roles == null) {
/*  53 */       return false;
/*     */     }
/*  55 */     for (Role role : getRoles()) {
/*  56 */       if (role.getSuper().booleanValue()) {
/*  57 */         return true;
/*     */       }
/*     */     }
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   public Admin()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Admin(Long id)
/*     */   {
/*  71 */     super(id);
/*     */   }
/*     */ 
/*     */   public Admin(Long id, User user, Website website, Date date, Boolean boolean1)
/*     */   {
/*  87 */     super(id, 
/*  84 */       user, 
/*  85 */       website, 
/*  86 */       date, 
/*  87 */       boolean1);
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  94 */     User user = getUser();
/*  95 */     if (user != null) {
/*  96 */       return user.getUsername();
/*     */     }
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   public String getEmail()
/*     */   {
/* 103 */     User user = getUser();
/* 104 */     if (user != null) {
/* 105 */       return user.getEmail();
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLastLoginIp()
/*     */   {
/* 113 */     User user = getUser();
/* 114 */     if (user != null) {
/* 115 */       return user.getLastLoginIp();
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */   public Date getLastLoginTime() {
/* 121 */     User user = getUser();
/* 122 */     if (user != null) {
/* 123 */       return user.getLastLoginTime();
/*     */     }
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   public String getCurrentLoginIp()
/*     */   {
/* 130 */     User user = getUser();
/* 131 */     if (user != null) {
/* 132 */       return user.getCurrentLoginIp();
/*     */     }
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */   public Date getCurrentLoginTime()
/*     */   {
/* 139 */     User user = getUser();
/* 140 */     if (user != null) {
/* 141 */       return user.getCurrentLoginTime();
/*     */     }
/* 143 */     return null;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.Admin
 * JD-Core Version:    0.6.0
 */