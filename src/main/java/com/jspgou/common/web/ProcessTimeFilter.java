package com.jspgou.common.web;

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

public class ProcessTimeFilter
        implements Filter {
    protected final Logger log = LoggerFactory.getLogger(ProcessTimeFilter.class);
    public static final String START_TIME = "_start_time";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        long time = System.currentTimeMillis();
        request.setAttribute("_start_time", Long.valueOf(time));
        chain.doFilter(request, response);
        time = System.currentTimeMillis() - time;
        this.log.debug("process in {} ms: {}", Long.valueOf(time), request.getRequestURI());
    }

    public void init(FilterConfig arg0)
            throws ServletException {
    }
}

