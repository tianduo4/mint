package com.mint.common.update;

import com.mint.cms.manager.UpdateMng;
import com.mint.common.web.springmvc.RealPathResolver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class UpdateServlet extends HttpServlet {
    public void init() {
        Install();
    }

    public void Install() {
        ServletContext sc = getServletContext();
        WebApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        RealPathResolver realPathResolver = (RealPathResolver) ac.getBean("realPathResolver");
        UpdateMng updateMng = (UpdateMng) ac.getBean("updateMng");
        updateMng.update();
        String dbXmlFileName = "/WEB-INF/config/jdbc.properties";
        try {
            InputStream in = new FileInputStream(realPathResolver.get(dbXmlFileName));
            Properties p = new Properties();
            p.load(in);
            String url = p.getProperty("jdbc.url");
            String[] urls = url.split("[?]");
            String dbUser = p.getProperty("jdbc.username");
            String dbPassword = p.getProperty("jdbc.password");
            createTable(urls[0], dbUser, dbPassword);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable(String url, String dbUser, String dbPassword) throws Exception {
        Connection conn = getConn(url, dbUser, dbPassword);
        Statement stat = conn.createStatement();
        stat.execute("UPDATE jc_core_website SET restart = '0';");
        stat.close();
        conn.close();
    }

    public Connection getConn(String url, String dbUser, String dbPassword) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String connStr = url + "?user=" + dbUser + "&password=" + dbPassword + "&characterEncoding=utf8";
        Connection conn = DriverManager.getConnection(connStr);
        return conn;
    }
}

