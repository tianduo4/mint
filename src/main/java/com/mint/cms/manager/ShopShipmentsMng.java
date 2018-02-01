package com.mint.cms.manager;

import com.mint.cms.entity.ShopShipments;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShopShipmentsMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ShopShipments findById(Long paramLong);

    public abstract ShopShipments save(ShopShipments paramShopShipments);

    public abstract ShopShipments update(ShopShipments paramShopShipments);

    public abstract ShopShipments deleteById(Long paramLong);

    public abstract ShopShipments[] deleteByIds(Long[] paramArrayOfLong);

    public abstract List<ShopShipments> getList(Boolean paramBoolean);
}

