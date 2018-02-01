package com.jspgou.core.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.core.entity.Admin;

public abstract interface AdminDao {
    public abstract Admin getByUsername(String paramString);

    public abstract Admin getByUserId(Long paramLong1, Long paramLong2);

    public abstract Admin findById(Long paramLong);

    public abstract Admin save(Admin paramAdmin);

    public abstract Admin updateByUpdater(Updater<Admin> paramUpdater);

    public abstract Admin deleteById(Long paramLong);
}

