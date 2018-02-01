package com.jspgou.cms.entity.base;

import java.io.Serializable;

public abstract class BaseProductExended
        implements Serializable {
    public static String REF = "ProductExended";
    public static String PROP_NAME = "name";
    public static String PROP_VALUE = "value";
    private String name;
    private String value;

    public BaseProductExended() {
        initialize();
    }

    public BaseProductExended(String name) {
        setName(name);
        initialize();
    }

    protected void initialize() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return super.toString();
    }
}

