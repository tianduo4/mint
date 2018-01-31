/*    */ package com.jspgou.common.web;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class XssFilter
/*    */   implements Filter
/*    */ {
/*    */   private String filterChar;
/*    */   private String replaceChar;
/*    */   private String splitChar;
/* 26 */   FilterConfig filterConfig = null;
/*    */ 
/*    */   public void init(FilterConfig filterConfig) throws ServletException {
/* 29 */     this.filterChar = filterConfig.getInitParameter("FilterChar");
/* 30 */     this.replaceChar = filterConfig.getInitParameter("ReplaceChar");
/* 31 */     this.splitChar = filterConfig.getInitParameter("SplitChar");
/* 32 */     this.filterConfig = filterConfig;
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 37 */     this.filterConfig = null;
/*    */   }
/*    */ 
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 44 */     chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest)request, this.filterChar, this.replaceChar, this.splitChar), response);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.XssFilter
 * JD-Core Version:    0.6.0
 */