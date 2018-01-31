/*    */ package com.jspgou.core.security;
/*    */ 
/*    */ import com.jspgou.common.web.CookieUtils;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.shiro.subject.Subject;
/*    */ import org.apache.shiro.web.filter.authc.LogoutFilter;
/*    */ 
/*    */ public class CmsLogoutFilter extends LogoutFilter
/*    */ {
/*    */   public static final String RETURN_URL = "returnUrl";
/*    */   public static final String USER_LOG_OUT_FLAG = "logout";
/*    */   private String adminPrefix;
/*    */   private String adminLogin;
/*    */ 
/*    */   protected String getRedirectUrl(ServletRequest req, ServletResponse resp, Subject subject)
/*    */   {
/* 28 */     HttpServletRequest request = (HttpServletRequest)req;
/* 29 */     HttpServletResponse response = (HttpServletResponse)resp;
/* 30 */     String redirectUrl = request.getParameter("returnUrl");
/* 31 */     String domain = request.getServerName();
/* 32 */     if (domain.indexOf(".") > -1) {
/* 33 */       domain = domain.substring(domain.indexOf(".") + 1);
/*    */     }
/* 35 */     CookieUtils.addCookie(request, response, "JSESSIONID", null, Integer.valueOf(0), domain, "/");
/* 36 */     CookieUtils.addCookie(request, response, "sso_logout", "true", null, domain, "/");
/* 37 */     if (StringUtils.isBlank(redirectUrl)) {
/* 38 */       if (request.getRequestURI().startsWith(request.getContextPath() + getAdminPrefix()))
/* 39 */         redirectUrl = getAdminLogin();
/*    */       else {
/* 41 */         redirectUrl = getRedirectUrl();
/*    */       }
/*    */     }
/* 44 */     return redirectUrl;
/*    */   }
/*    */ 
/*    */   public String getAdminPrefix()
/*    */   {
/* 49 */     return this.adminPrefix;
/*    */   }
/*    */ 
/*    */   public void setAdminPrefix(String adminPrefix) {
/* 53 */     this.adminPrefix = adminPrefix;
/*    */   }
/*    */ 
/*    */   public String getAdminLogin() {
/* 57 */     return this.adminLogin;
/*    */   }
/*    */ 
/*    */   public void setAdminLogin(String adminLogin) {
/* 61 */     this.adminLogin = adminLogin;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.security.CmsLogoutFilter
 * JD-Core Version:    0.6.0
 */