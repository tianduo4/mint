package com.jspgou.plug.store.manager;

import com.jspgou.common.page.Pagination;
import com.jspgou.plug.store.entity.PlugStoreConfig;

public abstract interface PlugStoreConfigMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract PlugStoreConfig findById(Integer paramInteger);

    public abstract PlugStoreConfig getDefault();

    public abstract PlugStoreConfig save(PlugStoreConfig paramPlugStoreConfig);

    public abstract PlugStoreConfig update(PlugStoreConfig paramPlugStoreConfig, String paramString);

    public abstract PlugStoreConfig deleteById(Integer paramInteger);

    public abstract PlugStoreConfig[] deleteByIds(Integer[] paramArrayOfInteger);
}

