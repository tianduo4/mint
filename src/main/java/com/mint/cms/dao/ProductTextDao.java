package com.mint.cms.dao;

import com.mint.cms.entity.ProductText;
import com.mint.common.hibernate4.Updater;

public abstract interface ProductTextDao {
    public abstract ProductText updateByUpdater(Updater<ProductText> paramUpdater);

    public abstract ProductText save(ProductText paramProductText);
}

