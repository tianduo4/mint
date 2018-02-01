package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Brand;
import com.jspgou.cms.entity.BrandText;

public abstract interface BrandTextMng {
    public abstract BrandText save(Brand paramBrand, String paramString);

    public abstract BrandText update(BrandText paramBrandText);
}

