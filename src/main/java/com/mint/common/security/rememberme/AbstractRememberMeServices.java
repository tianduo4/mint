package com.mint.common.security.rememberme;

import com.mint.common.security.AccountStatusException;
import com.mint.common.security.UsernameNotFoundException;
import com.mint.common.security.userdetails.AccountStatusUserDetailsChecker;
import com.mint.common.security.userdetails.UserDetails;
import com.mint.common.security.userdetails.UserDetailsChecker;
import com.mint.common.security.userdetails.UserDetailsService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractRememberMeServices
        implements RememberMeService, InitializingBean {
    public static final String REMEMBER_ME_COOKIE_KEY = "remember_me_cookie";
    private static final String DELIMITER = ":";
    public static final String DEFAULT_PARAMETER = "remember_me";
    public static final int TWO_WEEKS_S = 1209600;
    private String cookieName = "remember_me_cookie";
    private String parameter = "remember_me";
    private int tokenValiditySeconds = 1209600;
    private boolean alwaysRemember;
    private boolean alwaysRememberCookie;
    private String key;
    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
    private UserDetailsService userDetailsService;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public final UserDetails autoLogin(HttpServletRequest request, HttpServletResponse response)
            throws CookieTheftException {
        String rememberMeCookie = extractRememberMeCookie(request);

        if (rememberMeCookie == null) {
            return null;
        }

        this.logger.debug("Remember-me cookie detected");

        UserDetails user = null;
        try {
            String[] cookieTokens = decodeCookie(rememberMeCookie);
            user = processAutoLoginCookie(cookieTokens, request, response);
            this.userDetailsChecker.check(user);
        } catch (CookieTheftException cte) {
            cancelCookie(request, response);
            throw cte;
        } catch (UsernameNotFoundException noUser) {
            cancelCookie(request, response);
            this.logger.debug("Remember-me login was valid but corresponding user not found.",
                    noUser);
            return null;
        } catch (InvalidCookieException invalidCookie) {
            cancelCookie(request, response);
            this.logger.debug("Invalid remember-me cookie: " +
                    invalidCookie.getMessage());
            return null;
        } catch (AccountStatusException statusInvalid) {
            cancelCookie(request, response);
            this.logger.debug("Invalid UserDetails: " + statusInvalid.getMessage());
            return null;
        } catch (RememberMeAuthenticationException e) {
            cancelCookie(request, response);
            this.logger.debug(e.getMessage());
            return null;
        }
        String[] cookieTokens;
        this.logger.debug("Remember-me cookie accepted");
        return user;
    }

    public final boolean loginSuccess(HttpServletRequest request, HttpServletResponse response, UserDetails user) {
        if (!rememberMeRequested(request, this.parameter)) {
            this.logger.debug("Remember-me login not requested.");
            return false;
        }

        return onLoginSuccess(request, response, user);
    }

    public final void loginFail(HttpServletRequest request, HttpServletResponse response) {
        this.logger.debug("Interactive login attempt was unsuccessful.");
        cancelCookie(request, response);
        onLoginFail(request, response);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        this.logger.debug("Remember-me logout.");
        cancelCookie(request, response);
        onLogout(request, response);
    }

    protected String extractRememberMeCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if ((cookies == null) || (cookies.length == 0)) {
            return null;
        }

        for (int i = 0; i < cookies.length; i++) {
            if (this.cookieName.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        if (this.alwaysRemember) {
            return true;
        }

        String paramValue = request.getParameter(parameter);

        if ((paramValue != null) && (
                (paramValue.equalsIgnoreCase("true")) ||
                        (paramValue.equalsIgnoreCase("on")) ||
                        (paramValue.equalsIgnoreCase("yes")) ||
                        (paramValue.equals("1")))) {
            return true;
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Did not send remember-me cookie (principal did not set parameter '" +
                    parameter + "')");
        }

        return false;
    }

    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        String cookieValue = encodeCookie(tokens);
        Cookie cookie = new Cookie(this.cookieName, cookieValue);
        String ctx = request.getContextPath();
        cookie.setPath(StringUtils.hasText(ctx) ? ctx : "/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        this.logger.debug("Cancelling cookie");
        Cookie cookie = new Cookie(this.cookieName, null);
        String ctx = request.getContextPath();
        cookie.setPath(StringUtils.hasText(ctx) ? ctx : "/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    protected String[] decodeCookie(String cookieValue)
            throws InvalidCookieException {
        StringBuilder sb = new StringBuilder(cookieValue.length() + 3)
                .append(cookieValue);
        for (int j = 0; j < sb.length() % 4; j++) {
            sb.append("=");
        }
        cookieValue = sb.toString();
        if (!Base64.isArrayByteBase64(cookieValue.getBytes())) {
            throw new InvalidCookieException(
                    "Cookie token was not Base64 encoded; value was '" +
                            cookieValue + "'");
        }

        String cookieAsPlainText = new String(Base64.decodeBase64(cookieValue
                .getBytes()));

        return StringUtils.delimitedListToStringArray(cookieAsPlainText,
                ":");
    }

    protected String encodeCookie(String[] cookieTokens) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cookieTokens.length; i++) {
            sb.append(cookieTokens[i]);

            if (i < cookieTokens.length - 1) {
                sb.append(":");
            }
        }

        String value = sb.toString();
        sb = new StringBuilder(
                new String(Base64.encodeBase64(value.getBytes())));

        while (sb.charAt(sb.length() - 1) == '=') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    protected abstract UserDetails processAutoLoginCookie(String[] paramArrayOfString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
            throws RememberMeAuthenticationException, UsernameNotFoundException;

    protected abstract boolean onLoginSuccess(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, UserDetails paramUserDetails);

    protected void onLoginFail(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void onLogout(HttpServletRequest request, HttpServletResponse response) {
    }

    public void afterPropertiesSet()
            throws Exception {
        Assert.hasLength(this.key);
        Assert.hasLength(this.parameter);
        Assert.hasLength(this.cookieName);
        Assert.notNull(this.userDetailsService);
    }

    protected String getCookieName() {
        return this.cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public boolean isAlwaysRemember() {
        return this.alwaysRemember;
    }

    public void setAlwaysRemember(boolean alwaysRemember) {
        this.alwaysRemember = alwaysRemember;
    }

    public String getParameter() {
        return this.parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    protected int getTokenValiditySeconds() {
        return this.tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    public boolean isAlwaysRememberCookie() {
        return this.alwaysRememberCookie;
    }

    public void setAlwaysRememberCookie(boolean alwaysRememberCookie) {
        this.alwaysRememberCookie = alwaysRememberCookie;
    }
}

