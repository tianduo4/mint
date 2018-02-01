package com.jspgou.core.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.core.entity.Log;

public abstract interface LogDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Log findById(Long paramLong);

    public abstract Log save(Log paramLog);

    public abstract Log updateByUpdater(Updater<Log> paramUpdater);

    public abstract Log deleteById(Long paramLong);
}

