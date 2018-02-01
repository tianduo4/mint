package com.mint.cms.dao;

import com.mint.cms.entity.Logistics;
import com.mint.cms.entity.Shipments;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShipmentsDao {
    public abstract Pagination getPage(Boolean paramBoolean, int paramInt1, int paramInt2);

    public abstract List<Shipments> getlist(Long paramLong);

    public abstract Shipments findById(Long paramLong);

    public abstract Shipments save(Shipments paramShipments);

    public abstract Shipments updateByUpdater(Updater<Shipments> paramUpdater);

    public abstract Shipments deleteById(Long paramLong);

    public abstract List<Logistics> getAllList();
}

