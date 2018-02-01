package com.jspgou.common.web.springmvc;

import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

public class SimpleFreeMarkerView extends AbstractTemplateView {
    public static final String CONTEXT_PATH = "base";
    public static final String SSO_ENABLE = "ssoEnable";
    public static final String USER = "user";
    private Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }

    protected FreeMarkerConfig autodetectConfiguration()
            throws BeansException {
        try {
            return
                    (FreeMarkerConfig) BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(),
                            FreeMarkerConfig.class, true, false);
        } catch (NoSuchBeanDefinitionException ex) {
            throw new ApplicationContextException(
                    "Must define a single FreeMarkerConfig bean in this web application context (may be inherited): FreeMarkerConfigurer is the usual implementation. This bean may be given any name.",
                    ex);
        }
    }

    protected void initApplicationContext()
            throws BeansException {
        super.initApplicationContext();

        if (getConfiguration() == null) {
            FreeMarkerConfig freemarkerconfig = autodetectConfiguration();
            setConfiguration(freemarkerconfig.getConfiguration());
        }
        checkTemplate();
    }

    protected void checkTemplate()
            throws ApplicationContextException {
        try {
            getConfiguration().getTemplate(getUrl());
        } catch (ParseException ex) {
            throw new ApplicationContextException(
                    "Failed to parse FreeMarker template for URL [" + getUrl() +
                            "]", ex);
        } catch (IOException ex) {
            throw new ApplicationContextException(
                    "Could not load FreeMarker template for URL [" + getUrl() +
                            "]", ex);
        }
    }

    protected void renderMergedTemplateModel(Map model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        model.put("base", request.getContextPath());
        Website web = SiteUtils.getWeb(request);
        model.put("ssoEnable", web.getSsoEnable());
        model.put("user", MemberThread.get());
        getConfiguration().getTemplate(getUrl()).process(model,
                response.getWriter());
    }
}

