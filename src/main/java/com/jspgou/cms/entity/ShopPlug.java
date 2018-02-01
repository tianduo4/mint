package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseShopPlug;

import java.util.Date;

public class ShopPlug extends BaseShopPlug {
    private static final long serialVersionUID = 1L;

    public boolean getUsed() {
        return isUsed();
    }

    public boolean getFileConflict() {
        return isFileConflict();
    }

    public boolean getCanInstall() {
        return (!getUsed()) && (!getFileConflict());
    }

    public boolean getCanUnInstall() {
        return (getUsed()) && (!getFileConflict());
    }

    public ShopPlug() {
    }

    public ShopPlug(Long id) {
        super(id);
    }

    public ShopPlug(Long id, String name, String path, Date uploadTime, boolean fileConflict, boolean used) {
        super(id,
                name,
                path,
                uploadTime,
                fileConflict,
                used);
    }
}

