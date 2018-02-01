package com.mint.cms.manager;

import com.mint.cms.entity.ShopArticle;
import com.mint.cms.entity.ShopArticleContent;
import com.mint.common.page.Pagination;

public abstract interface ShopArticleContentMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ShopArticleContent findById(Long paramLong);

    public abstract ShopArticleContent save(String paramString, ShopArticle paramShopArticle);

    public abstract ShopArticleContent update(ShopArticleContent paramShopArticleContent);

    public abstract ShopArticleContent deleteById(Long paramLong);

    public abstract ShopArticleContent[] deleteByIds(Long[] paramArrayOfLong);
}

