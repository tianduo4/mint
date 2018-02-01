package com.jspgou.cms.web;

import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.web.threadvariable.GroupThread;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.WebErrors;
import com.jspgou.core.web.front.FrontHelper;
import com.jspgou.core.web.front.URLHelper;
import com.jspgou.core.web.front.URLHelper.PageInfo;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.util.UrlPathHelper;

public abstract class ShopFrontHelper {
    public static final String TPL_ERROR_PAGE = "tpl.errorPage";
    public static final String SUCCESS_PAGE = "tpl.successPage";

    public static void setDynamicPageData(HttpServletRequest request, ModelMap model, Website web, String location, String urlPrefix, String urlSuffix, int pageNo) {
        FrontHelper.setDynamicPageData(request, model, web, location,
                urlPrefix, urlSuffix, pageNo);
        setShopDate(request, model);
    }

    public static String showSuccess(HttpServletRequest request, ModelMap model, String nextUrl) {
        Website web = SiteUtils.getWeb(request);
        setCommonData(request, model, web, 1);
        if (!StringUtils.isBlank(nextUrl)) {
            model.put("nextUrl", nextUrl);
        }
        return web.getTplSys("common", MessageResolver.getMessage(request,
                "tpl.successPage", new Object[0]), request);
    }

    public static void frontPageData(HttpServletRequest request, Map<String, Object> map) {
        int pageNo = URLHelper.getPageNo(request);
        URLHelper.PageInfo info = URLHelper.getPageInfo(request);
        String href = info.getHref();
        String hrefFormer = info.getHrefFormer();
        String hrefLatter = info.getHrefLatter();
        frontPageData(pageNo, href, hrefFormer, hrefLatter, map);
    }

    public static void frontPage(HttpServletRequest request, Map<String, Object> map) {
        int pageNo = URLHelper.getPageNo(request);
        URLHelper.PageInfo info = URLHelper.getPageInfo(request);
        String href = info.getHref();
        String hrefFormer = info.getHrefFormer();
        String hrefLatter = info.getHrefLatter();
        frontPageData(pageNo, href, hrefFormer, hrefLatter, map);
    }

    public static void frontPageData(int pageNo, String href, String hrefFormer, String hrefLatter, Map<String, Object> map) {
        map.put("pageNo", Integer.valueOf(pageNo));
        map.put("href", href);
        map.put("hrefFormer", hrefFormer);
        map.put("hrefLatter", hrefLatter);
    }

    public static void setCommonData(HttpServletRequest request, ModelMap model, Website web, int pageNo) {
        FrontHelper.setCommonData(request, model, web, pageNo);
        setShopDate(request, model);
    }

    public static void setCommon(HttpServletRequest request, ModelMap model, Website web) {
        ShopMember member = (ShopMember) request.getAttribute("_member_key");
        if (member != null) {
            model.addAttribute("member", member);
            model.addAttribute("group", GroupThread.get());
        }
        model.addAttribute("config", request.getAttribute("_shop_config_key"));
        FrontHelper.setCommon(request, model, web);
    }

    public static void setShopDate(HttpServletRequest request, ModelMap model) {
        model.addAttribute("config", request.getAttribute("_shop_config_key"));

        ShopMember member = MemberThread.get();
        if (member != null) {
            model.addAttribute("sessionKey", MemberThread.getSessionKey());
            model.addAttribute("userName", MemberThread.getUserName());
            model.addAttribute("member", member);
            model.addAttribute("group", GroupThread.get());
        }
        Website site = com.jspgou.core.web.SiteUtils.getWeb(request);
        model.addAttribute("baseUrl", site.getUploadResUrlBuff());
    }

    public static String showError(HttpServletRequest request, HttpServletResponse response, ModelMap model, WebErrors errors) {
        Website web = SiteUtils.getWeb(request);
        setCommonData(request, model, web, 1);
        errors.toModel(model);
        return web.getTplSys("common", MessageResolver.getMessage(request,
                "tpl.errorPage", new Object[0]), request);
    }

    public static String getLocation(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        StringBuffer buff = request.getRequestURL();
        String uri = request.getRequestURI();
        String origUri = helper.getOriginatingRequestUri(request);
        buff.replace(buff.length() - uri.length(), buff.length(), origUri);
        String queryString = helper.getOriginatingQueryString(request);
        if (queryString != null) {
            buff.append("?").append(queryString);
        }
        return buff.toString();
    }
}

