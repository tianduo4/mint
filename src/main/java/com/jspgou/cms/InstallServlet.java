/*    */ package com.jspgou.cms;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class InstallServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   protected void doPost(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 18 */     String dbHost = request.getParameter("dbHost");
/* 19 */     String dbPort = request.getParameter("dbPort");
/* 20 */     String dbName = request.getParameter("dbName");
/* 21 */     String dbUser = request.getParameter("dbUser");
/* 22 */     String dbPassword = request.getParameter("dbPassword");
/*    */ 
/* 24 */     String isCreateDb = request.getParameter("isCreateDb");
/* 25 */     String isCreateTable = request.getParameter("isCreateTable");
/* 26 */     String isInitData = request.getParameter("isInitData");
/* 27 */     String domain = request.getParameter("domain");
/* 28 */     String cxtPath = request.getParameter("cxtPath");
/* 29 */     String port = request.getParameter("port");
/*    */ 
/* 31 */     String dbFileName = request.getParameter("dbFileName");
/* 32 */     String initFileName = request.getParameter("initFileName");
/* 33 */     String dbXmlFileName = "/WEB-INF/config/jdbc.properties";
/* 34 */     String webXmlFrom = "/install/config/web.xml";
/* 35 */     String webXmlTo = "/WEB-INF/web.xml";
/*    */     try
/*    */     {
/* 38 */       if ("true".equals(isCreateDb))
/* 39 */         Install.createDb(dbHost, dbPort, dbName, dbUser, dbPassword);
/*    */       else {
/* 41 */         Install.changeDbCharset(dbHost, dbPort, dbName, dbUser, 
/* 42 */           dbPassword);
/*    */       }
/*    */ 
/* 45 */       if ("true".equals(isCreateTable)) {
/* 46 */         String sqlPath = getServletContext().getRealPath(dbFileName);
/* 47 */         List sqlList = Install.readSql(sqlPath);
/* 48 */         Install.createTable(dbHost, dbPort, dbName, dbUser, dbPassword, 
/* 49 */           sqlList);
/*    */       }
/*    */ 
/* 52 */       if ("true".equals(isInitData)) {
/* 53 */         String initPath = getServletContext().getRealPath(initFileName);
/* 54 */         List initList = Install.readSql(initPath);
/* 55 */         Install.createTable(dbHost, dbPort, dbName, dbUser, dbPassword, 
/* 56 */           initList);
/*    */       }
/*    */ 
/* 59 */       Install.updateConfig(dbHost, dbPort, dbName, dbUser, dbPassword, 
/* 60 */         domain, cxtPath, port);
/*    */ 
/* 62 */       String dbXmlPath = getServletContext().getRealPath(dbXmlFileName);
/*    */ 
/* 64 */       Install.dbXml(dbXmlPath, dbHost, dbPort, dbName, dbUser, 
/* 65 */         dbPassword);
/*    */ 
/* 67 */       String webXmlFromPath = getServletContext().getRealPath(webXmlFrom);
/* 68 */       String webXmlToPath = getServletContext().getRealPath(webXmlTo);
/* 69 */       Install.webXml(webXmlFromPath, webXmlToPath);
/*    */     } catch (Exception e) {
/* 71 */       throw new ServletException("install failed!", e);
/*    */     }
/* 73 */     RequestDispatcher dispatcher = request
/* 74 */       .getRequestDispatcher("/install/install_setup.jsp");
/* 75 */     dispatcher.forward(request, response);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.InstallServlet
 * JD-Core Version:    0.6.0
 */