package com.mint.cms.manager;

import com.mint.cms.entity.ApiRecord;
import com.mint.common.page.Pagination;

public abstract interface ApiRecordMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ApiRecord findById(Long paramLong);

    public abstract ApiRecord save(ApiRecord paramApiRecord);

    public abstract ApiRecord update(ApiRecord paramApiRecord);

    public abstract ApiRecord deleteById(Long paramLong);

    public abstract ApiRecord[] deleteByIds(Long[] paramArrayOfLong);

    public abstract void callApiRecord(String paramString1, String paramString2, String paramString3, String paramString4);

    public abstract ApiRecord findBySign(String paramString1, String paramString2);
}

