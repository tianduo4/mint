package com.mint.plug.weixin.entity.base;

import com.mint.core.entity.Website;
import com.mint.plug.weixin.entity.WeixinMessage;

import java.io.Serializable;

public abstract class BaseWeixinMessage
        implements Serializable {
    public static String REF = "WeixinMessage";
    public static String PROP_SITE = "site";
    public static String PROP_URL = "url";
    public static String PROP_NUMBER = "number";
    public static String PROP_ID = "id";
    public static String PROP_CONTENT = "content";
    public static String PROP_WELCOME = "welcome";
    public static String PROP_TITLE = "title";
    public static String PROP_PATH = "path";

    private int hashCode = -2147483648;
    private Integer id;
    private String number;
    private String title;
    private String path;
    private String url;
    private String content;
    private Boolean welcome;
    private Integer type;
    private Website site;

    public BaseWeixinMessage() {
        initialize();
    }

    public BaseWeixinMessage(Integer id) {
        setId(id);
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

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isWelcome() {
        return this.welcome;
    }

    public void setWelcome(Boolean welcome) {
        this.welcome = welcome;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Website getSite() {
        return this.site;
    }

    public void setSite(Website site) {
        this.site = site;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof WeixinMessage)) return false;

        WeixinMessage weixinMessage = (WeixinMessage) obj;
        if ((getId() == null) || (weixinMessage.getId() == null)) return false;
        return getId().equals(weixinMessage.getId());
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

