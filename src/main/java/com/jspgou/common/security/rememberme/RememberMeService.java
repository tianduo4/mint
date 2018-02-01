package com.jspgou.common.security.rememberme;

import com.jspgou.common.security.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface RememberMeService {
    public abstract UserDetails autoLogin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
            throws CookieTheftException;

    public abstract void loginFail(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

    public abstract boolean loginSuccess(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, UserDetails paramUserDetails);

    public abstract void logout(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
}

