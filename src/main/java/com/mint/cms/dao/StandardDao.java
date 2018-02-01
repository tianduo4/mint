package com.mint.cms.dao;

import com.mint.cms.entity.Standard;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface StandardDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Standard findById(Long paramLong);

    public abstract List<Standard> findByTypeId(Long paramLong);

    public abstract Standard save(Standard paramStandard);

    public abstract Standard updateByUpdater(Updater<Standard> paramUpdater);

    public abstract Standard deleteById(Long paramLong);
}

