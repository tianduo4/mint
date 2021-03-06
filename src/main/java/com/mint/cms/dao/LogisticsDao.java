package com.mint.cms.dao;

import com.mint.cms.entity.Logistics;
import com.mint.common.hibernate4.Updater;

import java.util.List;

public abstract interface LogisticsDao {
    public abstract List<Logistics> getAllList();

    public abstract Logistics findById(Long paramLong);

    public abstract Logistics save(Logistics paramLogistics);

    public abstract Logistics updateByUpdater(Updater<Logistics> paramUpdater);

    public abstract Logistics deleteById(Long paramLong);
}

