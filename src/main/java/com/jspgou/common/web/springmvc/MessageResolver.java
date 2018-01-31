/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.web.context.WebApplicationContext;
/*    */ import org.springframework.web.servlet.LocaleResolver;
/*    */ import org.springframework.web.servlet.support.RequestContextUtils;
/*    */ 
/*    */ public final class MessageResolver
/*    */ {
/*    */   public static String getMessage(HttpServletRequest request, String code, Object[] args)
/*    */   {
/* 33 */     WebApplicationContext messageSource = 
/* 34 */       RequestContextUtils.getWebApplicationContext(request);
/* 35 */     if (messageSource == null) {
/* 36 */       throw new IllegalStateException("WebApplicationContext not found!");
/*    */     }
/* 38 */     LocaleResolver localeResolver = 
/* 39 */       RequestContextUtils.getLocaleResolver(request);
/*    */     Locale locale;
/*    */     Locale locale;
/* 41 */     if (localeResolver != null)
/* 42 */       locale = localeResolver.resolveLocale(request);
/*    */     else {
/* 44 */       locale = request.getLocale();
/*    */     }
/* 46 */     return messageSource.getMessage(code, args, locale);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.MessageResolver
 * JD-Core Version:    0.6.0
 */