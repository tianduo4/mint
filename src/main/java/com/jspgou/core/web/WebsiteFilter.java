package com.jspgou.core.web;

import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebsiteFilter
        implements Filter {
    private WebsiteMng websiteMng;

    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletrequest;
        HttpServletResponse response = (HttpServletResponse) servletresponse;
        String s = request.getServerName();
        Website website = this.websiteMng.getWebsite(s);
        if (website != null) {
            request.setAttribute("_web_key", website);
            String s1 = website.getBaseDomain();
            if (!StringUtils.isBlank(s1)) {
                request.setAttribute("_base_domain_key", s1);
            }
            filterchain.doFilter(request, response);
        } else {
            response.sendError(404, "domain '" + s + "' not found.");
        }
    }

    public void init(FilterConfig filterconfig) throws ServletException {
        WebApplicationContext webapplicationcontext = WebApplicationContextUtils.getWebApplicationContext(filterconfig.getServletContext());
        this.websiteMng = ((WebsiteMng) BeanFactoryUtils.beanOfTypeIncludingAncestors(webapplicationcontext, WebsiteMng.class, true, false));
    }

    public void destroy() {
    }
}

