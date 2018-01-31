/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.manager.UpdateMng;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.LogMng;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.HttpClient;
/*     */ import org.apache.http.client.ResponseHandler;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.conn.ClientConnectionManager;
/*     */ import org.apache.http.impl.client.DefaultHttpClient;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.apache.tools.zip.ZipEntry;
/*     */ import org.apache.tools.zip.ZipFile;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ public class UpdateMngImpl
/*     */   implements UpdateMng
/*     */ {
/*     */   public static final String UPDATE_PATH = ".zip";
/*     */   private String path;
/*     */ 
/*     */   @Autowired
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   @Autowired
/*     */   private LogMng logMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   public void update()
/*     */   {
/*  69 */     this.path = (this.realPathResolver.get("/") + "update" + System.getProperty("file.separator"));
/*  70 */     long daySpan = 1296000000L;
/*  71 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/*  73 */       Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
/*  74 */       Timer timer = new Timer();
/*  75 */       timer.schedule(new PlainTimerTask(), startTime, daySpan);
/*     */     } catch (ParseException e) {
/*  77 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getRestart()
/*     */   {
/*  83 */     String dbXmlFileName = "/WEB-INF/config/jdbc.properties";
/*     */ 
/*  85 */     String restart = null;
/*     */     try
/*     */     {
/*  88 */       InputStream in = new FileInputStream(this.realPathResolver.get(dbXmlFileName));
/*  89 */       Properties p = new Properties();
/*  90 */       p.load(in);
/*  91 */       String url = p.getProperty("jdbc.url");
/*  92 */       String[] urls = url.split("[?]");
/*  93 */       String dbUser = p.getProperty("jdbc.username");
/*  94 */       String dbPassword = p.getProperty("jdbc.password");
/*  95 */       Connection conn = getConn(urls[0], dbUser, dbPassword);
/*  96 */       Statement stat = conn.createStatement();
/*  97 */       ResultSet rs = stat.executeQuery("select * from jc_core_website ;");
/*  98 */       rs.next();
/*  99 */       restart = rs.getString("restart");
/* 100 */       stat.close();
/* 101 */       conn.close();
/*     */     } catch (FileNotFoundException e) {
/* 103 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 105 */       e.printStackTrace();
/*     */     }
/*     */     catch (Exception e) {
/* 108 */       e.printStackTrace();
/*     */     }
/* 110 */     return restart;
/*     */   }
/*     */ 
/*     */   public Connection getConn(String url, String dbUser, String dbPassword)
/*     */     throws Exception
/*     */   {
/* 369 */     Class.forName("com.mysql.jdbc.Driver");
/* 370 */     Class.forName("com.mysql.jdbc.Driver").newInstance();
/* 371 */     String connStr = url + "?user=" + dbUser + "&password=" + dbPassword + "&characterEncoding=utf8";
/* 372 */     Connection conn = DriverManager.getConnection(connStr);
/* 373 */     return conn;
/*     */   }
/*     */ 
/*     */   private class CharsetHandler
/*     */     implements ResponseHandler<String>
/*     */   {
/*     */     private String charset;
/*     */ 
/*     */     public CharsetHandler(String charset)
/*     */     {
/* 345 */       this.charset = charset;
/*     */     }
/*     */ 
/*     */     public String handleResponse(HttpResponse response)
/*     */       throws ClientProtocolException, IOException
/*     */     {
/* 351 */       StatusLine statusLine = response.getStatusLine();
/* 352 */       if (statusLine.getStatusCode() >= 300) {
/* 353 */         return null;
/*     */       }
/* 355 */       HttpEntity entity = response.getEntity();
/* 356 */       if (entity != null) {
/* 357 */         if (!StringUtils.isBlank(this.charset)) {
/* 358 */           return EntityUtils.toString(entity, this.charset);
/*     */         }
/* 360 */         return EntityUtils.toString(entity);
/*     */       }
/*     */ 
/* 363 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public class PlainTimerTask extends TimerTask
/*     */   {
/*     */     public PlainTimerTask()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 116 */       Website website = UpdateMngImpl.this.websiteMng.findById(Long.valueOf(1L));
/* 117 */       String url = "http://update.jeecms.com/update.jhtml?version=" + getVersion() + "&domain=" + website.getDomain() + "&name=" + website.getName();
/* 118 */       HttpClient client = new DefaultHttpClient();
/* 119 */       UpdateMngImpl.CharsetHandler handler = new UpdateMngImpl.CharsetHandler(UpdateMngImpl.this, "UTF-8");
/*     */       try
/*     */       {
/* 122 */         HttpGet httpget = new HttpGet(new URI(url));
/* 123 */         String xml = (String)client.execute(httpget, handler);
/* 124 */         if (!StringUtils.isBlank(xml)) {
/* 125 */           StringReader reader = new StringReader(xml);
/* 126 */           InputSource source = new InputSource(reader);
/* 127 */           SAXBuilder sax = new SAXBuilder();
/* 128 */           Document doc = sax.build(source);
/* 129 */           Element element = doc.getRootElement();
/* 130 */           List list = element.getChildren();
/* 131 */           for (int i = 0; i < list.size(); i++) {
/* 132 */             element = (Element)list.get(i);
/* 133 */             String versions = element.getChild("versions").getText();
/* 134 */             String updatepackage = element.getChild("updatepackage").getText();
/* 135 */             String updatelog = element.getChild("updatelog").getText();
/* 136 */             download(updatepackage, versions);
/* 137 */             UpdateMngImpl.this.logMng.save(versions, updatelog);
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (URISyntaxException localURISyntaxException) {
/*     */       }
/*     */       catch (ClientProtocolException localClientProtocolException) {
/*     */       }
/*     */       catch (IOException localIOException) {
/*     */       }
/*     */       catch (JDOMException localJDOMException) {
/*     */       }
/*     */     }
/*     */ 
/*     */     public void download(String updatepackage, String versions) {
/* 152 */       HttpClient httpClient = new DefaultHttpClient();
/* 153 */       HttpGet httpGet = new HttpGet(updatepackage);
/*     */       try {
/* 155 */         HttpResponse httpResponse = httpClient.execute(httpGet);
/* 156 */         StatusLine statusLine = httpResponse.getStatusLine();
/* 157 */         if (statusLine.getStatusCode() == 200) {
/* 158 */           String filePath = UpdateMngImpl.this.path + versions + ".zip";
/* 159 */           File file = new File(filePath);
/* 160 */           FileOutputStream outputStream = new FileOutputStream(file);
/* 161 */           InputStream inputStream = httpResponse.getEntity().getContent();
/* 162 */           byte[] b = new byte[1024];
/* 163 */           int j = 0;
/* 164 */           while ((j = inputStream.read(b)) != -1) {
/* 165 */             outputStream.write(b, 0, j);
/*     */           }
/* 167 */           outputStream.flush();
/* 168 */           outputStream.close();
/* 169 */           unZipFiles(file, UpdateMngImpl.this.path + versions + System.getProperty("file.separator"));
/* 170 */           Install(versions);
/* 171 */           replace(versions);
/*     */         }
/*     */       } catch (ClientProtocolException e) {
/* 174 */         e.printStackTrace();
/*     */       } catch (IOException e) {
/* 176 */         e.printStackTrace();
/*     */       } finally {
/* 178 */         httpClient.getConnectionManager().shutdown();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void unZipFiles(File zipFile, String descDir) throws IOException {
/* 183 */       File pathFile = new File(descDir);
/* 184 */       if (!pathFile.exists()) {
/* 185 */         pathFile.mkdirs();
/*     */       }
/* 187 */       ZipFile zip = new ZipFile(zipFile);
/* 188 */       for (Enumeration entries = zip.getEntries(); entries.hasMoreElements(); ) {
/* 189 */         ZipEntry entry = (ZipEntry)entries.nextElement();
/* 190 */         String zipEntryName = entry.getName();
/* 191 */         InputStream in = zip.getInputStream(entry);
/* 192 */         String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
/*     */ 
/* 194 */         if (outPath.lastIndexOf('/') > 0) {
/* 195 */           File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
/* 196 */           if (!file.exists()) {
/* 197 */             file.mkdirs();
/*     */           }
/*     */ 
/* 200 */           if (new File(outPath).isDirectory())
/*     */           {
/*     */             continue;
/*     */           }
/*     */         }
/* 205 */         OutputStream out = new FileOutputStream(outPath);
/* 206 */         byte[] buf1 = new byte[1024];
/*     */         int len;
/* 208 */         while ((len = in.read(buf1)) > 0)
/*     */         {
/*     */           int len;
/* 209 */           out.write(buf1, 0, len);
/*     */         }
/* 211 */         in.close();
/* 212 */         out.close();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void Install(String versions)
/*     */     {
/* 218 */       String dbXmlFileName = "/WEB-INF/config/jdbc.properties";
/* 219 */       String dbFileName = "/update/" + versions + "/db/update-to-" + versions + ".sql";
/*     */       try
/*     */       {
/* 223 */         InputStream in = new FileInputStream(UpdateMngImpl.this.realPathResolver.get(dbXmlFileName));
/* 224 */         Properties p = new Properties();
/* 225 */         p.load(in);
/* 226 */         String url = p.getProperty("jdbc.url");
/* 227 */         String[] urls = url.split("[?]");
/* 228 */         String dbUser = p.getProperty("jdbc.username");
/* 229 */         String dbPassword = p.getProperty("jdbc.password");
/* 230 */         List sqlList = readSql(UpdateMngImpl.this.realPathResolver.get(dbFileName));
/* 231 */         updateWebsite(urls[0], dbUser, dbPassword);
/* 232 */         createTable(urls[0], dbUser, dbPassword, sqlList);
/*     */       } catch (FileNotFoundException e) {
/* 234 */         e.printStackTrace();
/*     */       } catch (IOException e) {
/* 236 */         e.printStackTrace();
/*     */       }
/*     */       catch (Exception e) {
/* 239 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void updateWebsite(String url, String dbUser, String dbPassword) throws Exception
/*     */     {
/* 245 */       Connection conn = UpdateMngImpl.this.getConn(url, dbUser, dbPassword);
/* 246 */       Statement stat = conn.createStatement();
/* 247 */       String sql = "update jc_core_website set version = '4.6'";
/* 248 */       stat.executeUpdate(sql);
/* 249 */       sql = "update jc_core_website set restart = '1'";
/* 250 */       stat.executeUpdate(sql);
/* 251 */       stat.close();
/* 252 */       conn.close();
/*     */     }
/*     */ 
/*     */     public void replace(String versions)
/*     */     {
/* 258 */       String filePath = UpdateMngImpl.this.path + versions + System.getProperty("file.separator") + "ROOT" + ".zip";
/* 259 */       File file = new File(filePath);
/*     */       try {
/* 261 */         unZipFiles(file, UpdateMngImpl.this.realPathResolver.get("/"));
/*     */       } catch (IOException e) {
/* 263 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void createTable(String url, String dbUser, String dbPassword, List<String> sqlList) throws Exception
/*     */     {
/* 269 */       Connection conn = UpdateMngImpl.this.getConn(url, dbUser, dbPassword);
/* 270 */       Statement stat = conn.createStatement();
/* 271 */       for (String dllsql : sqlList) {
/* 272 */         stat.execute(dllsql);
/*     */       }
/* 274 */       stat.close();
/* 275 */       conn.close();
/*     */     }
/*     */ 
/*     */     public List<String> readSql(String fileName)
/*     */       throws Exception
/*     */     {
/* 287 */       BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
/* 288 */       List sqlList = new ArrayList();
/* 289 */       StringBuilder sqlSb = new StringBuilder();
/* 290 */       String s = null;
/* 291 */       while ((s = br.readLine()) != null) {
/* 292 */         if ((s.startsWith("/*")) || (s.startsWith("#")) || 
/* 293 */           (StringUtils.isBlank(s))) {
/*     */           continue;
/*     */         }
/* 296 */         if (s.endsWith(";")) {
/* 297 */           sqlSb.append(s);
/* 298 */           sqlSb.setLength(sqlSb.length() - 1);
/* 299 */           sqlList.add(sqlSb.toString());
/* 300 */           sqlSb.setLength(0);
/*     */         } else {
/* 302 */           sqlSb.append(s);
/*     */         }
/*     */       }
/* 305 */       br.close();
/* 306 */       return sqlList;
/*     */     }
/*     */ 
/*     */     public String getVersion() {
/* 310 */       String dbXmlFileName = "/WEB-INF/config/jdbc.properties";
/*     */ 
/* 312 */       String version = null;
/*     */       try
/*     */       {
/* 315 */         InputStream in = new FileInputStream(UpdateMngImpl.this.realPathResolver.get(dbXmlFileName));
/* 316 */         Properties p = new Properties();
/* 317 */         p.load(in);
/* 318 */         String url = p.getProperty("jdbc.url");
/* 319 */         String[] urls = url.split("[?]");
/* 320 */         String dbUser = p.getProperty("jdbc.username");
/* 321 */         String dbPassword = p.getProperty("jdbc.password");
/* 322 */         Connection conn = UpdateMngImpl.this.getConn(urls[0], dbUser, dbPassword);
/* 323 */         Statement stat = conn.createStatement();
/* 324 */         ResultSet rs = stat.executeQuery("select * from jc_core_website ;");
/* 325 */         rs.next();
/* 326 */         version = rs.getString("version");
/* 327 */         stat.close();
/* 328 */         conn.close();
/*     */       } catch (FileNotFoundException e) {
/* 330 */         e.printStackTrace();
/*     */       } catch (IOException e) {
/* 332 */         e.printStackTrace();
/*     */       }
/*     */       catch (Exception e) {
/* 335 */         e.printStackTrace();
/*     */       }
/* 337 */       return version;
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.UpdateMngImpl
 * JD-Core Version:    0.6.0
 */