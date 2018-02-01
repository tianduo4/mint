package com.jspgou.plug.weixin.entity.base;

import com.jspgou.core.entity.Website;
import com.jspgou.plug.weixin.entity.WeixinMenu;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseWeixinMenu
        implements Serializable {
    public static String REF = "WeixinMenu";
    public static String PROP_NAME = "name";
    public static String PROP_PARENT = "parent";
    public static String PROP_SITE = "site";
    public static String PROP_KEY = "key";
    public static String PROP_URL = "url";
    public static String PROP_TYPE = "type";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Integer id;
    private String name;
    private String type;
    private String url;
    private String key;
    private WeixinMenu parent;
    private Website site;
    private Set<WeixinMenu> child;

    public BaseWeixinMenu() {
        initialize();
    }

    public BaseWeixinMenu(Integer id) {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public WeixinMenu getParent() {
        return this.parent;
    }

    public void setParent(WeixinMenu parent) {
        this.parent = parent;
    }

    public Website getSite() {
        return this.site;
    }

    public void setSite(Website site) {
        this.site = site;
    }

    public Set<WeixinMenu> getChild() {
        return this.child;
    }

    public void setChild(Set<WeixinMenu> child) {
        this.child = child;
    }

    public void addTochild(WeixinMenu weixinMenu) {
        if (getChild() == null) setChild(new TreeSet());
        getChild().add(weixinMenu);
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof WeixinMenu)) return false;

        WeixinMenu weixinMenu = (WeixinMenu) obj;
        if ((getId() == null) || (weixinMenu.getId() == null)) return false;
        return getId().equals(weixinMenu.getId());
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

