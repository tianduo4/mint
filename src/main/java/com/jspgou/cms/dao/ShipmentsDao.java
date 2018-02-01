package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.Shipments;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

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

