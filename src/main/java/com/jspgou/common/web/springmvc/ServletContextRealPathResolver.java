package com.jspgou.common.web.springmvc;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class ServletContextRealPathResolver
        implements RealPathResolver, ServletContextAware {
    private ServletContext context;

    public String get(String path) {
        return this.context.getRealPath(path);
    }

    public void setServletContext(ServletContext servletContext) {
        this.context = servletContext;
    }
}

