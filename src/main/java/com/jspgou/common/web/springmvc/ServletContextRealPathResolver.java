/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import javax.servlet.ServletContext;
/*    */ import org.springframework.stereotype.Component;
/*    */ import org.springframework.web.context.ServletContextAware;
/*    */ 
/*    */ @Component
/*    */ public class ServletContextRealPathResolver
/*    */   implements RealPathResolver, ServletContextAware
/*    */ {
/*    */   private ServletContext context;
/*    */ 
/*    */   public String get(String path)
/*    */   {
/* 17 */     return this.context.getRealPath(path);
/*    */   }
/*    */ 
/*    */   public void setServletContext(ServletContext servletContext)
/*    */   {
/* 22 */     this.context = servletContext;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.ServletContextRealPathResolver
 * JD-Core Version:    0.6.0
 */