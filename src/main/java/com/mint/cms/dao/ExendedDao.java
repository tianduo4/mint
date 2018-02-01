package com.mint.cms.dao;

import com.mint.cms.entity.Exended;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ExendedDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Exended findById(Long paramLong);

    public abstract List<Exended> getList();

    public abstract List<Exended> getList(Long paramLong);

    public abstract Exended save(Exended paramExended);

    public abstract Exended updateByUpdater(Updater<Exended> paramUpdater);

    public abstract Exended deleteById(Long paramLong);
}

