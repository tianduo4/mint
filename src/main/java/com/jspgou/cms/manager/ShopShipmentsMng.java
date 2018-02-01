package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopShipments;
import com.jspgou.common.page.Pagination;

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

