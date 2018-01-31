/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import com.jspgou.cms.api.ApiResponse;
/*    */ import com.jspgou.common.web.ResponseUtils;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.beans.TypeMismatchException;
/*    */ import org.springframework.dao.DataIntegrityViolationException;
/*    */ import org.springframework.jdbc.BadSqlGrammarException;
/*    */ import org.springframework.validation.BindException;
/*    */ import org.springframework.web.bind.MissingServletRequestParameterException;
/*    */ import org.springframework.web.bind.ServletRequestBindingException;
/*    */ import org.springframework.web.servlet.HandlerExceptionResolver;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ 
/*    */ public class HandlerApiExceptionResolver
/*    */   implements HandlerExceptionResolver
/*    */ {
/*    */   public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
/*    */   {
/* 27 */     e.printStackTrace();
/* 28 */     int code = 100;
/* 29 */     String body = "\"\"";
/* 30 */     String message = "\"system exception\"";
/* 31 */     Class exceptionClass = e.getClass();
/* 32 */     if (exceptionClass.equals(BindException.class)) {
/* 33 */       code = 202;
/* 34 */       message = "\"param error\"";
/* 35 */     } else if (exceptionClass.equals(MissingServletRequestParameterException.class)) {
/* 36 */       code = 201;
/* 37 */       message = "\"param required\"";
/* 38 */     } else if (exceptionClass.equals(TypeMismatchException.class)) {
/* 39 */       code = 202;
/* 40 */       message = "\"param error\"";
/* 41 */     } else if (exceptionClass.equals(ServletRequestBindingException.class)) {
/* 42 */       code = 202;
/* 43 */       message = "\"param error\"";
/* 44 */     } else if (exceptionClass.equals(DataIntegrityViolationException.class)) {
/* 45 */       code = 202;
/* 46 */       message = "\"param error\"";
/* 47 */     } else if (exceptionClass.equals(BadSqlGrammarException.class)) {
/* 48 */       code = 202;
/* 49 */       message = "\"param error\"";
/*    */     }
/* 51 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 52 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/* 53 */     ModelAndView modelAndView = new ModelAndView();
/* 54 */     return modelAndView;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.HandlerApiExceptionResolver
 * JD-Core Version:    0.6.0
 */