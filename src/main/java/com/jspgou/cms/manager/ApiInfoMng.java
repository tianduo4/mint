package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ApiInfo;
import com.jspgou.common.page.Pagination;

public abstract interface ApiInfoMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ApiInfo findById(Long paramLong);

    public abstract ApiInfo findByApiUrl(String paramString);

    public abstract ApiInfo save(ApiInfo paramApiInfo);

    public abstract ApiInfo update(ApiInfo paramApiInfo);

    public abstract ApiInfo deleteById(Long paramLong);

    public abstract ApiInfo[] deleteByIds(Long[] paramArrayOfLong);
}

