package com.mint.cms.manager;

import com.mint.cms.entity.ShopDictionaryType;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShopDictionaryTypeMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ShopDictionaryType findById(Long paramLong);

    public abstract List<ShopDictionaryType> findAll();

    public abstract ShopDictionaryType save(ShopDictionaryType paramShopDictionaryType);

    public abstract ShopDictionaryType update(ShopDictionaryType paramShopDictionaryType);

    public abstract ShopDictionaryType deleteById(Long paramLong);

    public abstract ShopDictionaryType[] deleteByIds(Long[] paramArrayOfLong);
}

