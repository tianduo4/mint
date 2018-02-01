package com.jspgou.cms.web;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.entity.ApiAccount;
import com.jspgou.cms.entity.ApiUserLogin;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.manager.ApiAccountMng;
import com.jspgou.cms.manager.ApiUserLoginMng;
import com.jspgou.common.util.AES128Util;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;
import com.jspgou.core.web.WebErrors;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

public class AdminApiInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = Logger.getLogger(AdminApiInterceptor.class);
    public static final String SITE_PARAM = "_site_id_param";
    public static final String SITE_COOKIE = "_site_id_cookie";
    public static final String PERMISSION_MODEL = "_permission_key";

    @Autowired
    private WebsiteMng websiteMng;
    private String[] excludeUrls;
    private String sessionKey = "sessionKey";
    private String appId = "appId";
    private String sign = "sign";

    @Autowired
    private ApiAccountMng apiAccountMng;

    @Autowired
    private ApiUserLoginMng apiUserLoginMng;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String body = "\"\"";
        int code = 0;
        String message = "";
        try {
            WebErrors errors = WebErrors.create(request);
            String uri = getURI(request);

            ApiAccount apiAccount = getApiAccount(request);
            if ((apiAccount != null) && (!apiAccount.getDisabled())) {
                CmsThreadVariable.setApiAccount(apiAccount);

                if (exclude(uri)) {
                    return true;
                }

                ShopAdmin user = getUser(apiAccount, request);

                Short userStatus = getStatus(apiAccount, request);
                CmsUtils.setAdmin(request, user);

                CmsThreadVariable.setAdminUser(user);
                if (user != null) {
                    CmsThreadVariable.setSite(user.getWebsite());
                    CmsUtils.setWebsite(request, user.getWebsite());

                    if (userStatus.equals(ApiUserLogin.USER_STATUS_LOGOVERTIME)) {
                        code = 300;
                        message = "\"user over time\"";
                    } else if (userStatus.equals(ApiUserLogin.USER_STATUS_LOGOUT)) {
                        code = 302;
                        message = "\"user not login\"";
                    } else {
                        if (!user.isSuper()) {
                            if (!hasUrlPerm(user.getWebsite(), user, uri)) {
                                code = 209;
                                message = "\"user has not perm\"";
                            }
                        }
                        if (code == 0) {
                            HandlerMethod handlerMethod = (HandlerMethod) handler;
                            Method method = handlerMethod.getMethod();
                            SignValidate annotation = (SignValidate) method.getAnnotation(SignValidate.class);

                            if ((annotation != null) && (annotation.need())) {
                                if (user.getAdmin().getViewonlyAdmin().booleanValue()) {
                                    code = 209;
                                    message = "\"user has not perm\"";
                                } else {
                                    Object[] result = validateSign(request, errors);
                                    if (!"0".equals(result[0])) {
                                        code = Integer.parseInt(String.valueOf(result[0]));
                                        message = String.valueOf(result[1]);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    code = 302;
                    message = "\"user not login\"";
                }
            } else {
                code = 203;
                message = "\"appId not exist or appId disabled\"";
            }
        } catch (Exception e) {
            code = 100;
            message = "\"system exception\"";
        }
        if (code != 0) {
            ApiResponse apiResponse = new ApiResponse(body, message, code);
            ResponseUtils.renderApiJson(response, request, apiResponse);
            return false;
        }

        refreshUserLoginActive();
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
            throws Exception {
    }

    private Website getSite(HttpServletRequest request, HttpServletResponse response) {
        Website site = getByParams(request, response);
        if (site == null) {
            site = getByCookie(request);
        }
        if (site == null) {
            site = getByDomain(request);
        }
        if (site == null) {
            site = getByDefault();
        }
        if (site == null) {
            throw new RuntimeException("cannot get site!");
        }
        return site;
    }

    private Website getByParams(HttpServletRequest request, HttpServletResponse response) {
        String p = request.getParameter("_site_id_param");
        if (!StringUtils.isBlank(p)) {
            try {
                Long siteId = Long.valueOf(Long.parseLong(p));
                Website site = this.websiteMng.findById(siteId);
                if (site != null) {
                    CookieUtils.addCookie(request, response, "_site_id_cookie", site
                            .getId().toString(), null, null);
                    return site;
                }
            } catch (NumberFormatException e) {
                log.warn("param site id format exception", e);
            }
        }
        return null;
    }

    private Website getByCookie(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request, "_site_id_cookie");
        if (cookie != null) {
            String v = cookie.getValue();
            if (!StringUtils.isBlank(v)) {
                try {
                    Long siteId = Long.valueOf(Long.parseLong(v));
                    return this.websiteMng.findById(siteId);
                } catch (NumberFormatException e) {
                    log.warn("cookie site id format exception", e);
                }
            }
        }
        return null;
    }

    private Website getByDomain(HttpServletRequest request) {
        String domain = request.getServerName();
        if (!StringUtils.isBlank(domain)) {
            return this.websiteMng.findByDomain(domain, true);
        }
        return null;
    }

    private Website getByDefault() {
        List list = this.websiteMng.getListFromCache();
        if (list.size() > 0) {
            return (Website) list.get(0);
        }
        return null;
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
                    "admin access path not like '/jeeadmin/jeecms/...' pattern: " +
                            uri);
        }
        return uri.substring(start);
    }

    private ApiAccount getApiAccount(HttpServletRequest request) {
        String appId = request.getParameter(this.appId);
        ApiAccount apiAccount = null;
        if ((StringUtils.isNotBlank(this.sessionKey)) && (StringUtils.isNotBlank(appId))) {
            apiAccount = this.apiAccountMng.findByAppId(appId);
        }
        return apiAccount;
    }

    private ShopAdmin getUser(ApiAccount apiAccount, HttpServletRequest request) throws Exception {
        ShopAdmin user = null;
        String sessionKey = request.getParameter(this.sessionKey);
        String aesKey = apiAccount.getAesKey();
        user = this.apiUserLoginMng.findUser(sessionKey, aesKey, apiAccount.getIvKey());
        return user;
    }

    private Short getStatus(ApiAccount apiAccount, HttpServletRequest request) {
        String sessionKey = request.getParameter(this.sessionKey);
        Short loginStatus = ApiUserLogin.USER_STATUS_LOGOUT;
        if (apiAccount != null) {
            String decryptSessionKey = "";
            String aesKey = apiAccount.getAesKey();
            try {
                decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, apiAccount.getIvKey());
            } catch (Exception localException) {
            }
            if (StringUtils.isNotBlank(decryptSessionKey)) {
                loginStatus = this.apiUserLoginMng.getUserStatus(decryptSessionKey);
            }
        }
        return loginStatus;
    }

    private Object[] validateSign(HttpServletRequest request, WebErrors errors) {
        String sign = request.getParameter(this.sign);
        String appId = request.getParameter(this.appId);
        ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);
        errors = ApiValidate.validateApiAccount(request, errors, apiAccount);
        int code = 0;
        String message = "";
        Object[] result = new Object[2];
        if (errors.hasErrors()) {
            code = 203;
            message = "\"appId not exist or appId disabled\"";
        } else {
            errors = ApiValidate.validateSign(request, errors, apiAccount, sign);

            if (errors.hasErrors()) {
                code = 204;
                message = "\"sign validate error\"";
            }
        }
        result[0] = Integer.valueOf(code);
        result[1] = message;
        return result;
    }

    private void refreshUserLoginActive() {
        ApiUserLogin apiUserLogin = CmsThreadVariable.getApiUserLogin();
        if (apiUserLogin != null) {
            apiUserLogin.setActiveTime(new Timestamp(System.currentTimeMillis()));
            this.apiUserLoginMng.update(apiUserLogin);
        }
    }

    private void userActive(HttpServletRequest request) {
        String sessionKey = request.getParameter(this.sessionKey);
        ApiAccount apiAccount = getApiAccount(request);
        Short status = getStatus(apiAccount, request);
        if (apiAccount != null) {
            String decryptSessionKey = "";
            String aesKey = apiAccount.getAesKey();
            try {
                decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, apiAccount.getIvKey());
            } catch (Exception localException) {
            }
            if (StringUtils.isNotBlank(decryptSessionKey)) {
                this.apiUserLoginMng.userActive(decryptSessionKey);
            }
        }
        if ((apiAccount != null) && (status.equals(ApiUserLogin.USER_STATUS_LOGIN)))
            this.apiUserLoginMng.userActive(sessionKey);
    }

    private boolean hasUrlPerm(Website site, ShopAdmin user, String url) {
        Set perms = getUserPermission(site, user);
        if (perms == null) {
            return true;
        }
        Iterator it = perms.iterator();
        while (it.hasNext()) {
            String perm = (String) it.next();
            if ((perm.equals("/*")) || (perm.endsWith(url))) {
                return true;
            }
        }

        return false;
    }

    private Set<String> getUserPermission(Website site, ShopAdmin user) {
        Set<String> perms = user.getPerms();
        Set userPermission = new HashSet();
        if (perms != null) {
            for (String perm : perms) {
                if (perm.contains(":")) {
                    perm = perm.replace(":", "/").replace("*", "");
                }
                userPermission.add(perm);
            }
        }
        return userPermission;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}

