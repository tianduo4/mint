package com.mint.cms.manager;

import com.mint.cms.entity.ProductText;

public abstract interface ProductTextMng {
    public abstract ProductText update(ProductText paramProductText);

    public abstract ProductText save(ProductText paramProductText);
}

