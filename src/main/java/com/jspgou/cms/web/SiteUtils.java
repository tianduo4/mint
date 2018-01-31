/*    */ package com.jspgou.cms.web;
/*    */ 
/*    */ import com.jspgou.cms.entity.ShopConfig;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class SiteUtils extends com.jspgou.core.web.SiteUtils
/*    */ {
/*    */   public static ShopConfig getConfig(HttpServletRequest request)
/*    */   {
/* 15 */     ShopConfig config = (ShopConfig)request.getAttribute("_shop_config_key");
/* 16 */     if (config == null) {
/* 17 */       throw new IllegalStateException(
/* 18 */         "Config not found in Request Attribute '_shop_config_key'");
/*    */     }
/*    */ 
/* 21 */     return config;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.SiteUtils
 * JD-Core Version:    0.6.0
 */