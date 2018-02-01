package com.jspgou.cms.dao;

import com.jspgou.cms.entity.LogisticsText;
import com.jspgou.common.hibernate4.Updater;

public abstract interface LogisticsTextDao {
    public abstract LogisticsText save(LogisticsText paramLogisticsText);

    public abstract LogisticsText updateByUpdater(Updater<LogisticsText> paramUpdater);
}

