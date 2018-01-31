/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class CorsFilter
/*    */   implements Filter
/*    */ {
/*    */   public void init(FilterConfig filterConfig)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
/*    */     throws IOException, ServletException
/*    */   {
/* 20 */     HttpServletResponse response = (HttpServletResponse)servletResponse;
/* 21 */     response.setHeader("Access-Control-Allow-Origin", "*");
/* 22 */     response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
/* 23 */     response.setHeader("Access-Control-Max-Age", "3600");
/* 24 */     response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
/*    */ 
/* 32 */     filterChain.doFilter(servletRequest, response);
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.CorsFilter
 * JD-Core Version:    0.6.0
 */