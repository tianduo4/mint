package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseProductPicture;

public class ProductPicture extends BaseProductPicture {
    private static final long serialVersionUID = 1L;

    public ProductPicture() {
    }

    public ProductPicture(String picturePath, String bigPicturePath, String appPicturePath) {
        super(picturePath,
                bigPicturePath,
                appPicturePath);
    }
}

