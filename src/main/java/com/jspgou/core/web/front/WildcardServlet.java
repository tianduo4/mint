/*    */ package com.jspgou.core.web.front;
/*    */ 
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.beans.factory.BeanFactoryUtils;
/*    */ import org.springframework.web.context.WebApplicationContext;
/*    */ import org.springframework.web.context.support.WebApplicationContextUtils;
/*    */ 
/*    */ public class WildcardServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 29 */   public static String DYNAMIC = "jeedynamic";
/* 30 */   public static String URL = "_dynamic_url";
/* 31 */   public static String URI_INFO = "_dynamic_uri_info";
/* 32 */   public static String QUERY_STRING = "_dynamic_query_string";
/* 33 */   private String dynamic = DYNAMIC;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 39 */     String s = request.getServerName();
/* 40 */     Website website = this.websiteMng.getWebsite(s);
/* 41 */     if (website != null) {
/* 42 */       String s1 = request.getRequestURL().toString();
/* 43 */       request.setAttribute(URL, s1);
/*    */ 
/* 45 */       request.setAttribute(QUERY_STRING, request.getQueryString());
/*    */ 
/* 49 */       String s3 = "/" + this.dynamic;
/* 50 */       RequestDispatcher requestdispatcher = request.getRequestDispatcher(s3);
/* 51 */       requestdispatcher.forward(request, response);
/*    */     } else {
/* 53 */       throw new IllegalStateException("no website found");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() throws ServletException
/*    */   {
/* 59 */     WebApplicationContext webapplicationcontext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
/* 60 */     this.websiteMng = ((WebsiteMng)BeanFactoryUtils.beanOfTypeIncludingAncestors(webapplicationcontext, WebsiteMng.class, true, false));
/* 61 */     String s = getServletConfig().getInitParameter("dynamic");
/* 62 */     if (!StringUtils.isBlank(s))
/* 63 */       this.dynamic = s;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.front.WildcardServlet
 * JD-Core Version:    0.6.0
 */