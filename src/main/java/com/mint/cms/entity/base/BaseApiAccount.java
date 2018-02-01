package com.mint.cms.entity.base;

import com.mint.cms.entity.ApiAccount;

import java.io.Serializable;

public abstract class BaseApiAccount
        implements Serializable {
    public static String REF = "ApiAccount";
    public static String PROP_AES_KEY = "aesKey";
    public static String PROP_DISABLED = "disabled";
    public static String PROP_IV_KEY = "ivKey";
    public static String PROP_APP_ID = "appId";
    public static String PROP_ID = "id";
    public static String PROP_APP_KEY = "appKey";

    private int hashCode = -2147483648;
    private Long id;
    private String appId;
    private String appKey;
    private String aesKey;
    private boolean disabled;
    private String ivKey;

    public BaseApiAccount() {
        initialize();
    }

    public BaseApiAccount(Long id) {
        setId(id);
        initialize();
    }

    public BaseApiAccount(Long id, String appId, String appKey, String aesKey, boolean disabled) {
        setId(id);
        setAppId(appId);
        setAppKey(appKey);
        setAesKey(aesKey);
        setDisabled(disabled);
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

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAesKey() {
        return this.aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getIvKey() {
        return this.ivKey;
    }

    public void setIvKey(String ivKey) {
        this.ivKey = ivKey;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ApiAccount)) return false;

        ApiAccount apiAccount = (ApiAccount) obj;
        if ((getId() == null) || (apiAccount.getId() == null)) return false;
        return getId().equals(apiAccount.getId());
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

