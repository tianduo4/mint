package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ApiAccount;
import com.jspgou.common.page.Pagination;

public abstract interface ApiAccountMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ApiAccount findById(Long paramLong);

    public abstract ApiAccount findByAppId(String paramString);

    public abstract ApiAccount save(ApiAccount paramApiAccount);

    public abstract ApiAccount update(ApiAccount paramApiAccount);

    public abstract ApiAccount deleteById(Long paramLong);

    public abstract ApiAccount[] deleteByIds(Long[] paramArrayOfLong);
}

