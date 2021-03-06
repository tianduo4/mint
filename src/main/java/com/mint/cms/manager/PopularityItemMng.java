package com.mint.cms.manager;

import com.mint.cms.entity.Cart;
import com.mint.cms.entity.PopularityItem;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface PopularityItemMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<PopularityItem> getlist(Long paramLong1, Long paramLong2);

    public abstract PopularityItem findById(Long paramLong);

    public abstract PopularityItem findById(Long paramLong1, Long paramLong2);

    public abstract PopularityItem save(PopularityItem paramPopularityItem);

    public abstract PopularityItem update(PopularityItem paramPopularityItem);

    public abstract PopularityItem deleteById(Long paramLong);

    public abstract PopularityItem[] deleteByIds(Long[] paramArrayOfLong);

    public abstract void save(Cart paramCart, Long paramLong);
}

