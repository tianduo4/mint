package com.mint.cms.manager;

import com.mint.cms.entity.ShopPlug;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShopPlugMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<ShopPlug> getList(String paramString, Boolean paramBoolean);

    public abstract ShopPlug findById(Long paramLong);

    public abstract ShopPlug findByPath(String paramString);

    public abstract ShopPlug save(ShopPlug paramShopPlug);

    public abstract ShopPlug update(ShopPlug paramShopPlug);

    public abstract ShopPlug deleteById(Long paramLong);

    public abstract ShopPlug[] deleteByIds(Long[] paramArrayOfLong);
}

