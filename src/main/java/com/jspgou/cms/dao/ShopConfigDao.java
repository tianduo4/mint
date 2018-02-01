package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopConfig;
import com.jspgou.common.hibernate4.Updater;

public abstract interface ShopConfigDao {
    public abstract ShopConfig findById(Long paramLong);

    public abstract ShopConfig save(ShopConfig paramShopConfig);

    public abstract ShopConfig updateByUpdater(Updater<ShopConfig> paramUpdater);

    public abstract ShopConfig deleteById(Long paramLong);
}

