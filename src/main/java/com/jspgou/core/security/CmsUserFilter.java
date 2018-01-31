/*    */ package com.jspgou.core.security;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.shiro.web.filter.authc.UserFilter;
/*    */ import org.apache.shiro.web.util.WebUtils;
/*    */ 
/*    */ public class CmsUserFilter extends UserFilter
/*    */ {
/*    */   private String adminPrefix;
/*    */   private String adminLogin;
/*    */ 
/*    */   protected void redirectToLogin(ServletRequest req, ServletResponse resp)
/*    */     throws IOException
/*    */   {
/* 22 */     HttpServletRequest request = (HttpServletRequest)req;
/* 23 */     HttpServletResponse response = (HttpServletResponse)resp;
/*    */     String loginUrl;
/*    */     String loginUrl;
/* 28 */     if (request.getRequestURI().startsWith(request.getContextPath() + getAdminPrefix()))
/* 29 */       loginUrl = getAdminLogin();
/*    */     else {
/* 31 */       loginUrl = getLoginUrl();
/*    */     }
/* 33 */     WebUtils.issueRedirect(request, response, loginUrl);
/*    */   }
/*    */ 
/*    */   public String getAdminPrefix()
/*    */   {
/* 51 */     return this.adminPrefix;
/*    */   }
/*    */ 
/*    */   public void setAdminPrefix(String adminPrefix) {
/* 55 */     this.adminPrefix = adminPrefix;
/*    */   }
/*    */ 
/*    */   public String getAdminLogin() {
/* 59 */     return this.adminLogin;
/*    */   }
/*    */ 
/*    */   public void setAdminLogin(String adminLogin) {
/* 63 */     this.adminLogin = adminLogin;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.security.CmsUserFilter
 * JD-Core Version:    0.6.0
 */