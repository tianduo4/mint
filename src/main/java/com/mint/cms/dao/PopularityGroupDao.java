package com.mint.cms.dao;

import com.mint.cms.entity.PopularityGroup;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface PopularityGroupDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract PopularityGroup findById(Long paramLong);

    public abstract PopularityGroup save(PopularityGroup paramPopularityGroup);

    public abstract PopularityGroup updateByUpdater(Updater<PopularityGroup> paramUpdater);

    public abstract PopularityGroup deleteById(Long paramLong);
}

