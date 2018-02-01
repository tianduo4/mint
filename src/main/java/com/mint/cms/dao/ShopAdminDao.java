package com.mint.cms.dao;

import com.mint.cms.entity.ShopAdmin;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface ShopAdminDao {
    public abstract ShopAdmin getByUsername(String paramString);

    public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

    public abstract ShopAdmin findById(Long paramLong);

    public abstract ShopAdmin save(ShopAdmin paramShopAdmin);

    public abstract ShopAdmin updateByUpdater(Updater<ShopAdmin> paramUpdater);

    public abstract ShopAdmin deleteById(Long paramLong);

    public abstract ShopAdmin findByName(String paramString);
}

