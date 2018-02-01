package com.jspgou.common.web.springmvc;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class ApiFreeMarkerView extends FreeMarkerView {
    public static final String CONTEXT_PATH = "base";
    public static final String SSO_ENABLE = "ssoEnable";
    public static final String USER = "user";

    protected void exposeHelpers(Map model, HttpServletRequest request)
            throws Exception {
        super.exposeHelpers(model, request);
    }
}

