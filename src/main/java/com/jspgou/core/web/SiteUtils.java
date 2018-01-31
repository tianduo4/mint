/*    */ package com.jspgou.core.web;
/*    */ 
/*    */ import com.jspgou.core.entity.Website;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public abstract class SiteUtils
/*    */ {
/*    */   public static Website getWeb(HttpServletRequest request)
/*    */   {
/* 13 */     Website website = (Website)request.getAttribute("_web_key");
/* 14 */     if (website == null) {
/* 15 */       throw new IllegalStateException("Webiste not found in Request Attribute '_web_key'");
/*    */     }
/* 17 */     return website;
/*    */   }
/*    */ 
/*    */   public static Long getWebId(HttpServletRequest request)
/*    */   {
/* 22 */     return getWeb(request).getId();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.SiteUtils
 * JD-Core Version:    0.6.0
 */