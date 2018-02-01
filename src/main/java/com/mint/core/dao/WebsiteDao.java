package com.mint.core.dao;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.core.entity.Website;

import java.util.List;

public abstract interface WebsiteDao {
    public abstract Website getUniqueWebsite();

    public abstract int countWebsite();

    public abstract Website findByDomain(String paramString);

    public abstract Pagination getAllPage(int paramInt1, int paramInt2);

    public abstract List<Website> getAllList();

    public abstract Website findById(Long paramLong);

    public abstract Website findById1(Long paramLong);

    public abstract Website save(Website paramWebsite);

    public abstract Website updateByUpdater(Updater<Website> paramUpdater);

    public abstract Website deleteById(Long paramLong);

    public abstract Website findByDomain(String paramString, boolean paramBoolean);

    public abstract List<Website> getList(boolean paramBoolean);
}

