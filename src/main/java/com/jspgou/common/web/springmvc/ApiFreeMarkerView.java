/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
/*    */ 
/*    */ public class ApiFreeMarkerView extends FreeMarkerView
/*    */ {
/*    */   public static final String CONTEXT_PATH = "base";
/*    */   public static final String SSO_ENABLE = "ssoEnable";
/*    */   public static final String USER = "user";
/*    */ 
/*    */   protected void exposeHelpers(Map model, HttpServletRequest request)
/*    */     throws Exception
/*    */   {
/* 39 */     super.exposeHelpers(model, request);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.ApiFreeMarkerView
 * JD-Core Version:    0.6.0
 */