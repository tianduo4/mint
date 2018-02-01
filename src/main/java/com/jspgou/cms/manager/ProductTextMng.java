package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ProductText;

public abstract interface ProductTextMng {
    public abstract ProductText update(ProductText paramProductText);

    public abstract ProductText save(ProductText paramProductText);
}

