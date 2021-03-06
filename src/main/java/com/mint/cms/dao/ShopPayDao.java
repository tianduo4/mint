package com.mint.cms.dao;

import com.mint.cms.entity.ShopPay;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface ShopPayDao {
    public abstract Pagination getPageShopPay(int paramInt1, int paramInt2);

    public abstract ShopPay findById(Integer paramInteger);

    public abstract ShopPay save(ShopPay paramShopPay);

    public abstract ShopPay updateByUpdater(Updater<ShopPay> paramUpdater);

    public abstract ShopPay deleteById(Integer paramInteger);
}

