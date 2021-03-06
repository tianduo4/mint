package com.mint.cms.manager;

import com.mint.cms.entity.ShopArticle;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShopArticleMng {
    public abstract Pagination getPage(Integer paramInteger, Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageForTag(Long paramLong1, Long paramLong2, int paramInt1, int paramInt2);

    public abstract List<ShopArticle> getListForTag(Long paramLong, Integer paramInteger, int paramInt1, int paramInt2);

    public abstract ShopArticle findById(Long paramLong);

    public abstract ShopArticle save(ShopArticle paramShopArticle, Integer paramInteger, String paramString);

    public abstract ShopArticle update(ShopArticle paramShopArticle, Integer paramInteger, String paramString);

    public abstract ShopArticle deleteById(Long paramLong);

    public abstract ShopArticle[] deleteByIds(Long[] paramArrayOfLong);
}

