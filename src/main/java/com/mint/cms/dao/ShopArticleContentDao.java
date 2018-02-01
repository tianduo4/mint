package com.mint.cms.dao;

import com.mint.cms.entity.ShopArticleContent;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface ShopArticleContentDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ShopArticleContent findById(Long paramLong);

    public abstract ShopArticleContent save(ShopArticleContent paramShopArticleContent);

    public abstract ShopArticleContent updateByUpdater(Updater<ShopArticleContent> paramUpdater);

    public abstract ShopArticleContent deleteById(Long paramLong);
}

