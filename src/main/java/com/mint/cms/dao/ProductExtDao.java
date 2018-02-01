package com.mint.cms.dao;

import com.mint.cms.entity.ProductExt;
import com.mint.common.hibernate4.Updater;

public abstract interface ProductExtDao {
    public abstract ProductExt findById(Long paramLong);

    public abstract ProductExt save(ProductExt paramProductExt);

    public abstract ProductExt updateByUpdater(Updater<ProductExt> paramUpdater);
}

