/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
/*    */ import org.springframework.web.servlet.view.AbstractUrlBasedView;
/*    */ 
/*    */ public class SimpleFreeMarkerViewResolver extends AbstractTemplateViewResolver
/*    */ {
/*    */   public SimpleFreeMarkerViewResolver()
/*    */   {
/* 20 */     setViewClass(SimpleFreeMarkerView.class);
/*    */   }
/*    */ 
/*    */   protected AbstractUrlBasedView buildView(String viewName)
/*    */     throws Exception
/*    */   {
/* 28 */     AbstractUrlBasedView view = super.buildView(viewName);
/*    */ 
/* 30 */     if (viewName.startsWith("/")) {
/* 31 */       view.setUrl(viewName + getSuffix());
/*    */     }
/* 33 */     return view;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.SimpleFreeMarkerViewResolver
 * JD-Core Version:    0.6.0
 */