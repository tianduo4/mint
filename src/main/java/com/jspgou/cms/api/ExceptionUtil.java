/*    */ package com.jspgou.cms.api;
/*    */ 
/*    */ import com.jspgou.common.util.PropertyUtils;
/*    */ import com.jspgou.common.web.ResponseUtils;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.sql.BatchUpdateException;
/*    */ import java.sql.SQLException;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.hibernate.exception.ConstraintViolationException;
/*    */ 
/*    */ public class ExceptionUtil
/*    */ {
/* 16 */   public static String JSPGOU_JDBC_CONFIG = "/WEB-INF/config/jdbc.properties";
/* 17 */   public static String MES_CONFIG = "/WEB-INF/languages/jspgou_api/messages_zh_CN.properties";
/*    */ 
/*    */   public static void convertException(HttpServletResponse response, HttpServletRequest request, Exception e) {
/* 20 */     ApiResponse apiResponse = new ApiResponse("\"\"", "\"system exception\"", 100);
/* 21 */     Throwable th = e.getCause();
/* 22 */     String errMsg = "";
/*    */ 
/* 24 */     if ((th instanceof ConstraintViolationException))
/* 25 */       errMsg = ((ConstraintViolationException)th).getSQLException().getMessage();
/* 26 */     else if ((th instanceof BatchUpdateException)) {
/* 27 */       errMsg = ((BatchUpdateException)th).getMessage();
/*    */     }
/* 29 */     System.out.println("exception:" + errMsg);
/* 30 */     String path = request.getServletContext().getRealPath(JSPGOU_JDBC_CONFIG);
/* 31 */     String driverName = PropertyUtils.getPropertyValue(new File(path), "jdbc.driverClassName");
/* 32 */     if ((StringUtils.isNotEmpty(driverName)) && (StringUtils.isNotEmpty(errMsg)))
/*    */     {
/* 34 */       if (driverName.indexOf("mysql") >= 0)
/*    */       {
/* 36 */         String checKey = errMsg.substring(errMsg.indexOf(" CONSTRAINT `") + 13, errMsg.indexOf("` FOREIGN KEY"));
/* 37 */         System.out.println(checKey);
/*    */ 
/* 39 */         String zhMes = PropertyUtils.getPropertyValue(new File(request.getServletContext().getRealPath(MES_CONFIG)), checKey);
/* 40 */         apiResponse.setMessage("\"delete error\"");
/* 41 */         apiResponse.setCode(205);
/* 42 */         if (StringUtils.isEmpty(zhMes)) {
/* 43 */           zhMes = errMsg;
/*    */         }
/* 45 */         apiResponse.setBody("\"\",\"fail\":\"" + zhMes + "\""); } else {
/* 46 */         driverName.indexOf("oracle");
/*    */       }
/*    */     }
/*    */ 
/* 50 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.ExceptionUtil
 * JD-Core Version:    0.6.0
 */