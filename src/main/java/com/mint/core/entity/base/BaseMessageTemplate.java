package com.mint.core.entity.base;

import java.io.Serializable;

public abstract class BaseMessageTemplate
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "MessageTemplate";
    public static String PROP_SUBJECT = "subject";
    public static String PROP_TEXT = "text";
    private String subject;
    private String text;
    private String activeTitle;
    private String activeTxt;

    public BaseMessageTemplate() {
        initialize();
    }

    protected void initialize() {
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getActiveTitle() {
        return this.activeTitle;
    }

    public void setActiveTitle(String activeTitle) {
        this.activeTitle = activeTitle;
    }

    public String getActiveTxt() {
        return this.activeTxt;
    }

    public void setActiveTxt(String activeTxt) {
        this.activeTxt = activeTxt;
    }

    public String toString() {
        return super.toString();
    }
}

