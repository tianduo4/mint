package com.jspgou.cms.manager;

import com.jspgou.cms.entity.WebserviceAuth;
import com.jspgou.common.page.Pagination;

public abstract interface WebserviceAuthMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract boolean isPasswordValid(String paramString1, String paramString2);

    public abstract WebserviceAuth findByUsername(String paramString);

    public abstract WebserviceAuth findById(Integer paramInteger);

    public abstract WebserviceAuth save(WebserviceAuth paramWebserviceAuth);

    public abstract WebserviceAuth update(WebserviceAuth paramWebserviceAuth);

    public abstract WebserviceAuth update(Integer paramInteger, String paramString1, String paramString2, String paramString3, Boolean paramBoolean);

    public abstract WebserviceAuth deleteById(Integer paramInteger);

    public abstract WebserviceAuth[] deleteByIds(Integer[] paramArrayOfInteger);
}

