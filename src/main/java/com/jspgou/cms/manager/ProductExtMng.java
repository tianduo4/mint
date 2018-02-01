package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductExt;

public abstract interface ProductExtMng {
    public abstract ProductExt save(ProductExt paramProductExt, Product paramProduct);

    public abstract ProductExt saveOrUpdate(ProductExt paramProductExt, Product paramProduct);
}

