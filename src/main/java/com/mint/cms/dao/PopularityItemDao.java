package com.mint.cms.dao;

import com.mint.cms.entity.PopularityItem;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface PopularityItemDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<PopularityItem> getlist(Long paramLong1, Long paramLong2);

    public abstract PopularityItem findById(Long paramLong1, Long paramLong2);

    public abstract PopularityItem findById(Long paramLong);

    public abstract PopularityItem save(PopularityItem paramPopularityItem);

    public abstract PopularityItem updateByUpdater(Updater<PopularityItem> paramUpdater);

    public abstract PopularityItem deleteById(Long paramLong);
}

