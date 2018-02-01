package com.jspgou.cms.entity.base;

import java.io.Serializable;

public abstract class BaseProductPicture
        implements Serializable {
    public static String REF = "ProductPicture";
    public static String PROP_BIG_PICTURE_PATH = "bigPicturePath";
    public static String PROP_APP_PICTURE_PATH = "appPicturePath";
    public static String PROP_PICTURE_PATH = "picturePath";
    private String picturePath;
    private String bigPicturePath;
    private String appPicturePath;

    public BaseProductPicture() {
        initialize();
    }

    public BaseProductPicture(String picturePath, String bigPicturePath, String appPicturePath) {
        setPicturePath(picturePath);
        setBigPicturePath(bigPicturePath);
        setAppPicturePath(appPicturePath);
        initialize();
    }

    protected void initialize() {
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getBigPicturePath() {
        return this.bigPicturePath;
    }

    public void setBigPicturePath(String bigPicturePath) {
        this.bigPicturePath = bigPicturePath;
    }

    public String getAppPicturePath() {
        return this.appPicturePath;
    }

    public void setAppPicturePath(String appPicturePath) {
        this.appPicturePath = appPicturePath;
    }

    public String toString() {
        return super.toString();
    }
}

