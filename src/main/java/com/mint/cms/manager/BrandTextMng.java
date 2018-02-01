package com.mint.cms.manager;

import com.mint.cms.entity.Brand;
import com.mint.cms.entity.BrandText;

public abstract interface BrandTextMng {
    public abstract BrandText save(Brand paramBrand, String paramString);

    public abstract BrandText update(BrandText paramBrandText);
}

