package com.jspgou.common.web.springmvc;

import java.util.Date;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class BindingInitializer
        implements WebBindingInitializer {
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new DateTypeEditor());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }
}

