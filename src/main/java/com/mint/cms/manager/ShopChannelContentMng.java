package com.mint.cms.manager;

import com.mint.cms.entity.ShopChannel;
import com.mint.cms.entity.ShopChannelContent;
import com.mint.common.page.Pagination;

public abstract interface ShopChannelContentMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ShopChannelContent findById(Long paramLong);

    public abstract ShopChannelContent save(String paramString, ShopChannel paramShopChannel);

    public abstract ShopChannelContent update(ShopChannelContent paramShopChannelContent);

    public abstract ShopChannelContent deleteById(Long paramLong);

    public abstract ShopChannelContent[] deleteByIds(Long[] paramArrayOfLong);
}

