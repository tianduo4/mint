package com.jspgou.cms;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InstallServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dbHost = request.getParameter("dbHost");
        String dbPort = request.getParameter("dbPort");
        String dbName = request.getParameter("dbName");
        String dbUser = request.getParameter("dbUser");
        String dbPassword = request.getParameter("dbPassword");

        String isCreateDb = request.getParameter("isCreateDb");
        String isCreateTable = request.getParameter("isCreateTable");
        String isInitData = request.getParameter("isInitData");
        String domain = request.getParameter("domain");
        String cxtPath = request.getParameter("cxtPath");
        String port = request.getParameter("port");

        String dbFileName = request.getParameter("dbFileName");
        String initFileName = request.getParameter("initFileName");
        String dbXmlFileName = "/WEB-INF/config/jdbc.properties";
        String webXmlFrom = "/install/config/web.xml";
        String webXmlTo = "/WEB-INF/web.xml";
        try {
            if ("true".equals(isCreateDb))
                Install.createDb(dbHost, dbPort, dbName, dbUser, dbPassword);
            else {
                Install.changeDbCharset(dbHost, dbPort, dbName, dbUser,
                        dbPassword);
            }

            if ("true".equals(isCreateTable)) {
                String sqlPath = getServletContext().getRealPath(dbFileName);
                List sqlList = Install.readSql(sqlPath);
                Install.createTable(dbHost, dbPort, dbName, dbUser, dbPassword,
                        sqlList);
            }

            if ("true".equals(isInitData)) {
                String initPath = getServletContext().getRealPath(initFileName);
                List initList = Install.readSql(initPath);
                Install.createTable(dbHost, dbPort, dbName, dbUser, dbPassword,
                        initList);
            }

            Install.updateConfig(dbHost, dbPort, dbName, dbUser, dbPassword,
                    domain, cxtPath, port);

            String dbXmlPath = getServletContext().getRealPath(dbXmlFileName);

            Install.dbXml(dbXmlPath, dbHost, dbPort, dbName, dbUser,
                    dbPassword);

            String webXmlFromPath = getServletContext().getRealPath(webXmlFrom);
            String webXmlToPath = getServletContext().getRealPath(webXmlTo);
            Install.webXml(webXmlFromPath, webXmlToPath);
        } catch (Exception e) {
            throw new ServletException("install failed!", e);
        }
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("/install/install_setup.jsp");
        dispatcher.forward(request, response);
    }
}

