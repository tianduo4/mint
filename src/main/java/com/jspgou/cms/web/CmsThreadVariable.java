/*     */ package com.jspgou.cms.web;
/*     */ 
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ApiUserLogin;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ 
/*     */ public class CmsThreadVariable
/*     */ {
/*  17 */   private static ThreadLocal<User> cmsUserVariable = new ThreadLocal();
/*     */ 
/*  22 */   private static ThreadLocal<ApiUserLogin> apiLogVariable = new ThreadLocal();
/*     */ 
/*  27 */   private static ThreadLocal<ApiAccount> apiAccountVariable = new ThreadLocal();
/*     */ 
/*  33 */   private static ThreadLocal<ShopAdmin> cmsAdminUserVariable = new ThreadLocal();
/*     */ 
/*  37 */   private static ThreadLocal<Website> cmsSiteVariable = new ThreadLocal();
/*     */ 
/*     */   public static User getUser()
/*     */   {
/*  45 */     return (User)cmsUserVariable.get();
/*     */   }
/*     */ 
/*     */   public static void setUser(User user)
/*     */   {
/*  54 */     cmsUserVariable.set(user);
/*     */   }
/*     */ 
/*     */   public static void removeUser()
/*     */   {
/*  61 */     cmsUserVariable.remove();
/*     */   }
/*     */ 
/*     */   public static void setAdminUser(ShopAdmin shopAdmin)
/*     */   {
/*  69 */     cmsAdminUserVariable.set(shopAdmin);
/*     */   }
/*     */ 
/*     */   public static ShopAdmin getAdminUser()
/*     */   {
/*  76 */     return (ShopAdmin)cmsAdminUserVariable.get();
/*     */   }
/*     */ 
/*     */   public static Website getSite()
/*     */   {
/*  85 */     return (Website)cmsSiteVariable.get();
/*     */   }
/*     */ 
/*     */   public static void setSite(Website site)
/*     */   {
/*  94 */     cmsSiteVariable.set(site);
/*     */   }
/*     */ 
/*     */   public static void removeSite()
/*     */   {
/* 101 */     cmsSiteVariable.remove();
/*     */   }
/*     */ 
/*     */   public static ApiAccount getApiAccount()
/*     */   {
/* 108 */     return (ApiAccount)apiAccountVariable.get();
/*     */   }
/*     */ 
/*     */   public static void setApiAccount(ApiAccount apiAccount)
/*     */   {
/* 116 */     apiAccountVariable.set(apiAccount);
/*     */   }
/*     */ 
/*     */   public static ApiUserLogin getApiUserLogin()
/*     */   {
/* 123 */     return (ApiUserLogin)apiLogVariable.get();
/*     */   }
/*     */ 
/*     */   public static void setApiUserLogin(ApiUserLogin apiUserLogin)
/*     */   {
/* 131 */     apiLogVariable.set(apiUserLogin);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.CmsThreadVariable
 * JD-Core Version:    0.6.0
 */