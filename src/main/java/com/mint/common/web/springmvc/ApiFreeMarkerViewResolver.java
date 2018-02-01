package com.mint.common.web.springmvc;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class ApiFreeMarkerViewResolver extends AbstractTemplateViewResolver {
    public ApiFreeMarkerViewResolver() {
        setViewClass(ApiFreeMarkerView.class);
    }

    protected AbstractUrlBasedView buildView(String viewName)
            throws Exception {
        AbstractUrlBasedView view = super.buildView(viewName);

        if (viewName.startsWith("/")) {
            view.setUrl(viewName + getSuffix());
        }
        return view;
    }
}

