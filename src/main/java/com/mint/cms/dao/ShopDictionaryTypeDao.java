package com.mint.cms.dao;

import com.mint.cms.entity.ShopDictionaryType;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShopDictionaryTypeDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ShopDictionaryType findById(Long paramLong);

    public abstract List<ShopDictionaryType> findAll();

    public abstract ShopDictionaryType save(ShopDictionaryType paramShopDictionaryType);

    public abstract ShopDictionaryType updateByUpdater(Updater<ShopDictionaryType> paramUpdater);

    public abstract ShopDictionaryType deleteById(Long paramLong);
}

