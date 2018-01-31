/*    */ package com.jspgou.cms.web;
/*    */ 
/*    */ import com.jspgou.cms.service.LoginSvc;
/*    */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*    */ import com.jspgou.common.security.annotation.Secured;
/*    */ import com.jspgou.core.web.front.FrontHelper;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.beans.factory.InitializingBean;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.util.Assert;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ 
/*    */ public class FrontSecureInterceptor extends HandlerInterceptorAdapter
/*    */   implements InitializingBean
/*    */ {
/*    */   private String loginUrl;
/*    */   private LoginSvc loginSvc;
/*    */ 
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
/*    */     throws Exception
/*    */   {
/* 31 */     request.setAttribute("_login_url", this.loginUrl);
/* 32 */     Secured secured = (Secured)handler.getClass().getAnnotation(Secured.class);
/* 33 */     if (secured != null)
/*    */     {
/* 35 */       if (MemberThread.get() == null)
/*    */       {
/* 37 */         this.loginSvc.clearCookie(request, response);
/* 38 */         response.sendRedirect(FrontHelper.getLoginUrl(this.loginUrl, request
/* 39 */           .getContextPath(), request.getRequestURL().toString()));
/* 40 */         return false;
/*    */       }
/*    */     }
/*    */ 
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   public void afterPropertiesSet() throws Exception
/*    */   {
/* 49 */     Assert.notNull(this.loginUrl);
/*    */   }
/*    */ 
/*    */   public void setLoginUrl(String loginUrl)
/*    */   {
/* 56 */     this.loginUrl = loginUrl;
/*    */   }
/*    */   @Autowired
/*    */   public void setLoginSvc(LoginSvc loginSvc) {
/* 61 */     this.loginSvc = loginSvc;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.FrontSecureInterceptor
 * JD-Core Version:    0.6.0
 */