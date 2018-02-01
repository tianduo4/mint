package com.jspgou.core.web.admin;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.web.threadvariable.AdminThread;
import com.jspgou.core.entity.Admin;

import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminSecureInterceptor extends HandlerInterceptorAdapter {
    private boolean develop = false;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
            throws Exception {
        ShopAdmin admin = AdminThread.get();
        if (this.develop) {
            return true;
        }
        Set set = (Set) request.getAttribute("_permission_key");
        String s = getURI(request.getRequestURI(), request.getContextPath());
        if (s.equals("/login.do")) {
            return true;
        }
        if ((admin != null) && (admin.getAdmin().isSuper())) {
            return true;
        }
        if (set == null) {
            return false;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String str2 = (String) iterator.next();
            if ((s.equals(str2)) || (s.startsWith(str2)))
                return true;
        }
        response.sendError(403);
        return false;
    }

    public static String getURI(String s, String s1) throws IllegalStateException {
        int i = 0;
        int j = 0;
        int k = 2;
        if (!StringUtils.isBlank(s1)) {
            k++;
        }
        for (; (j < k) && (i != -1); j++) {
            i = s.indexOf('/', i + 1);
        }
        if (i <= 0) {
            throw new IllegalStateException("admin access path not like '/jeeadmin/jspgou/...' pattern: " + s);
        }
        return s.substring(i);
    }

    public void setDevelop(boolean develop) {
        this.develop = develop;
    }
}

