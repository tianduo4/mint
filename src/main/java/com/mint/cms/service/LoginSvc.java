package com.mint.cms.service;

import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopMember;
import com.mint.common.security.BadCredentialsException;
import com.mint.common.security.UserNotAcitveException;
import com.mint.common.security.UsernameNotFoundException;
import com.mint.core.entity.Website;
import com.mint.core.security.UserNotInWebsiteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface LoginSvc {
    public abstract ShopMember memberLogin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Website paramWebsite, String paramString1, String paramString2)
            throws UsernameNotFoundException, BadCredentialsException, UserNotInWebsiteException, UserNotAcitveException;

    public abstract ShopMember getMember(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Website paramWebsite);

    public abstract ShopAdmin adminLogin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Website paramWebsite, String paramString1, String paramString2)
            throws UsernameNotFoundException, BadCredentialsException, UserNotInWebsiteException;

    public abstract ShopAdmin getAdmin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Website paramWebsite);

    public abstract void logout(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

    public abstract void clearCookie(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
}

