package com.mint.core.security;

import com.mint.common.web.CookieUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

public class CmsLogoutFilter extends LogoutFilter {
    public static final String RETURN_URL = "returnUrl";
    public static final String USER_LOG_OUT_FLAG = "logout";
    private String adminPrefix;
    private String adminLogin;

    protected String getRedirectUrl(ServletRequest req, ServletResponse resp, Subject subject) {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String redirectUrl = request.getParameter("returnUrl");
        String domain = request.getServerName();
        if (domain.indexOf(".") > -1) {
            domain = domain.substring(domain.indexOf(".") + 1);
        }
        CookieUtils.addCookie(request, response, "JSESSIONID", null, Integer.valueOf(0), domain, "/");
        CookieUtils.addCookie(request, response, "sso_logout", "true", null, domain, "/");
        if (StringUtils.isBlank(redirectUrl)) {
            if (request.getRequestURI().startsWith(request.getContextPath() + getAdminPrefix()))
                redirectUrl = getAdminLogin();
            else {
                redirectUrl = getRedirectUrl();
            }
        }
        return redirectUrl;
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

