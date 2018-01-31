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
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class ProcessTimeFilter
/*    */   implements Filter
/*    */ {
/* 15 */   protected final Logger log = LoggerFactory.getLogger(ProcessTimeFilter.class);
/*    */   public static final String START_TIME = "_start_time";
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 28 */     HttpServletRequest request = (HttpServletRequest)req;
/* 29 */     long time = System.currentTimeMillis();
/* 30 */     request.setAttribute("_start_time", Long.valueOf(time));
/* 31 */     chain.doFilter(request, response);
/* 32 */     time = System.currentTimeMillis() - time;
/* 33 */     this.log.debug("process in {} ms: {}", Long.valueOf(time), request.getRequestURI());
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig arg0)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.ProcessTimeFilter
 * JD-Core Version:    0.6.0
 */