package com.mint.common.web.springmvc;

import com.mint.cms.web.threadvariable.AdminThread;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class RichFreeMarkerView extends FreeMarkerView {
    public static final String CONTEXT_PATH = "base";
    public static final String SSO_ENABLE = "ssoEnable";
    public static final String USER = "user";

    protected void exposeHelpers(Map model, HttpServletRequest request)
            throws Exception {
        super.exposeHelpers(model, request);
        model.put("base", request.getContextPath());
        Website web = SiteUtils.getWeb(request);
        model.put("ssoEnable", web.getSsoEnable());
        model.put("user", AdminThread.get());
    }
}

