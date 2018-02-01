package com.mint.cms.dao;

import com.mint.cms.entity.ShopArticle;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShopArticleDao {
    public abstract Pagination getPage(Integer paramInteger, Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageByChannel(Long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);

    public abstract Pagination getPageByWebsite(Long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);

    public abstract List<ShopArticle> getListByChannel(Integer paramInteger, int paramInt1, int paramInt2, boolean paramBoolean);

    public abstract List<ShopArticle> getListByWebsite(Long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);

    public abstract ShopArticle findById(Long paramLong);

    public abstract ShopArticle save(ShopArticle paramShopArticle);

    public abstract ShopArticle updateByUpdater(Updater<ShopArticle> paramUpdater);

    public abstract ShopArticle deleteById(Long paramLong);
}

