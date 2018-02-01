package com.mint.cms.manager.impl;

import com.mint.cms.manager.UpdateMng;
import com.mint.common.web.springmvc.RealPathResolver;
import com.mint.core.entity.Website;
import com.mint.core.manager.LogMng;
import com.mint.core.manager.WebsiteMng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

public class UpdateMngImpl
        implements UpdateMng {
    public static final String UPDATE_PATH = ".zip";
    private String path;

    @Autowired
    private RealPathResolver realPathResolver;

    @Autowired
    private LogMng logMng;

    @Autowired
    private WebsiteMng websiteMng;

    public void update() {
        this.path = (this.realPathResolver.get("/") + "update" + System.getProperty("file.separator"));
        long daySpan = 1296000000L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
            Timer timer = new Timer();
            timer.schedule(new PlainTimerTask(), startTime, daySpan);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getRestart() {
        String dbXmlFileName = "/WEB-INF/config/jdbc.properties";

        String restart = null;
        try {
            InputStream in = new FileInputStream(this.realPathResolver.get(dbXmlFileName));
            Properties p = new Properties();
            p.load(in);
            String url = p.getProperty("jdbc.url");
            String[] urls = url.split("[?]");
            String dbUser = p.getProperty("jdbc.username");
            String dbPassword = p.getProperty("jdbc.password");
            Connection conn = getConn(urls[0], dbUser, dbPassword);
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from jc_core_website ;");
            rs.next();
            restart = rs.getString("restart");
            stat.close();
            conn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restart;
    }

    public Connection getConn(String url, String dbUser, String dbPassword)
            throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String connStr = url + "?user=" + dbUser + "&password=" + dbPassword + "&characterEncoding=utf8";
        Connection conn = DriverManager.getConnection(connStr);
        return conn;
    }

    private class CharsetHandler
            implements ResponseHandler<String> {
        private String charset;

        public CharsetHandler(String charset) {
            this.charset = charset;
        }

        public String handleResponse(HttpResponse response)
                throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                if (!StringUtils.isBlank(this.charset)) {
                    return EntityUtils.toString(entity, this.charset);
                }
                return EntityUtils.toString(entity);
            }

            return null;
        }
    }

    public class PlainTimerTask extends TimerTask {
        public PlainTimerTask() {
        }

        public void run() {
            Website website = UpdateMngImpl.this.websiteMng.findById(Long.valueOf(1L));
            String url = "http://update.jeecms.com/update.jhtml?version=" + getVersion() + "&domain=" + website.getDomain() + "&name=" + website.getName();
            HttpClient client = new DefaultHttpClient();
            UpdateMngImpl.CharsetHandler handler = new UpdateMngImpl.CharsetHandler("UTF-8");
            try {
                HttpGet httpget = new HttpGet(new URI(url));
                String xml = (String) client.execute(httpget, handler);
                if (!StringUtils.isBlank(xml)) {
                    StringReader reader = new StringReader(xml);
                    InputSource source = new InputSource(reader);
                    SAXBuilder sax = new SAXBuilder();
                    Document doc = sax.build(source);
                    Element element = doc.getRootElement();
                    List list = element.getChildren();
                    for (int i = 0; i < list.size(); i++) {
                        element = (Element) list.get(i);
                        String versions = element.getChild("versions").getText();
                        String updatepackage = element.getChild("updatepackage").getText();
                        String updatelog = element.getChild("updatelog").getText();
                        download(updatepackage, versions);
                        UpdateMngImpl.this.logMng.save(versions, updatelog);
                    }
                }
            } catch (URISyntaxException localURISyntaxException) {
            } catch (ClientProtocolException localClientProtocolException) {
            } catch (IOException localIOException) {
            } catch (JDOMException localJDOMException) {
            }
        }

        public void download(String updatepackage, String versions) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(updatepackage);
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine.getStatusCode() == 200) {
                    String filePath = UpdateMngImpl.this.path + versions + ".zip";
                    File file = new File(filePath);
                    FileOutputStream outputStream = new FileOutputStream(file);
                    InputStream inputStream = httpResponse.getEntity().getContent();
                    byte[] b = new byte[1024];
                    int j = 0;
                    while ((j = inputStream.read(b)) != -1) {
                        outputStream.write(b, 0, j);
                    }
                    outputStream.flush();
                    outputStream.close();
                    unZipFiles(file, UpdateMngImpl.this.path + versions + System.getProperty("file.separator"));
                    Install(versions);
                    replace(versions);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
        }

        public void unZipFiles(File zipFile, String descDir) throws IOException {
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipFile zip = new ZipFile(zipFile);
            for (Enumeration entries = zip.getEntries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");

                if (outPath.lastIndexOf('/') > 0) {
                    File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    if (new File(outPath).isDirectory()) {
                        continue;
                    }
                }
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
        }

        public void Install(String versions) {
            String dbXmlFileName = "/WEB-INF/config/jdbc.properties";
            String dbFileName = "/update/" + versions + "/db/update-to-" + versions + ".sql";
            try {
                InputStream in = new FileInputStream(UpdateMngImpl.this.realPathResolver.get(dbXmlFileName));
                Properties p = new Properties();
                p.load(in);
                String url = p.getProperty("jdbc.url");
                String[] urls = url.split("[?]");
                String dbUser = p.getProperty("jdbc.username");
                String dbPassword = p.getProperty("jdbc.password");
                List sqlList = readSql(UpdateMngImpl.this.realPathResolver.get(dbFileName));
                updateWebsite(urls[0], dbUser, dbPassword);
                createTable(urls[0], dbUser, dbPassword, sqlList);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void updateWebsite(String url, String dbUser, String dbPassword) throws Exception {
            Connection conn = UpdateMngImpl.this.getConn(url, dbUser, dbPassword);
            Statement stat = conn.createStatement();
            String sql = "update jc_core_website set version = '4.6'";
            stat.executeUpdate(sql);
            sql = "update jc_core_website set restart = '1'";
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
        }

        public void replace(String versions) {
            String filePath = UpdateMngImpl.this.path + versions + System.getProperty("file.separator") + "ROOT" + ".zip";
            File file = new File(filePath);
            try {
                unZipFiles(file, UpdateMngImpl.this.realPathResolver.get("/"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void createTable(String url, String dbUser, String dbPassword, List<String> sqlList) throws Exception {
            Connection conn = UpdateMngImpl.this.getConn(url, dbUser, dbPassword);
            Statement stat = conn.createStatement();
            for (String dllsql : sqlList) {
                stat.execute(dllsql);
            }
            stat.close();
            conn.close();
        }

        public List<String> readSql(String fileName)
                throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
            List sqlList = new ArrayList();
            StringBuilder sqlSb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                if ((s.startsWith("/*")) || (s.startsWith("#")) ||
                        (StringUtils.isBlank(s))) {
                    continue;
                }
                if (s.endsWith(";")) {
                    sqlSb.append(s);
                    sqlSb.setLength(sqlSb.length() - 1);
                    sqlList.add(sqlSb.toString());
                    sqlSb.setLength(0);
                } else {
                    sqlSb.append(s);
                }
            }
            br.close();
            return sqlList;
        }

        public String getVersion() {
            String dbXmlFileName = "/WEB-INF/config/jdbc.properties";

            String version = null;
            try {
                InputStream in = new FileInputStream(UpdateMngImpl.this.realPathResolver.get(dbXmlFileName));
                Properties p = new Properties();
                p.load(in);
                String url = p.getProperty("jdbc.url");
                String[] urls = url.split("[?]");
                String dbUser = p.getProperty("jdbc.username");
                String dbPassword = p.getProperty("jdbc.password");
                Connection conn = UpdateMngImpl.this.getConn(urls[0], dbUser, dbPassword);
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery("select * from jc_core_website ;");
                rs.next();
                version = rs.getString("version");
                stat.close();
                conn.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return version;
        }
    }
}

