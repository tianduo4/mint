package com.jspgou.common.web.freemarker;

import freemarker.template.TemplateModelException;

public class MustDateException extends TemplateModelException {
    public MustDateException(String paramName) {
        super("The \"" + paramName + "\" parameter must be a date.");
    }
}

