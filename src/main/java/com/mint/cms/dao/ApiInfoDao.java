package com.mint.cms.dao;

import com.mint.cms.entity.ApiInfo;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface ApiInfoDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ApiInfo findById(Long paramLong);

    public abstract ApiInfo findByApiUrl(String paramString);

    public abstract ApiInfo save(ApiInfo paramApiInfo);

    public abstract ApiInfo updateByUpdater(Updater<ApiInfo> paramUpdater);

    public abstract ApiInfo deleteById(Long paramLong);
}

