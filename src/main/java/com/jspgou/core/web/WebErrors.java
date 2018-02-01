package com.jspgou.core.web;

import java.io.Serializable;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

public class WebErrors extends com.jspgou.common.web.springmvc.WebErrors {
    public static final String ERROR_PAGE = "/common/error_message";
    public static final String ERROR_ATTR_NAME = "errors";

    public static WebErrors create(HttpServletRequest request) {
        return new WebErrors(request);
    }

    public WebErrors() {
    }

    public WebErrors(HttpServletRequest request) {
        super(request);
    }

    public WebErrors(MessageSource messagesource, Locale locale) {
        super(messagesource, locale);
    }

    public void notInWeb(Class class1, Serializable serializable) {
        addErrorCode("error.notInWeb", new Object[]{
                class1.getSimpleName(), serializable});
    }

    protected String getErrorAttrName() {
        return "errors";
    }

    protected String getErrorPage() {
        return "/common/error_message";
    }
}

