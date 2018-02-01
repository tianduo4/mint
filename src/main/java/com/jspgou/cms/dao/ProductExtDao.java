package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ProductExt;
import com.jspgou.common.hibernate4.Updater;

public abstract interface ProductExtDao {
    public abstract ProductExt findById(Long paramLong);

    public abstract ProductExt save(ProductExt paramProductExt);

    public abstract ProductExt updateByUpdater(Updater<ProductExt> paramUpdater);
}

