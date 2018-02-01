package com.mint.cms.dao;

import com.mint.cms.entity.LogisticsText;
import com.mint.common.hibernate4.Updater;

public abstract interface LogisticsTextDao {
    public abstract LogisticsText save(LogisticsText paramLogisticsText);

    public abstract LogisticsText updateByUpdater(Updater<LogisticsText> paramUpdater);
}

