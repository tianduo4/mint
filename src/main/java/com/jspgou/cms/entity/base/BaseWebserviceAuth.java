package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.WebserviceAuth;
import com.jspgou.cms.entity.WebserviceCallRecord;

import java.io.Serializable;
import java.util.Set;

public abstract class BaseWebserviceAuth
        implements Serializable {
    public static String REF = "WebserviceAuth";
    public static String PROP_ENABLE = "enable";
    public static String PROP_PASSWORD = "password";
    public static String PROP_USERNAME = "username";
    public static String PROP_ID = "id";
    public static String PROP_SYSTEM = "system";

    private int hashCode = -2147483648;
    private Integer id;
    private String username;
    private String password;
    private String system;
    private boolean enable;
    private Set<WebserviceCallRecord> webserviceCallRecords;

    public BaseWebserviceAuth() {
        initialize();
    }

    public BaseWebserviceAuth(Integer id) {
        setId(id);
        initialize();
    }

    public BaseWebserviceAuth(Integer id, String username, String password, String system, boolean enable) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setSystem(system);
        setEnable(enable);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
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

    public String getSystem() {
        return this.system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Set<WebserviceCallRecord> getWebserviceCallRecords() {
        return this.webserviceCallRecords;
    }

    public void setWebserviceCallRecords(Set<WebserviceCallRecord> webserviceCallRecords) {
        this.webserviceCallRecords = webserviceCallRecords;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof WebserviceAuth)) return false;

        WebserviceAuth webserviceAuth = (WebserviceAuth) obj;
        if ((getId() == null) || (webserviceAuth.getId() == null)) return false;
        return getId().equals(webserviceAuth.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (getId() == null) return super.hashCode();

            String hashStr = getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

