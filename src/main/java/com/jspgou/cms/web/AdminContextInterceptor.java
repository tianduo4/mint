/*     */ package com.jspgou.cms.web;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.manager.ShopAdminMng;
/*     */ import com.jspgou.cms.service.LoginSvc;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.shiro.SecurityUtils;
/*     */ import org.apache.shiro.subject.Subject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.servlet.ModelAndView;
/*     */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*     */ import org.springframework.web.util.UrlPathHelper;
/*     */ 
/*     */ public class AdminContextInterceptor extends HandlerInterceptorAdapter
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private LoginSvc loginSvc;
/*     */ 
/*     */   @Autowired
/*     */   private ShopAdminMng shopAdminMng;
/*     */   private Long developAdminId;
/*     */   private String[] excludeUrls;
/*     */ 
/*     */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
/*     */     throws Exception
/*     */   {
/*  42 */     Website web = SiteUtils.getWeb(request);
/*  43 */     ShopAdmin admin = null;
/*  44 */     if (this.developAdminId != null)
/*     */     {
/*  46 */       admin = this.shopAdminMng.findById(this.developAdminId);
/*     */ 
/*  48 */       if (admin == null) {
/*  49 */         throw new IllegalStateException("developAdminId not found: " + this.developAdminId);
/*     */       }
/*  51 */       Long webId = admin.getWebsite().getId();
/*     */ 
/*  53 */       if (!webId.equals(web.getId())) {
/*  54 */         throw new IllegalStateException("developAdminId's website id=" + 
/*  55 */           webId + " not in current website id=" + web.getId());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  60 */       Subject subject = SecurityUtils.getSubject();
/*  61 */       if (subject != null) {
/*  62 */         String username = (String)subject.getPrincipal();
/*  63 */         if (subject.isAuthenticated())
/*     */         {
/*  66 */           admin = this.shopAdminMng.getByUsername(username);
/*     */ 
/*  68 */           AdminThread.set(admin);
/*     */         }
/*     */       }
/*     */     }
/*  72 */     String uri = getURI(request);
/*  73 */     if (exclude(uri)) {
/*  74 */       return true;
/*     */     }
/*  76 */     if (admin != null) {
/*  77 */       boolean viewonly = admin.getViewonlyAdmin().booleanValue();
/*     */ 
/*  80 */       Set perms = admin.getPerms();
/*  81 */       request.setAttribute("_permission_key", perms);
/*  82 */       if ((viewonly) && 
/*  83 */         (permistionPass(uri))) {
/*  84 */         request.setAttribute("message", MessageResolver.getMessage(request, "login.notPermission", new Object[0]));
/*  85 */         response.sendError(403);
/*  86 */         return false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
/*     */     throws Exception
/*     */   {
/* 101 */     ShopAdmin admin = AdminThread.get();
/* 102 */     if ((admin != null) && (mav != null) && (mav.getModelMap() != null) && 
/* 103 */       (mav.getViewName() != null) && 
/* 104 */       (!mav.getViewName().startsWith("redirect:")))
/* 105 */       mav.getModelMap().addAttribute("_permission_key", 
/* 106 */         admin.getPerms());
/*     */   }
/*     */ 
/*     */   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
/*     */     throws Exception
/*     */   {
/* 114 */     AdminThread.remove();
/*     */   }
/*     */ 
/*     */   private boolean exclude(String uri) {
/* 118 */     if (this.excludeUrls != null) {
/* 119 */       for (String exc : this.excludeUrls) {
/* 120 */         if (exc.equals(uri)) {
/* 121 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean permistionPass(String uri)
/*     */   {
/* 130 */     String u = null;
/*     */ 
/* 132 */     int i = uri.lastIndexOf("/");
/* 133 */     if (i == -1) {
/* 134 */       throw new RuntimeException("uri must start width '/':" + uri);
/*     */     }
/* 136 */     u = uri.substring(i + 1);
/*     */ 
/* 139 */     return u.startsWith("o_");
/*     */   }
/*     */ 
/*     */   private static String getURI(HttpServletRequest request)
/*     */     throws IllegalStateException
/*     */   {
/* 153 */     UrlPathHelper helper = new UrlPathHelper();
/* 154 */     String uri = helper.getOriginatingRequestUri(request);
/* 155 */     String ctxPath = helper.getOriginatingContextPath(request);
/* 156 */     int start = 0; int i = 0; int count = 2;
/* 157 */     if (!StringUtils.isBlank(ctxPath)) {
/* 158 */       count++;
/*     */     }
/* 160 */     while ((i < count) && (start != -1)) {
/* 161 */       start = uri.indexOf('/', start + 1);
/* 162 */       i++;
/*     */     }
/* 164 */     if (start <= 0) {
/* 165 */       throw new IllegalStateException(
/* 166 */         "admin access path not like '/jeeadmin/jspgou/...' pattern: " + 
/* 167 */         uri);
/*     */     }
/* 169 */     return uri.substring(start);
/*     */   }
/*     */ 
/*     */   public void setExcludeUrls(String[] excludeUrls)
/*     */   {
/* 181 */     this.excludeUrls = excludeUrls;
/*     */   }
/*     */ 
/*     */   public void setDevelopAdminId(Long developAdminId)
/*     */   {
/* 187 */     this.developAdminId = developAdminId;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.AdminContextInterceptor
 * JD-Core Version:    0.6.0
 */