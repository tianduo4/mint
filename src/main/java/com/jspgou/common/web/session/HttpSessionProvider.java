/*    */ package com.jspgou.common.web.session;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class HttpSessionProvider
/*    */   implements SessionProvider
/*    */ {
/*    */   public Serializable getAttribute(HttpServletRequest request, String name)
/*    */   {
/* 13 */     HttpSession session = request.getSession(false);
/* 14 */     if (session != null) {
/* 15 */       return (Serializable)session.getAttribute(name);
/*    */     }
/* 17 */     return null;
/*    */   }
/*    */ 
/*    */   public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value)
/*    */   {
/* 24 */     HttpSession httpsession = request.getSession();
/* 25 */     httpsession.setAttribute(name, value);
/*    */   }
/*    */ 
/*    */   public String getSessionId(HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 31 */     return request.getSession().getId();
/*    */   }
/*    */ 
/*    */   public void logout(HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 36 */     HttpSession session = request.getSession(false);
/* 37 */     if (session != null)
/* 38 */       session.invalidate();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.session.HttpSessionProvider
 * JD-Core Version:    0.6.0
 */