package com.mint.cms.dao;

import com.mint.cms.entity.ShopChannelContent;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface ShopChannelContentDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ShopChannelContent findById(Long paramLong);

    public abstract ShopChannelContent save(ShopChannelContent paramShopChannelContent);

    public abstract ShopChannelContent updateByUpdater(Updater<ShopChannelContent> paramUpdater);

    public abstract ShopChannelContent deleteById(Long paramLong);
}

