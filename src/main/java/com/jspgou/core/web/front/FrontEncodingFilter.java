/*    */ package com.jspgou.core.web.front;
/*    */ 
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.web.SiteUtils;
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
/*    */ public class FrontEncodingFilter
/*    */   implements Filter
/*    */ {
/* 16 */   private static final Logger log = LoggerFactory.getLogger(FrontEncodingFilter.class);
/*    */   public static final String AJAX_HEAD = "isAjax";
/*    */ 
/*    */   public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
/*    */     throws IOException, ServletException
/*    */   {
/* 22 */     HttpServletRequest httpservletrequest = (HttpServletRequest)servletrequest;
/* 23 */     Website website = SiteUtils.getWeb(httpservletrequest);
/* 24 */     String s = httpservletrequest.getHeader("isAjax");
/* 25 */     if ((s != null) && ("true".equals(s))) {
/* 26 */       httpservletrequest.setCharacterEncoding("UTF-8");
/* 27 */       log.debug("ajax request");
/*    */     } else {
/* 29 */       httpservletrequest.setCharacterEncoding(website.getFrontEncoding());
/* 30 */       servletresponse.setContentType(website.getFrontContentType());
/*    */     }
/* 32 */     filterchain.doFilter(httpservletrequest, servletresponse);
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig filterconfig)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.front.FrontEncodingFilter
 * JD-Core Version:    0.6.0
 */