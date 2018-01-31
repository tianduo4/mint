/*    */ package com.jspgou.core.web;
/*    */ 
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.beans.factory.BeanFactoryUtils;
/*    */ import org.springframework.web.context.WebApplicationContext;
/*    */ import org.springframework.web.context.support.WebApplicationContextUtils;
/*    */ 
/*    */ public class WebsiteFilter
/*    */   implements Filter
/*    */ {
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
/*    */     throws IOException, ServletException
/*    */   {
/* 32 */     HttpServletRequest request = (HttpServletRequest)servletrequest;
/* 33 */     HttpServletResponse response = (HttpServletResponse)servletresponse;
/* 34 */     String s = request.getServerName();
/* 35 */     Website website = this.websiteMng.getWebsite(s);
/* 36 */     if (website != null) {
/* 37 */       request.setAttribute("_web_key", website);
/* 38 */       String s1 = website.getBaseDomain();
/* 39 */       if (!StringUtils.isBlank(s1)) {
/* 40 */         request.setAttribute("_base_domain_key", s1);
/*    */       }
/* 42 */       filterchain.doFilter(request, response);
/*    */     } else {
/* 44 */       response.sendError(404, "domain '" + s + "' not found.");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig filterconfig) throws ServletException
/*    */   {
/* 50 */     WebApplicationContext webapplicationcontext = WebApplicationContextUtils.getWebApplicationContext(filterconfig.getServletContext());
/* 51 */     this.websiteMng = ((WebsiteMng)BeanFactoryUtils.beanOfTypeIncludingAncestors(webapplicationcontext, WebsiteMng.class, true, false));
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.WebsiteFilter
 * JD-Core Version:    0.6.0
 */