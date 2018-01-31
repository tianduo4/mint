/*    */ package com.jspgou.common.update;
/*    */ 
/*    */ import com.jspgou.cms.manager.UpdateMng;
/*    */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.Statement;
/*    */ import java.util.Properties;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import org.springframework.web.context.WebApplicationContext;
/*    */ import org.springframework.web.context.support.WebApplicationContextUtils;
/*    */ 
/*    */ public class UpdateServlet extends HttpServlet
/*    */ {
/*    */   public void init()
/*    */   {
/* 28 */     Install();
/*    */   }
/*    */ 
/*    */   public void Install() {
/* 32 */     ServletContext sc = getServletContext();
/* 33 */     WebApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
/* 34 */     RealPathResolver realPathResolver = (RealPathResolver)ac.getBean("realPathResolver");
/* 35 */     UpdateMng updateMng = (UpdateMng)ac.getBean("updateMng");
/* 36 */     updateMng.update();
/* 37 */     String dbXmlFileName = "/WEB-INF/config/jdbc.properties";
/*    */     try
/*    */     {
/* 41 */       InputStream in = new FileInputStream(realPathResolver.get(dbXmlFileName));
/* 42 */       Properties p = new Properties();
/* 43 */       p.load(in);
/* 44 */       String url = p.getProperty("jdbc.url");
/* 45 */       String[] urls = url.split("[?]");
/* 46 */       String dbUser = p.getProperty("jdbc.username");
/* 47 */       String dbPassword = p.getProperty("jdbc.password");
/* 48 */       createTable(urls[0], dbUser, dbPassword);
/*    */     } catch (FileNotFoundException e) {
/* 50 */       e.printStackTrace();
/*    */     } catch (IOException e) {
/* 52 */       e.printStackTrace();
/*    */     }
/*    */     catch (Exception e) {
/* 55 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void createTable(String url, String dbUser, String dbPassword) throws Exception {
/* 60 */     Connection conn = getConn(url, dbUser, dbPassword);
/* 61 */     Statement stat = conn.createStatement();
/* 62 */     stat.execute("UPDATE jc_core_website SET restart = '0';");
/* 63 */     stat.close();
/* 64 */     conn.close();
/*    */   }
/*    */ 
/*    */   public Connection getConn(String url, String dbUser, String dbPassword) throws Exception
/*    */   {
/* 69 */     Class.forName("com.mysql.jdbc.Driver");
/* 70 */     Class.forName("com.mysql.jdbc.Driver").newInstance();
/* 71 */     String connStr = url + "?user=" + dbUser + "&password=" + dbPassword + "&characterEncoding=utf8";
/* 72 */     Connection conn = DriverManager.getConnection(connStr);
/* 73 */     return conn;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.update.UpdateServlet
 * JD-Core Version:    0.6.0
 */