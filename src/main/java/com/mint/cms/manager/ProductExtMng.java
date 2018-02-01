package com.mint.cms.manager;

import com.mint.cms.entity.Product;
import com.mint.cms.entity.ProductExt;

public abstract interface ProductExtMng {
    public abstract ProductExt save(ProductExt paramProductExt, Product paramProduct);

    public abstract ProductExt saveOrUpdate(ProductExt paramProductExt, Product paramProduct);
}

