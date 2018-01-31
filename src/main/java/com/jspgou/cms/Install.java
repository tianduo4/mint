/*     */ package com.jspgou.cms;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class Install
/*     */ {
/*     */   public static void dbXml(String fileName, String dbHost, String dbPort, String dbName, String dbUser, String dbPassword)
/*     */     throws Exception
/*     */   {
/*  24 */     String s = FileUtils.readFileToString(new File(fileName));
/*  25 */     s = StringUtils.replace(s, "DB_HOST", dbHost);
/*  26 */     s = StringUtils.replace(s, "DB_PORT", dbPort);
/*  27 */     s = StringUtils.replace(s, "DB_NAME", dbName);
/*  28 */     s = StringUtils.replace(s, "DB_USER", dbUser);
/*  29 */     s = StringUtils.replace(s, "DB_PASSWORD", dbPassword);
/*  30 */     FileUtils.writeStringToFile(new File(fileName), s);
/*     */   }
/*     */ 
/*     */   public static Connection getConn(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword) throws Exception
/*     */   {
/*  35 */     Class.forName("com.mysql.jdbc.Driver");
/*  36 */     Class.forName("com.mysql.jdbc.Driver").newInstance();
/*  37 */     String connStr = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + 
/*  38 */       "?user=" + dbUser + "&password=" + dbPassword + 
/*  39 */       "&characterEncoding=utf8";
/*  40 */     Connection conn = DriverManager.getConnection(connStr);
/*  41 */     return conn;
/*     */   }
/*     */ 
/*     */   public static void webXml(String fromFile, String toFile) throws Exception {
/*  45 */     FileUtils.copyFile(new File(fromFile), new File(toFile));
/*     */   }
/*     */ 
/*     */   public static void createDb(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword)
/*     */     throws Exception
/*     */   {
/*  60 */     Class.forName("com.mysql.jdbc.Driver");
/*  61 */     Class.forName("com.mysql.jdbc.Driver").newInstance();
/*  62 */     String connStr = "jdbc:mysql://" + dbHost + ":" + dbPort + "?user=" + 
/*  63 */       dbUser + "&password=" + dbPassword + 
/*  64 */       "&characterEncoding=UTF8";
/*  65 */     Connection conn = DriverManager.getConnection(connStr);
/*  66 */     Statement stat = conn.createStatement();
/*  67 */     String sql = "drop database if exists " + dbName;
/*  68 */     stat.execute(sql);
/*  69 */     sql = "create database " + dbName + " CHARACTER SET UTF8";
/*  70 */     stat.execute(sql);
/*  71 */     stat.close();
/*  72 */     conn.close();
/*     */   }
/*     */ 
/*     */   public static void changeDbCharset(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword) throws Exception
/*     */   {
/*  77 */     Connection conn = getConn(dbHost, dbPort, dbName, dbUser, dbPassword);
/*  78 */     Statement stat = conn.createStatement();
/*  79 */     String sql = "ALTER DATABASE " + dbName + " CHARACTER SET UTF8";
/*  80 */     stat.execute(sql);
/*  81 */     stat.close();
/*  82 */     conn.close();
/*     */   }
/*     */ 
/*     */   public static void createTable(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword, List<String> sqlList)
/*     */     throws Exception
/*     */   {
/*  99 */     Connection conn = getConn(dbHost, dbPort, dbName, dbUser, dbPassword);
/* 100 */     Statement stat = conn.createStatement();
/* 101 */     for (String dllsql : sqlList) {
/* 102 */       System.out.println(dllsql);
/* 103 */       stat.execute(dllsql);
/*     */     }
/* 105 */     stat.close();
/* 106 */     conn.close();
/*     */   }
/*     */ 
/*     */   public static void updateConfig(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword, String domain, String cxtPath, String port)
/*     */     throws Exception
/*     */   {
/* 125 */     Connection conn = getConn(dbHost, dbPort, dbName, dbUser, dbPassword);
/* 126 */     Statement stat = conn.createStatement();
/* 127 */     String sql = "update jc_core_website set domain='" + domain + "'";
/* 128 */     stat.executeUpdate(sql);
/* 129 */     sql = "update jc_core_global set context_path='" + cxtPath + "',port=" + port;
/* 130 */     stat.executeUpdate(sql);
/* 131 */     stat.close();
/* 132 */     conn.close();
/*     */   }
/*     */ 
/*     */   public static List<String> readSql(String fileName)
/*     */     throws Exception
/*     */   {
/* 144 */     BufferedReader br = new BufferedReader(
/* 145 */       new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
/* 146 */     List sqlList = new ArrayList();
/* 147 */     StringBuilder sqlSb = new StringBuilder();
/* 148 */     String s = null;
/* 149 */     while ((s = br.readLine()) != null) {
/* 150 */       if ((s.startsWith("/*")) || (s.startsWith("#")) || 
/* 151 */         (StringUtils.isBlank(s))) {
/*     */         continue;
/*     */       }
/* 154 */       if (s.endsWith(";")) {
/* 155 */         sqlSb.append(s);
/* 156 */         sqlSb.setLength(sqlSb.length() - 1);
/* 157 */         sqlList.add(sqlSb.toString());
/* 158 */         sqlSb.setLength(0);
/*     */       } else {
/* 160 */         sqlSb.append(s);
/*     */       }
/*     */     }
/* 163 */     br.close();
/* 164 */     return sqlList;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.Install
 * JD-Core Version:    0.6.0
 */