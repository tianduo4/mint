package com.mint.cms.dao;

import com.mint.cms.entity.ShopConfig;
import com.mint.common.hibernate4.Updater;

public abstract interface ShopConfigDao {
    public abstract ShopConfig findById(Long paramLong);

    public abstract ShopConfig save(ShopConfig paramShopConfig);

    public abstract ShopConfig updateByUpdater(Updater<ShopConfig> paramUpdater);

    public abstract ShopConfig deleteById(Long paramLong);
}

