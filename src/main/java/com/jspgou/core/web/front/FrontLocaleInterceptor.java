/*    */ package com.jspgou.core.web.front;
/*    */ 
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import java.util.Locale;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.beans.propertyeditors.LocaleEditor;
/*    */ import org.springframework.web.servlet.LocaleResolver;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ import org.springframework.web.servlet.support.RequestContextUtils;
/*    */ 
/*    */ public class FrontLocaleInterceptor extends HandlerInterceptorAdapter
/*    */ {
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
/*    */     throws ServletException
/*    */   {
/* 22 */     LocaleResolver localeresolver = RequestContextUtils.getLocaleResolver(request);
/* 23 */     if (localeresolver == null) {
/* 24 */       throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
/*    */     }
/* 26 */     Website website = SiteUtils.getWeb(request);
/* 27 */     String s = website.getLocaleFront();
/* 28 */     LocaleEditor localeeditor = new LocaleEditor();
/* 29 */     localeeditor.setAsText(s);
/* 30 */     localeresolver.setLocale(request, response, (Locale)localeeditor.getValue());
/* 31 */     return true;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.front.FrontLocaleInterceptor
 * JD-Core Version:    0.6.0
 */