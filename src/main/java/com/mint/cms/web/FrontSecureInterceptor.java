package com.mint.cms.web;

import com.mint.cms.service.LoginSvc;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.security.annotation.Secured;
import com.mint.core.web.front.FrontHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class FrontSecureInterceptor extends HandlerInterceptorAdapter
        implements InitializingBean {
    private String loginUrl;
    private LoginSvc loginSvc;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setAttribute("_login_url", this.loginUrl);
        Secured secured = (Secured) handler.getClass().getAnnotation(Secured.class);
        if (secured != null) {
            if (MemberThread.get() == null) {
                this.loginSvc.clearCookie(request, response);
                response.sendRedirect(FrontHelper.getLoginUrl(this.loginUrl, request
                        .getContextPath(), request.getRequestURL().toString()));
                return false;
            }
        }

        return true;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.loginUrl);
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Autowired
    public void setLoginSvc(LoginSvc loginSvc) {
        this.loginSvc = loginSvc;
    }
}

