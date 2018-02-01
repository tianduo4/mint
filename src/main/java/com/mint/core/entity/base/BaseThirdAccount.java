package com.mint.core.entity.base;

import com.mint.core.entity.ThirdAccount;

import java.io.Serializable;

public abstract class BaseThirdAccount
        implements Serializable {
    public static String REF = "ThirdAccount";
    public static String PROP_SOURCE = "source";
    public static String PROP_ACCOUNT_KEY = "accountKey";
    public static String PROP_THIRD_LOGIN_NAME = "thirdLoginName";
    public static String PROP_USERNAME = "username";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Long id;
    private String accountKey;
    private String username;
    private String source;
    private String thirdLoginName;

    public BaseThirdAccount() {
        initialize();
    }

    public BaseThirdAccount(Long id) {
        setId(id);
        initialize();
    }

    public BaseThirdAccount(Long id, String accountKey, String username, String source) {
        setId(id);
        setAccountKey(accountKey);
        setUsername(username);
        setSource(source);
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

    public String getAccountKey() {
        return this.accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getThirdLoginName() {
        return this.thirdLoginName;
    }

    public void setThirdLoginName(String thirdLoginName) {
        this.thirdLoginName = thirdLoginName;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ThirdAccount)) return false;

        ThirdAccount thirdAccount = (ThirdAccount) obj;
        if ((getId() == null) || (thirdAccount.getId() == null)) return false;
        return getId().equals(thirdAccount.getId());
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

