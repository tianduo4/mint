/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import com.jspgou.cms.web.AdminApiInterceptor;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.web.servlet.config.annotation.CorsRegistration;
/*    */ import org.springframework.web.servlet.config.annotation.CorsRegistry;
/*    */ import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/*    */ import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
/*    */ import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/*    */ 
/*    */ @Configuration
/*    */ @EnableWebMvc
/*    */ public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private AdminApiInterceptor adminApiInterceptor;
/*    */ 
/*    */   public void addCorsMappings(CorsRegistry registry)
/*    */   {
/* 24 */     CorsRegistration corsRe = registry.addMapping("/**");
/* 25 */     corsRe.allowedOrigins(new String[] { "*" }).allowedHeaders(new String[] { "*" })
/* 26 */       .allowedMethods(new String[] { 
/* 26 */       "GET", "POST", "DELETE", "PUT", "OPTIONS" });
/*    */   }
/*    */ 
/*    */   public void addInterceptors(InterceptorRegistry registry)
/*    */   {
/* 32 */     registry.addInterceptor(this.adminApiInterceptor);
/* 33 */     super.addInterceptors(registry);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.CorsConfigurerAdapter
 * JD-Core Version:    0.6.0
 */