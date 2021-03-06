package com.mint.cms.manager;

import com.mint.cms.entity.ShopDictionary;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShopDictionaryMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Pagination getPage(String paramString, Long paramLong, int paramInt1, int paramInt2);

    public abstract ShopDictionary findById(Long paramLong);

    public abstract List<ShopDictionary> getListByType(Long paramLong);

    public abstract ShopDictionary save(ShopDictionary paramShopDictionary);

    public abstract ShopDictionary update(ShopDictionary paramShopDictionary);

    public abstract ShopDictionary deleteById(Long paramLong);

    public abstract ShopDictionary[] deleteByIds(Long[] paramArrayOfLong);

    public abstract ShopDictionary[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);
}

