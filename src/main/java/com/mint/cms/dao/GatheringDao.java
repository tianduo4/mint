package com.mint.cms.dao;

import com.mint.cms.entity.Gathering;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface GatheringDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<Gathering> getlist(Long paramLong);

    public abstract Gathering findById(Long paramLong);

    public abstract Gathering save(Gathering paramGathering);

    public abstract Gathering updateByUpdater(Updater<Gathering> paramUpdater);

    public abstract Gathering deleteById(Long paramLong);
}

