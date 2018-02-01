package com.mint.core.entity.base;

import com.mint.core.entity.Log;
import com.mint.core.entity.User;
import com.mint.core.entity.Website;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseLog
        implements Serializable {
    public static String REF = "Log";
    public static String PROP_USER = "user";
    public static String PROP_IP = "ip";
    public static String PROP_CATEGORY = "category";
    public static String PROP_SITE = "site";
    public static String PROP_TIME = "time";
    public static String PROP_URL = "url";
    public static String PROP_ID = "id";
    public static String PROP_CONTENT = "content";
    public static String PROP_TITLE = "title";

    private int hashCode = -2147483648;
    private Long id;
    private Integer category;
    private Date time;
    private String ip;
    private String url;
    private String title;
    private String content;
    private User user;
    private Website site;

    public BaseLog() {
        initialize();
    }

    public BaseLog(Long id) {
        setId(id);
        initialize();
    }

    public BaseLog(Long id, Integer category, Date time) {
        setId(id);
        setCategory(category);
        setTime(time);
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

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Website getSite() {
        return this.site;
    }

    public void setSite(Website site) {
        this.site = site;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Log)) return false;

        Log log = (Log) obj;
        if ((getId() == null) || (log.getId() == null)) return false;
        return getId().equals(log.getId());
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

