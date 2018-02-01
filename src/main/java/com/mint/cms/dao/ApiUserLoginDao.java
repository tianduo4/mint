package com.mint.cms.dao;

import com.mint.cms.entity.ApiUserLogin;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface ApiUserLoginDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ApiUserLogin findById(Long paramLong);

    public abstract ApiUserLogin save(ApiUserLogin paramApiUserLogin);

    public abstract ApiUserLogin updateByUpdater(Updater<ApiUserLogin> paramUpdater);

    public abstract ApiUserLogin deleteById(Long paramLong);

    public abstract ApiUserLogin findBySessionKey(String paramString);

    public abstract ApiUserLogin findByUsername(String paramString);

    public abstract ApiUserLogin findUserLogin(String paramString1, String paramString2);
}

