package com.mint.common.web.freemarker;

import freemarker.template.TemplateModelException;

public class ParamsRequiredException extends TemplateModelException {
    public ParamsRequiredException(String paramName) {
        super("The required \"" + paramName + "\" paramter is missing.");
    }
}

