package com.jspgou.common.web.freemarker;

import freemarker.template.TemplateModelException;

public class MustStringException extends TemplateModelException {
    public MustStringException(String paramName) {
        super("The \"" + paramName + "\" parameter must be a string.");
    }
}

