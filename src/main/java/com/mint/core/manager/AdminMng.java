package com.mint.core.manager;

import com.mint.core.entity.Admin;
import com.mint.core.entity.Website;

public abstract interface AdminMng {
    public abstract Admin getByUsername(String paramString);

    public abstract Admin getByUserId(Long paramLong1, Long paramLong2);

    public abstract Admin register(String paramString1, String paramString2, String paramString3, String paramString4, Boolean paramBoolean1, Website paramWebsite, Boolean paramBoolean2);

    public abstract Admin findById(Long paramLong);

    public abstract Admin save(Admin paramAdmin);

    public abstract Admin update(Admin paramAdmin);

    public abstract Admin deleteById(Long paramLong);

    public abstract Admin[] deleteByIds(Long[] paramArrayOfLong);

    public abstract void updateRole(Admin paramAdmin, Integer[] paramArrayOfInteger);

    public abstract void addRole(Admin paramAdmin, Integer[] paramArrayOfInteger);
}

