package com.jspgou.cms.web;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.manager.ShopAdminMng;
import com.jspgou.cms.service.LoginSvc;
import com.jspgou.cms.web.threadvariable.AdminThread;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

public class AdminContextInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LoginSvc loginSvc;

    @Autowired
    private ShopAdminMng shopAdminMng;
    private Long developAdminId;
    private String[] excludeUrls;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Website web = SiteUtils.getWeb(request);
        ShopAdmin admin = null;
        if (this.developAdminId != null) {
            admin = this.shopAdminMng.findById(this.developAdminId);

            if (admin == null) {
                throw new IllegalStateException("developAdminId not found: " + this.developAdminId);
            }
            Long webId = admin.getWebsite().getId();

            if (!webId.equals(web.getId())) {
                throw new IllegalStateException("developAdminId's website id=" +
                        webId + " not in current website id=" + web.getId());
            }
        } else {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                String username = (String) subject.getPrincipal();
                if (subject.isAuthenticated()) {
                    admin = this.shopAdminMng.getByUsername(username);

                    AdminThread.set(admin);
                }
            }
        }
        String uri = getURI(request);
        if (exclude(uri)) {
            return true;
        }
        if (admin != null) {
            boolean viewonly = admin.getViewonlyAdmin().booleanValue();

            Set perms = admin.getPerms();
            request.setAttribute("_permission_key", perms);
            if ((viewonly) &&
                    (permistionPass(uri))) {
                request.setAttribute("message", MessageResolver.getMessage(request, "login.notPermission", new Object[0]));
                response.sendError(403);
                return false;
            }

        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
            throws Exception {
        ShopAdmin admin = AdminThread.get();
        if ((admin != null) && (mav != null) && (mav.getModelMap() != null) &&
                (mav.getViewName() != null) &&
                (!mav.getViewName().startsWith("redirect:")))
            mav.getModelMap().addAttribute("_permission_key",
                    admin.getPerms());
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        AdminThread.remove();
    }

    private boolean exclude(String uri) {
        if (this.excludeUrls != null) {
            for (String exc : this.excludeUrls) {
                if (exc.equals(uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean permistionPass(String uri) {
        String u = null;

        int i = uri.lastIndexOf("/");
        if (i == -1) {
            throw new RuntimeException("uri must start width '/':" + uri);
        }
        u = uri.substring(i + 1);

        return u.startsWith("o_");
    }

    private static String getURI(HttpServletRequest request)
            throws IllegalStateException {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        int start = 0;
        int i = 0;
        int count = 2;
        if (!StringUtils.isBlank(ctxPath)) {
            count++;
        }
        while ((i < count) && (start != -1)) {
            start = uri.indexOf('/', start + 1);
            i++;
        }
        if (start <= 0) {
            throw new IllegalStateException(
                    "admin access path not like '/jeeadmin/jspgou/...' pattern: " +
                            uri);
        }
        return uri.substring(start);
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public void setDevelopAdminId(Long developAdminId) {
        this.developAdminId = developAdminId;
    }
}

