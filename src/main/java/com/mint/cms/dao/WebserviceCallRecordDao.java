package com.mint.cms.dao;

import com.mint.cms.entity.WebserviceCallRecord;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface WebserviceCallRecordDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract WebserviceCallRecord findById(Integer paramInteger);

    public abstract WebserviceCallRecord save(WebserviceCallRecord paramWebserviceCallRecord);

    public abstract WebserviceCallRecord updateByUpdater(Updater<WebserviceCallRecord> paramUpdater);

    public abstract WebserviceCallRecord deleteById(Integer paramInteger);
}

