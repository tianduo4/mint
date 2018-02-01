package com.mint.plug.weixin.entity.base;

import com.mint.core.entity.Website;
import com.mint.plug.weixin.entity.Weixin;

import java.io.Serializable;

public abstract class BaseWeixin
        implements Serializable {
    public static String REF = "Weixin";
    public static String PROP_SITE = "site";
    public static String PROP_ID = "id";
    public static String PROP_PIC = "pic";

    private int hashCode = -2147483648;
    private Long id;
    private String welcome;
    private String pic;
    private String appKey;
    private String appSecret;
    private String token;
    private Website site;

    public BaseWeixin() {
        initialize();
    }

    public BaseWeixin(Long id) {
        setId(id);
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

    public String getWelcome() {
        return this.welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Website getSite() {
        return this.site;
    }

    public void setSite(Website site) {
        this.site = site;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Weixin)) return false;

        Weixin weixin = (Weixin) obj;
        if ((getId() == null) || (weixin.getId() == null)) return false;
        return getId().equals(weixin.getId());
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

