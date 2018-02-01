package com.mint.common.web.freemarker;

import freemarker.template.TemplateModelException;

public class MustNumberException extends TemplateModelException {
    public MustNumberException(String paramName) {
        super("The \"" + paramName + "\" parameter must be a number.");
    }
}

