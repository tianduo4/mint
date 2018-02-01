package com.jspgou.cms.entity.base;

import java.io.Serializable;

public abstract class BaseOrderReturnPicture
        implements Serializable {
    public static String REF = "OrderReturnPicture";
    public static String PROP_DESCRIPTION = "description";
    public static String PROP_IMG_PATH = "imgPath";
    private String imgPath;
    private String description;

    public BaseOrderReturnPicture() {
        initialize();
    }

    public BaseOrderReturnPicture(String imgPath) {
        setImgPath(imgPath);
        initialize();
    }

    protected void initialize() {
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return super.toString();
    }
}

