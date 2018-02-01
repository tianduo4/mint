package com.mint.cms.manager;

import com.mint.cms.entity.Gathering;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface GatheringMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<Gathering> getlist(Long paramLong);

    public abstract void deleteByorderId(Long paramLong);

    public abstract Gathering findById(Long paramLong);

    public abstract Gathering save(Gathering paramGathering);

    public abstract Gathering update(Gathering paramGathering);

    public abstract Gathering deleteById(Long paramLong);

    public abstract Gathering[] deleteByIds(Long[] paramArrayOfLong);
}

