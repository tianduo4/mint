package com.jspgou.core.security;

import com.jspgou.cms.manager.ApiAccountMng;
import com.jspgou.cms.manager.ApiUserLoginMng;
import com.jspgou.common.security.DisabledException;
import com.jspgou.common.security.UserNotAcitveException;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.session.SessionProvider;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.Member;
import com.jspgou.core.entity.User;
import com.jspgou.core.manager.AdminMng;
import com.jspgou.core.manager.LogMng;
import com.jspgou.core.manager.MemberMng;
import com.jspgou.core.manager.UserMng;
import com.octo.captcha.service.image.ImageCaptchaService;

import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CmsAuthenticationFilter extends FormAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(CmsAuthenticationFilter.class);
    public static final String COOKIE_ERROR_REMAINING = "_error_remaining";
    public static final String CAPTCHA_PARAM = "captcha";
    public static final String RETURN_URL = "returnUrl";
    public static final String FAILURE_URL = "failureUrl";

    @Autowired
    private MemberMng memberMng;

    @Autowired
    private AdminMng adminMng;

    @Autowired
    private UserMng userMng;

    @Autowired
    private SessionProvider session;

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    @Autowired
    private LogMng cmsLogMng;

    @Autowired
    private ApiAccountMng apiAccountMng;

    @Autowired
    private ApiUserLoginMng apiUserLoginMng;
    private String adminPrefix;
    private String adminIndex;
    private String adminLogin;

    protected boolean executeLogin(ServletRequest request, ServletResponse response)
            throws UnknownAccountException, Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "create AuthenticationToken error";
            throw new IllegalStateException(msg);
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String username = (String) token.getPrincipal();
        boolean adminLogin = false;
        if (req.getRequestURI().startsWith(req.getContextPath() + getAdminPrefix())) {
            adminLogin = true;
        }
        String failureUrl = req.getParameter("failureUrl");

        Admin admin = this.adminMng.getByUsername(username);
        Member member = this.memberMng.getByUsername(username);

        if ((admin != null) &&
                (isDisabled(admin))) {
            return onLoginFailure(token, failureUrl, adminLogin, new DisabledException(), request, response);
        }

        if ((member != null) &&
                (!isActive(member))) {
            return onLoginFailure(token, failureUrl, adminLogin, new UserNotAcitveException(), request, response);
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, adminLogin, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, failureUrl, adminLogin, e, request, response);
        }
    }

    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        boolean isAllowed = isAccessAllowed(request, response, mappedValue);

        if ((isAllowed) && (isLoginRequest(request, response))) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                this.logger.error("", e);
            }
            return false;
        }
        return (isAllowed) || (onAccessDenied(request, response, mappedValue));
    }

    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response)
            throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String successUrl = req.getParameter("returnUrl");
        if (StringUtils.isBlank(successUrl)) {
            if (req.getRequestURI().startsWith(
                    req.getContextPath() + getAdminPrefix())) {
                successUrl = getAdminIndex();

                WebUtils.getAndClearSavedRequest(request);
                WebUtils.issueRedirect(request, response, successUrl, null, true);
                return;
            }
            successUrl = getSuccessUrl();
        }

        WebUtils.redirectToSavedRequest(req, res, successUrl);
    }

    protected boolean isLoginRequest(ServletRequest req, ServletResponse resp) {
        return (pathsMatch(getLoginUrl(), req)) || (pathsMatch(getAdminLogin(), req));
    }

    private boolean onLoginSuccess(AuthenticationToken token, boolean adminLogin, Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String username = (String) subject.getPrincipal();
        User user = this.userMng.getByUsername(username);
        String ip = RequestUtils.getIpAddr(req);
        Date now = new Timestamp(System.currentTimeMillis());
        String userSessionId = this.session.getSessionId((HttpServletRequest) request, (HttpServletResponse) response);
        this.userMng.updateLoginInfo(user.getId(), ip, now, userSessionId);

        if (adminLogin) {
            this.cmsLogMng.loginSuccess(req, user);
        }
        this.userMng.updateLoginSuccess(user.getId(), ip);
        loginCookie(username, req, res);
        return super.onLoginSuccess(token, subject, request, response);
    }

    private boolean onLoginFailure(AuthenticationToken token, String failureUrl, boolean adminLogin, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String username = (String) token.getPrincipal();
        HttpServletRequest req = (HttpServletRequest) request;
        String ip = RequestUtils.getIpAddr(req);
        User user = this.userMng.getByUsername(username);
        if (user != null) {
            this.userMng.updateLoginError(user.getId(), ip);
        }

        if (adminLogin) {
            this.cmsLogMng.loginFailure(req, "username=" + username);
        }
        return onLoginFailure(failureUrl, token, e, request, response);
    }

    private boolean onLoginFailure(String failureUrl, AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String className = e.getClass().getName();
        request.setAttribute(getFailureKeyAttribute(), className);
        if ((failureUrl != null) || (StringUtils.isNotBlank(failureUrl)))
            try {
                request.getRequestDispatcher(failureUrl).forward(request, response);
            } catch (Exception localException) {
            }
        return true;
    }

    private void loginCookie(String username, HttpServletRequest request, HttpServletResponse response) {
        String domain = request.getServerName();
        if (domain.indexOf(".") > -1) {
            domain = domain.substring(domain.indexOf(".") + 1);
        }
        CookieUtils.addCookie(request, response, "JSESSIONID", this.session.getSessionId(request, response), null, domain, "/");

        CookieUtils.addCookie(request, response, "sso_logout", null, Integer.valueOf(0), domain, "/");
    }

    private boolean isCaptchaRequired(String username, HttpServletRequest request, HttpServletResponse response) {
        Integer errorRemaining = this.userMng.errorRemaining(username);
        String captcha = RequestUtils.getQueryParam(request, "captcha");

        return (!StringUtils.isBlank(captcha)) || ((errorRemaining != null) && (errorRemaining.intValue() < 0));
    }

    private boolean isDisabled(Admin admin) {
        return admin.getDisabled().booleanValue();
    }

    private boolean isActive(Member member) {
        Member un = this.memberMng.findById(member.getId());
        if (un != null) {
            return member.getActive().booleanValue();
        }

        return false;
    }

    public String getAdminPrefix() {
        return this.adminPrefix;
    }

    public void setAdminPrefix(String adminPrefix) {
        this.adminPrefix = adminPrefix;
    }

    public String getAdminIndex() {
        return this.adminIndex;
    }

    public void setAdminIndex(String adminIndex) {
        this.adminIndex = adminIndex;
    }

    public String getAdminLogin() {
        return this.adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }
}

