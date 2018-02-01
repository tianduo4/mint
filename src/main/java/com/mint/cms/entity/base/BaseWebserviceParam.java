package com.mint.cms.entity.base;

import java.io.Serializable;

public abstract class BaseWebserviceParam
        implements Serializable {
    public static String REF = "WebserviceParam";
    public static String PROP_PARAM_NAME = "paramName";
    public static String PROP_DEFAULT_VALUE = "defaultValue";
    private String paramName;
    private String defaultValue;

    public BaseWebserviceParam() {
        initialize();
    }

    public BaseWebserviceParam(String paramName) {
        setParamName(paramName);
        initialize();
    }

    protected void initialize() {
    }

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String toString() {
        return super.toString();
    }
}

