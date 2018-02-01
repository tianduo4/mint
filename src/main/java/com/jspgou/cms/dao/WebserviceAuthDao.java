package com.jspgou.cms.dao;

import com.jspgou.cms.entity.WebserviceAuth;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface WebserviceAuthDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract WebserviceAuth findByUsername(String paramString);

    public abstract WebserviceAuth findById(Integer paramInteger);

    public abstract WebserviceAuth save(WebserviceAuth paramWebserviceAuth);

    public abstract WebserviceAuth updateByUpdater(Updater<WebserviceAuth> paramUpdater);

    public abstract WebserviceAuth deleteById(Integer paramInteger);
}

