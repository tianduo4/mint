package com.jspgou.core.web;

import com.jspgou.core.entity.Website;

import javax.servlet.http.HttpServletRequest;

public abstract class SiteUtils {
    public static Website getWeb(HttpServletRequest request) {
        Website website = (Website) request.getAttribute("_web_key");
        if (website == null) {
            throw new IllegalStateException("Webiste not found in Request Attribute '_web_key'");
        }
        return website;
    }

    public static Long getWebId(HttpServletRequest request) {
        return getWeb(request).getId();
    }
}

