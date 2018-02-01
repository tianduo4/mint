package com.jspgou.common.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

public class CookieUtils {
    public static final String COOKIE_PAGE_SIZE = "_cookie_page_size";
    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 200;

    public static int getPageSize(HttpServletRequest request) {
        Assert.notNull(request);
        Cookie cookie = getCookie(request, "_cookie_page_size");
        int count = 0;
        if (cookie != null)
            try {
                count = Integer.parseInt(cookie.getValue());
            } catch (Exception localException) {
            }
        if (count <= 0)
            count = 20;
        else if (count > 200) {
            count = 200;
        }
        return count;
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request);
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String domain, String path) {
        Cookie cookie = new Cookie(name, value);
        if (expiry != null) {
            cookie.setMaxAge(expiry.intValue());
        }
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        response.addCookie(cookie);
        return cookie;
    }

    public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String domain) {
        Cookie cookie = new Cookie(name, value);
        if (expiry != null) {
            cookie.setMaxAge(expiry.intValue());
        }
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        String ctx = request.getContextPath();
        cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
        response.addCookie(cookie);
        return cookie;
    }

    public static void cancleCookie(HttpServletResponse response, String name, String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        if (!StringUtils.isBlank(domain)) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }
}

