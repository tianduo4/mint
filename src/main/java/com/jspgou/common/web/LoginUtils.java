/*    */ package com.jspgou.common.web;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.shiro.SecurityUtils;
/*    */ import org.apache.shiro.subject.PrincipalCollection;
/*    */ import org.apache.shiro.subject.SimplePrincipalCollection;
/*    */ import org.apache.shiro.subject.Subject;
/*    */ import org.apache.shiro.util.ThreadContext;
/*    */ import org.apache.shiro.web.subject.WebSubject;
/*    */ import org.apache.shiro.web.subject.WebSubject.Builder;
/*    */ 
/*    */ public class LoginUtils
/*    */ {
/*    */   public static void loginShiro(HttpServletRequest request, HttpServletResponse response, String username)
/*    */   {
/* 16 */     PrincipalCollection principals = new SimplePrincipalCollection(username, username);
/* 17 */     WebSubject.Builder builder = new WebSubject.Builder(request, response);
/* 18 */     builder.principals(principals);
/* 19 */     builder.authenticated(true);
/* 20 */     WebSubject subject = builder.buildWebSubject();
/* 21 */     ThreadContext.bind(subject);
/*    */   }
/*    */ 
/*    */   public static void logout() {
/* 25 */     Subject subject = SecurityUtils.getSubject();
/* 26 */     subject.logout();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.LoginUtils
 * JD-Core Version:    0.6.0
 */