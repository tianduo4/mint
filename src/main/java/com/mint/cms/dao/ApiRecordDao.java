package com.mint.cms.dao;

import com.mint.cms.entity.ApiRecord;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface ApiRecordDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ApiRecord findById(Long paramLong);

    public abstract ApiRecord save(ApiRecord paramApiRecord);

    public abstract ApiRecord updateByUpdater(Updater<ApiRecord> paramUpdater);

    public abstract ApiRecord deleteById(Long paramLong);

    public abstract ApiRecord findBySign(String paramString1, String paramString2);
}

