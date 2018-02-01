package com.jspgou.common.web.session;

import com.jspgou.common.web.session.cache.SessionCache;
import com.jspgou.common.web.session.id.SessionIdGenerator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class CacheSessionProvider
        implements SessionProvider, InitializingBean {
    public static final String CURRENT_SESSION = "_current_session";
    public static final String CURRENT_SESSION_ID = "_current_session_id";
    private SessionCache sessionCache;
    private SessionIdGenerator sessionIdGenerator;
    private int sessionTimeout;

    public CacheSessionProvider() {
        this.sessionTimeout = 30;
    }

    public Serializable getAttribute(HttpServletRequest request, String name) {
        Map session = (Map) request
                .getAttribute("_current_session");
        if (session != null) {
            return (Serializable) session.get(name);
        }
        String root = (String) request.getAttribute("_current_session_id");
        if (root == null) {
            root = request.getRequestedSessionId();
        }
        if (StringUtils.isBlank(root)) {
            request.setAttribute("_current_session",
                    new HashMap());
            return null;
        }
        session = this.sessionCache.getSession(root);
        if (session != null) {
            request.setAttribute("_current_session_id", root);
            request.setAttribute("_current_session", session);
            return (Serializable) session.get(name);
        }
        return null;
    }

    public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value) {
        Map session = (Map) request
                .getAttribute("_current_session");
        String root;
        if (session == null) {
            root = request.getRequestedSessionId();
            if ((root != null) && (root.length() == 32)) {
                session = this.sessionCache.getSession(root);
            }
            if (session == null) {
                session = new HashMap();
                do
                    root = this.sessionIdGenerator.get();
                while (this.sessionCache.exist(root));
                response.addCookie(createCookie(request, root));
            }
            request.setAttribute("_current_session", session);
            request.setAttribute("_current_session_id", root);
        } else {
            root = (String) request.getAttribute("_current_session_id");
            if (root == null) {
                do
                    root = this.sessionIdGenerator.get();
                while (this.sessionCache.exist(root));
                response.addCookie(createCookie(request, root));
                request.setAttribute("_current_session_id", root);
            }
        }
        session.put(name, value);
        this.sessionCache.setSession(root, session, this.sessionTimeout);
    }

    public String getSessionId(HttpServletRequest request, HttpServletResponse response) {
        String root = (String) request.getAttribute("_current_session_id");
        if (root != null) {
            return root;
        }
        root = request.getRequestedSessionId();
        if ((root == null) || (root.length() != 32) || (!this.sessionCache.exist(root))) {
            do
                root = this.sessionIdGenerator.get();
            while (this.sessionCache.exist(root));
            this.sessionCache.setSession(root, new HashMap(),
                    this.sessionTimeout);
            response.addCookie(createCookie(request, root));
        }
        request.setAttribute("_current_session_id", root);
        return root;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.removeAttribute("_current_session");
        request.removeAttribute("_current_session_id");
        String root = request.getRequestedSessionId();
        if (!StringUtils.isBlank(root)) {
            this.sessionCache.clear(root);
            Cookie cookie = createCookie(request, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    private Cookie createCookie(HttpServletRequest request, String value) {
        Cookie cookie = new Cookie("JSESSIONID", value);
        String ctx = request.getContextPath();
        cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
        return cookie;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.sessionCache);
        Assert.notNull(this.sessionIdGenerator);
    }

    public void setSessionCache(SessionCache sessionCache) {
        this.sessionCache = sessionCache;
    }

    public void setSessionTimeout(int sessionTimeout) {
        Assert.isTrue(sessionTimeout > 0);
        this.sessionTimeout = sessionTimeout;
    }

    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }
}

