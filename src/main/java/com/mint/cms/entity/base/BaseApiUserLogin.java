package com.mint.cms.entity.base;

import com.mint.cms.entity.ApiUserLogin;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseApiUserLogin
        implements Serializable {
    public static String REF = "ApiUserLogin";
    public static String PROP_LOGIN_COUNT = "loginCount";
    public static String PROP_LOGIN_TIME = "loginTime";
    public static String PROP_SESSION_KEY = "sessionKey";
    public static String PROP_USERNAME = "username";
    public static String PROP_ACTIVETIME = "activeTime";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String sessionKey;
    private String username;
    private Date loginTime;
    private Integer loginCount;
    private Date activeTime;

    public BaseApiUserLogin() {
        initialize();
    }

    public BaseApiUserLogin(Long id) {
        setId(id);
        initialize();
    }

    public BaseApiUserLogin(Long id, String username, Date loginTime, Integer loginCount, Date actvieTime) {
        setId(id);
        setUsername(username);
        setLoginTime(loginTime);
        setLoginCount(loginCount);
        setActiveTime(actvieTime);
        initialize();
    }

    protected void initialize() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getSessionKey() {
        return this.sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getLoginCount() {
        return this.loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ApiUserLogin)) return false;

        ApiUserLogin apiUserLogin = (ApiUserLogin) obj;
        if ((getId() == null) || (apiUserLogin.getId() == null)) return false;
        return getId().equals(apiUserLogin.getId());
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

