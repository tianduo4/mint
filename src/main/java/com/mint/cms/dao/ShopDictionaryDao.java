package com.mint.cms.dao;

import com.mint.cms.entity.ShopDictionary;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShopDictionaryDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Pagination getPage(String paramString, Long paramLong, int paramInt1, int paramInt2);

    public abstract ShopDictionary findById(Long paramLong);

    public abstract List<ShopDictionary> getListByType(Long paramLong);

    public abstract ShopDictionary save(ShopDictionary paramShopDictionary);

    public abstract ShopDictionary updateByUpdater(Updater<ShopDictionary> paramUpdater);

    public abstract ShopDictionary deleteById(Long paramLong);
}

