/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import java.util.Date;
/*    */ import org.springframework.beans.propertyeditors.StringTrimmerEditor;
/*    */ import org.springframework.web.bind.WebDataBinder;
/*    */ import org.springframework.web.bind.support.WebBindingInitializer;
/*    */ import org.springframework.web.context.request.WebRequest;
/*    */ 
/*    */ public class BindingInitializer
/*    */   implements WebBindingInitializer
/*    */ {
/*    */   public void initBinder(WebDataBinder binder, WebRequest request)
/*    */   {
/* 22 */     binder.registerCustomEditor(Date.class, new DateTypeEditor());
/* 23 */     binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.BindingInitializer
 * JD-Core Version:    0.6.0
 */