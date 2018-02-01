package com.mint.core.dao;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.core.entity.ThirdAccount;

public abstract interface ThirdAccountDao {
    public abstract Pagination getPage(String paramString1, String paramString2, int paramInt1, int paramInt2);

    public abstract ThirdAccount findById(Long paramLong);

    public abstract ThirdAccount findByKey(String paramString);

    public abstract ThirdAccount save(ThirdAccount paramThirdAccount);

    public abstract ThirdAccount updateByUpdater(Updater<ThirdAccount> paramUpdater);

    public abstract ThirdAccount deleteById(Long paramLong);
}

