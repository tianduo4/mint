/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
/*    */ import org.springframework.web.servlet.view.AbstractUrlBasedView;
/*    */ 
/*    */ public class ApiFreeMarkerViewResolver extends AbstractTemplateViewResolver
/*    */ {
/*    */   public ApiFreeMarkerViewResolver()
/*    */   {
/* 21 */     setViewClass(ApiFreeMarkerView.class);
/*    */   }
/*    */ 
/*    */   protected AbstractUrlBasedView buildView(String viewName)
/*    */     throws Exception
/*    */   {
/* 29 */     AbstractUrlBasedView view = super.buildView(viewName);
/*    */ 
/* 31 */     if (viewName.startsWith("/")) {
/* 32 */       view.setUrl(viewName + getSuffix());
/*    */     }
/* 34 */     return view;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.ApiFreeMarkerViewResolver
 * JD-Core Version:    0.6.0
 */