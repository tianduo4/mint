/*    */ package com.jspgou.core.web.admin;
/*    */ 
/*    */ import com.jspgou.cms.entity.ShopAdmin;
/*    */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*    */ import com.jspgou.core.entity.Admin;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ 
/*    */ public class AdminSecureInterceptor extends HandlerInterceptorAdapter
/*    */ {
/* 18 */   private boolean develop = false;
/*    */ 
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
/*    */     throws Exception
/*    */   {
/* 24 */     ShopAdmin admin = AdminThread.get();
/* 25 */     if (this.develop) {
/* 26 */       return true;
/*    */     }
/* 28 */     Set set = (Set)request.getAttribute("_permission_key");
/* 29 */     String s = getURI(request.getRequestURI(), request.getContextPath());
/* 30 */     if (s.equals("/login.do")) {
/* 31 */       return true;
/*    */     }
/* 33 */     if ((admin != null) && (admin.getAdmin().isSuper())) {
/* 34 */       return true;
/*    */     }
/* 36 */     if (set == null)
/*    */     {
/* 38 */       return false;
/*    */     }
/* 40 */     Iterator iterator = set.iterator();
/* 41 */     while (iterator.hasNext()) {
/* 42 */       String str2 = (String)iterator.next();
/* 43 */       if ((s.equals(str2)) || (s.startsWith(str2)))
/* 44 */         return true;
/*    */     }
/* 46 */     response.sendError(403);
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   public static String getURI(String s, String s1) throws IllegalStateException
/*    */   {
/* 52 */     int i = 0;
/* 53 */     int j = 0;
/* 54 */     int k = 2;
/* 55 */     if (!StringUtils.isBlank(s1)) {
/* 56 */       k++;
/*    */     }
/* 58 */     for (; (j < k) && (i != -1); j++) {
/* 59 */       i = s.indexOf('/', i + 1);
/*    */     }
/* 61 */     if (i <= 0) {
/* 62 */       throw new IllegalStateException("admin access path not like '/jeeadmin/jspgou/...' pattern: " + s);
/*    */     }
/* 64 */     return s.substring(i);
/*    */   }
/*    */ 
/*    */   public void setDevelop(boolean develop)
/*    */   {
/* 85 */     this.develop = develop;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.admin.AdminSecureInterceptor
 * JD-Core Version:    0.6.0
 */