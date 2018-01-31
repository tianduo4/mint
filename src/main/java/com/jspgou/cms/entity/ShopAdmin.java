/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.entity.base.BaseShopAdmin;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.Role;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ShopAdmin extends BaseShopAdmin
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public boolean isSuper()
/*     */   {
/*  27 */     Set roles = getAdmin().getRoles();
/*  28 */     for (Role role : roles) {
/*  29 */       if (role.getSuper().booleanValue()) {
/*  30 */         return true;
/*     */       }
/*     */     }
/*  33 */     return false;
/*     */   }
/*     */ 
/*     */   public Set<String> getPerms() {
/*  37 */     Admin admin = getAdmin();
/*  38 */     if (admin != null) {
/*  39 */       return admin.getRolesPerms();
/*     */     }
/*  41 */     return null;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  46 */     Admin admin = getAdmin();
/*  47 */     if (admin != null) {
/*  48 */       return admin.getUsername();
/*     */     }
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */   public String getEmail()
/*     */   {
/*  55 */     Admin admin = getAdmin();
/*  56 */     if (admin != null) {
/*  57 */       return admin.getEmail();
/*     */     }
/*  59 */     return null;
/*     */   }
/*     */ 
/*     */   public Date getLastLoginTime()
/*     */   {
/*  64 */     Admin admin = getAdmin();
/*  65 */     if (admin != null) {
/*  66 */       return admin.getLastLoginTime();
/*     */     }
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLastLoginIp()
/*     */   {
/*  73 */     Admin admin = getAdmin();
/*  74 */     if (admin != null) {
/*  75 */       return admin.getLastLoginIp();
/*     */     }
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */   public Boolean getViewonlyAdmin()
/*     */   {
/*  82 */     Admin admin = getAdmin();
/*  83 */     if (admin != null) {
/*  84 */       return admin.getViewonlyAdmin();
/*     */     }
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */   public Date getCurrentLoginTime()
/*     */   {
/*  91 */     Admin admin = getAdmin();
/*  92 */     if (admin != null) {
/*  93 */       return admin.getCurrentLoginTime();
/*     */     }
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   public String getCurrentLoginIp()
/*     */   {
/* 100 */     Admin admin = getAdmin();
/* 101 */     if (admin != null) {
/* 102 */       return admin.getCurrentLoginIp();
/*     */     }
/* 104 */     return null;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled()
/*     */   {
/* 109 */     Admin admin = getAdmin();
/* 110 */     if (admin != null) {
/* 111 */       return admin.getDisabled();
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   public ShopAdmin()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ShopAdmin(Long id)
/*     */   {
/* 126 */     super(id);
/*     */   }
/*     */ 
/*     */   public ShopAdmin(Long id, Website website)
/*     */   {
/* 134 */     super(id, website);
/*     */   }
/*     */ 
/*     */   public JSONObject converToJson() {
/* 138 */     JSONObject json = new JSONObject();
/* 139 */     json.put("id", CommonUtils.parseId(getId()));
/* 140 */     json.put("username", CommonUtils.parseStr(getUsername()));
/* 141 */     json.put("firstname", CommonUtils.parseStr(getFirstname()));
/* 142 */     json.put("createTime", getAdmin() == null ? "" : CommonUtils.parseDate(getAdmin().getCreateTime(), DateUtils.COMMON_FORMAT_STR));
/* 143 */     json.put("currentLoginTime", getAdmin() == null ? "" : CommonUtils.parseDate(getAdmin().getCurrentLoginTime(), DateUtils.COMMON_FORMAT_STR));
/* 144 */     json.put("disabled", getAdmin() == null ? "" : getAdmin().getDisabled());
/* 145 */     json.put("viewonlyAdmin", getAdmin() == null ? "" : getAdmin().getViewonlyAdmin());
/* 146 */     json.put("email", getAdmin() == null ? "" : CommonUtils.parseStr(getAdmin().getEmail()));
/* 147 */     String str = "";
/* 148 */     if (getAdmin() != null) {
/* 149 */       Set roles = getAdmin().getRoles();
/* 150 */       if (roles != null) {
/* 151 */         for (Role role : roles) {
/* 152 */           str = str + "," + role.getId();
/*     */         }
/* 154 */         if (StringUtils.isNotEmpty(str)) {
/* 155 */           str = str.substring(1, str.length());
/*     */         }
/*     */       }
/*     */     }
/* 159 */     json.put("roleIds", str);
/* 160 */     return json;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopAdmin
 * JD-Core Version:    0.6.0
 */