package com.mint.cms.manager;

import com.mint.cms.entity.WebserviceCallRecord;
import com.mint.common.page.Pagination;

public abstract interface WebserviceCallRecordMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract WebserviceCallRecord findById(Integer paramInteger);

    public abstract WebserviceCallRecord save(String paramString1, String paramString2);

    public abstract WebserviceCallRecord save(WebserviceCallRecord paramWebserviceCallRecord);

    public abstract WebserviceCallRecord update(WebserviceCallRecord paramWebserviceCallRecord);

    public abstract WebserviceCallRecord deleteById(Integer paramInteger);

    public abstract WebserviceCallRecord[] deleteByIds(Integer[] paramArrayOfInteger);
}

