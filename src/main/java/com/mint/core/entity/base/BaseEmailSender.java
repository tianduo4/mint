package com.mint.core.entity.base;

import java.io.Serializable;

public abstract class BaseEmailSender
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "EmailSender";
    public static String PROP_PASSWORD = "password";
    public static String PROP_HOST = "host";
    public static String PROP_ENCODING = "encoding";
    public static String PROP_PERSONAL = "personal";
    public static String PROP_USERNAME = "username";
    private String host;
    private String encoding;
    private String username;
    private String password;
    private String personal;

    public BaseEmailSender() {
        initialize();
    }

    protected void initialize() {
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonal() {
        return this.personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String toString() {
        return super.toString();
    }
}

