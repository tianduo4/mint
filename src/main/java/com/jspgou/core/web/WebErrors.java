/*    */ package com.jspgou.core.web;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.MessageSource;
/*    */ 
/*    */ public class WebErrors extends com.jspgou.common.web.springmvc.WebErrors
/*    */ {
/*    */   public static final String ERROR_PAGE = "/common/error_message";
/*    */   public static final String ERROR_ATTR_NAME = "errors";
/*    */ 
/*    */   public static WebErrors create(HttpServletRequest request)
/*    */   {
/* 17 */     return new WebErrors(request);
/*    */   }
/*    */   public WebErrors() {
/*    */   }
/*    */ 
/*    */   public WebErrors(HttpServletRequest request) {
/* 23 */     super(request);
/*    */   }
/*    */ 
/*    */   public WebErrors(MessageSource messagesource, Locale locale) {
/* 27 */     super(messagesource, locale);
/*    */   }
/*    */ 
/*    */   public void notInWeb(Class class1, Serializable serializable)
/*    */   {
/* 32 */     addErrorCode("error.notInWeb", new Object[] { 
/* 33 */       class1.getSimpleName(), serializable });
/*    */   }
/*    */ 
/*    */   protected String getErrorAttrName()
/*    */   {
/* 39 */     return "errors";
/*    */   }
/*    */ 
/*    */   protected String getErrorPage()
/*    */   {
/* 44 */     return "/common/error_message";
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.WebErrors
 * JD-Core Version:    0.6.0
 */