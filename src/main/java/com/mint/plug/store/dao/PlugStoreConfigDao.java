package com.mint.plug.store.dao;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.plug.store.entity.PlugStoreConfig;

public abstract interface PlugStoreConfigDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract PlugStoreConfig findById(Integer paramInteger);

    public abstract PlugStoreConfig save(PlugStoreConfig paramPlugStoreConfig);

    public abstract PlugStoreConfig updateByUpdater(Updater<PlugStoreConfig> paramUpdater);

    public abstract PlugStoreConfig deleteById(Integer paramInteger);

    public abstract Class<PlugStoreConfig> getEntityClass();
}

