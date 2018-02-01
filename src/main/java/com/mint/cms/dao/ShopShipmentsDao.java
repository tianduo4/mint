package com.mint.cms.dao;

import com.mint.cms.entity.ShopShipments;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShopShipmentsDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ShopShipments findById(Long paramLong);

    public abstract ShopShipments save(ShopShipments paramShopShipments);

    public abstract ShopShipments updateByUpdater(Updater<ShopShipments> paramUpdater);

    public abstract ShopShipments deleteById(Long paramLong);

    public abstract List<ShopShipments> getList(Boolean paramBoolean);
}

