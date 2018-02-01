package com.jspgou.core.web.front;

import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontEncodingFilter
        implements Filter {
    private static final Logger log = LoggerFactory.getLogger(FrontEncodingFilter.class);
    public static final String AJAX_HEAD = "isAjax";

    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        HttpServletRequest httpservletrequest = (HttpServletRequest) servletrequest;
        Website website = SiteUtils.getWeb(httpservletrequest);
        String s = httpservletrequest.getHeader("isAjax");
        if ((s != null) && ("true".equals(s))) {
            httpservletrequest.setCharacterEncoding("UTF-8");
            log.debug("ajax request");
        } else {
            httpservletrequest.setCharacterEncoding(website.getFrontEncoding());
            servletresponse.setContentType(website.getFrontContentType());
        }
        filterchain.doFilter(httpservletrequest, servletresponse);
    }

    public void init(FilterConfig filterconfig)
            throws ServletException {
    }

    public void destroy() {
    }
}

