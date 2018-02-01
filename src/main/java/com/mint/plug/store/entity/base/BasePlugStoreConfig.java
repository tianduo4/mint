package com.mint.plug.store.entity.base;

import com.mint.plug.store.entity.PlugStoreConfig;

import java.io.Serializable;

public abstract class BasePlugStoreConfig
        implements Serializable {
    public static String REF = "PlugStoreConfig";
    public static String PROP_MAINTAIN_COMPANY = "maintainCompany";
    public static String PROP_SERVER_URL = "serverUrl";
    public static String PROP_MAINTAIN_TEL = "maintainTel";
    public static String PROP_PASSWOD = "passwod";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Integer id;
    private String serverUrl;
    private String password;

    public BasePlugStoreConfig() {
        initialize();
    }

    public BasePlugStoreConfig(Integer id) {
        setId(id);
        initialize();
    }

    public BasePlugStoreConfig(Integer id, String serverUrl, String passwod) {
        setId(id);
        setServerUrl(serverUrl);
        setPassword(passwod);
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

    public String getServerUrl() {
        return this.serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof PlugStoreConfig)) return false;

        PlugStoreConfig plugStoreConfig = (PlugStoreConfig) obj;
        if ((getId() == null) || (plugStoreConfig.getId() == null)) return false;
        return getId().equals(plugStoreConfig.getId());
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

