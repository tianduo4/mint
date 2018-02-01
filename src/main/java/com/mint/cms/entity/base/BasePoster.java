package com.mint.cms.entity.base;

import com.mint.cms.entity.Poster;

import java.io.Serializable;
import java.util.Date;

public abstract class BasePoster
        implements Serializable {
    public static String REF = "Poster";
    public static String PROP_TIME = "time";
    public static String PROP_PIC_URL = "picUrl";
    public static String PROP_INTER_URL = "interUrl";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Integer id;
    private String picUrl;
    private Date time;
    private String interUrl;

    public BasePoster() {
        initialize();
    }

    public BasePoster(Integer id) {
        setId(id);
        initialize();
    }

    public BasePoster(Integer id, String picUrl) {
        setId(id);
        setPicUrl(picUrl);
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

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getInterUrl() {
        return this.interUrl;
    }

    public void setInterUrl(String interUrl) {
        this.interUrl = interUrl;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Poster)) return false;

        Poster poster = (Poster) obj;
        if ((getId() == null) || (poster.getId() == null)) return false;
        return getId().equals(poster.getId());
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

