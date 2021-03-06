package com.mint.cms.manager;

import com.mint.cms.entity.ApiUserLogin;
import com.mint.cms.entity.ShopAdmin;
import com.mint.common.page.Pagination;

public abstract interface ApiUserLoginMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ApiUserLogin findById(Long paramLong);

    public abstract ApiUserLogin save(ApiUserLogin paramApiUserLogin);

    public abstract ApiUserLogin update(ApiUserLogin paramApiUserLogin);

    public abstract ApiUserLogin deleteById(Long paramLong);

    public abstract ApiUserLogin[] deleteByIds(Long[] paramArrayOfLong);

    public abstract ApiUserLogin findBySessionKey(String paramString);

    public abstract ApiUserLogin findByUsername(String paramString);

    public abstract void updateLoginSuccess(String paramString1, String paramString2);

    public abstract void saveLoginSuccess(String paramString1, String paramString2);

    public abstract ShopAdmin findUser(String paramString1, String paramString2, String paramString3)
            throws Exception;

    public abstract ApiUserLogin findUserLogin(String paramString1, String paramString2);

    public abstract Short getUserStatus(String paramString);

    public abstract ApiUserLogin userActive(String paramString);

    public abstract ApiUserLogin userLogin(String paramString1, String paramString2);

    public abstract ApiUserLogin userLogout(String paramString);
}

