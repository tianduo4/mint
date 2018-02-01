package com.jspgou.cms.web;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.core.entity.Website;

import javax.servlet.http.HttpServletRequest;

public class CmsUtils {
    public static final String REQUEST_MEMBER_KEY = "_member_key";
    public static final String REQUEST_ADMIN_KEY = "_admin_key";
    public static final String REQUEST_WEBSITE_KEY = "_web_key";
    public static final String REQUEST_BASE_DOMAIN_KEY = "_base_domain_key";

    public static ShopMember getMember(HttpServletRequest request) {
        return (ShopMember) request.getAttribute("_member_key");
    }

    public static void setAdmin(HttpServletRequest request, ShopAdmin admin) {
        request.setAttribute("_admin_key", admin);
    }

    public static ShopAdmin getAdmin(HttpServletRequest request) {
        return (ShopAdmin) request.getAttribute("_admin_key");
    }

    public static Long getMemberId(HttpServletRequest request) {
        ShopMember member = getMember(request);
        if (member != null) {
            return member.getId();
        }
        return null;
    }

    public static void setMember(HttpServletRequest request, ShopMember member) {
        request.setAttribute("_member_key", member);
    }

    public static Website getWebsite(HttpServletRequest request) {
        return (Website) request.getAttribute("_web_key");
    }

    public static void setWebsite(HttpServletRequest request, Website web) {
        request.setAttribute("_web_key", web);
    }

    public static Long getWebsiteId(HttpServletRequest request) {
        return getWebsite(request).getId();
    }
}

