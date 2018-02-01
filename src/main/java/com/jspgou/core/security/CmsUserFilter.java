package com.jspgou.core.security;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

public class CmsUserFilter extends UserFilter {
    private String adminPrefix;
    private String adminLogin;

    protected void redirectToLogin(ServletRequest req, ServletResponse resp)
            throws IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String loginUrl;
        if (request.getRequestURI().startsWith(request.getContextPath() + getAdminPrefix()))
            loginUrl = getAdminLogin();
        else {
            loginUrl = getLoginUrl();
        }
        WebUtils.issueRedirect(request, response, loginUrl);
    }

    public String getAdminPrefix() {
        return this.adminPrefix;
    }

    public void setAdminPrefix(String adminPrefix) {
        this.adminPrefix = adminPrefix;
    }

    public String getAdminLogin() {
        return this.adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }
}

