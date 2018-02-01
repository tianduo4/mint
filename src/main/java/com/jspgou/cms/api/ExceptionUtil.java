package com.jspgou.cms.api;

import com.jspgou.common.util.PropertyUtils;
import com.jspgou.common.web.ResponseUtils;

import java.io.File;
import java.io.PrintStream;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;

public class ExceptionUtil {
    public static String JSPGOU_JDBC_CONFIG = "/WEB-INF/config/jdbc.properties";
    public static String MES_CONFIG = "/WEB-INF/languages/jspgou_api/messages_zh_CN.properties";

    public static void convertException(HttpServletResponse response, HttpServletRequest request, Exception e) {
        ApiResponse apiResponse = new ApiResponse("\"\"", "\"system exception\"", 100);
        Throwable th = e.getCause();
        String errMsg = "";

        if ((th instanceof ConstraintViolationException))
            errMsg = ((ConstraintViolationException) th).getSQLException().getMessage();
        else if ((th instanceof BatchUpdateException)) {
            errMsg = ((BatchUpdateException) th).getMessage();
        }
        System.out.println("exception:" + errMsg);
        String path = request.getServletContext().getRealPath(JSPGOU_JDBC_CONFIG);
        String driverName = PropertyUtils.getPropertyValue(new File(path), "jdbc.driverClassName");
        if ((StringUtils.isNotEmpty(driverName)) && (StringUtils.isNotEmpty(errMsg))) {
            if (driverName.indexOf("mysql") >= 0) {
                String checKey = errMsg.substring(errMsg.indexOf(" CONSTRAINT `") + 13, errMsg.indexOf("` FOREIGN KEY"));
                System.out.println(checKey);

                String zhMes = PropertyUtils.getPropertyValue(new File(request.getServletContext().getRealPath(MES_CONFIG)), checKey);
                apiResponse.setMessage("\"delete error\"");
                apiResponse.setCode(205);
                if (StringUtils.isEmpty(zhMes)) {
                    zhMes = errMsg;
                }
                apiResponse.setBody("\"\",\"fail\":\"" + zhMes + "\"");
            } else {
                driverName.indexOf("oracle");
            }
        }

        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

