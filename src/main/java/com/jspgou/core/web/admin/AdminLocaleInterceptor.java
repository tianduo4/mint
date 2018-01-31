/*    */ package com.jspgou.core.web.admin;
/*    */ 
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import java.util.Locale;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.beans.propertyeditors.LocaleEditor;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.servlet.LocaleResolver;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ import org.springframework.web.servlet.support.RequestContextUtils;
/*    */ 
/*    */ public class AdminLocaleInterceptor extends HandlerInterceptorAdapter
/*    */ {
/*    */   public static final String LOCALE = "locale";
/*    */ 
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
/*    */     throws ServletException
/*    */   {
/* 25 */     LocaleResolver localeresolver = RequestContextUtils.getLocaleResolver(request);
/* 26 */     if (localeresolver == null) {
/* 27 */       throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
/*    */     }
/* 29 */     Website website = SiteUtils.getWeb(request);
/* 30 */     String s = website.getLocaleAdmin();
/* 31 */     LocaleEditor localeeditor = new LocaleEditor();
/* 32 */     localeeditor.setAsText(s);
/* 33 */     localeresolver.setLocale(request, response, (Locale)localeeditor.getValue());
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView modelandview)
/*    */     throws Exception
/*    */   {
/* 41 */     LocaleResolver localeresolver = RequestContextUtils.getLocaleResolver(request);
/* 42 */     if (localeresolver == null) {
/* 43 */       throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
/*    */     }
/* 45 */     if (modelandview != null)
/* 46 */       modelandview.getModelMap().addAttribute("locale", localeresolver.resolveLocale(request).toString());
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.admin.AdminLocaleInterceptor
 * JD-Core Version:    0.6.0
 */