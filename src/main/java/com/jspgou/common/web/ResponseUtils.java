/*    */ package com.jspgou.common.web;
/*    */ 
/*    */ import com.jspgou.cms.api.ApiResponse;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public final class ResponseUtils
/*    */ {
/* 20 */   public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);
/*    */ 
/*    */   public static void renderJson(HttpServletResponse response, String text)
/*    */   {
/* 31 */     render(response, "application/json;charset=UTF-8", text);
/*    */   }
/*    */ 
/*    */   public static void renderText(HttpServletResponse response, String text)
/*    */   {
/* 44 */     render(response, "text/plain;charset=UTF-8", text);
/*    */   }
/*    */ 
/*    */   public static void render(HttpServletResponse response, String contentType, String text)
/*    */   {
/* 55 */     response.setContentType(contentType);
/* 56 */     response.setHeader("Pragma", "No-cache");
/* 57 */     response.setHeader("Cache-Control", "no-cache");
/* 58 */     response.setDateHeader("Expires", 0L);
/* 59 */     response.setHeader("Access-Control-Allow-Origin", "*");
/* 60 */     response.setHeader("access-control-allow-methods", "POST, GET,HEAD, OPTIONS,PATCH, DELETE");
/* 61 */     response.setHeader("Access-Control-Max-Age", "3600");
/* 62 */     response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
/* 63 */     response.setHeader("access-control-allow-credentials", "true");
/*    */     try {
/* 65 */       response.getWriter().write(text);
/*    */     } catch (IOException e) {
/* 67 */       log.error(e.getMessage(), e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void renderApiJson(HttpServletResponse response, HttpServletRequest request, ApiResponse apiResult) {
/* 72 */     response.setCharacterEncoding("UTF-8");
/* 73 */     renderJson(response, apiResult.toString());
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.ResponseUtils
 * JD-Core Version:    0.6.0
 */