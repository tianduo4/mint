package com.mint.cms.manager;

import com.mint.cms.entity.Logistics;
import com.mint.cms.entity.Shipments;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ShipmentsMng {
    public abstract List<Logistics> getAllList();

    public abstract Pagination getPage(Boolean paramBoolean, int paramInt1, int paramInt2);

    public abstract List<Shipments> getlist(Long paramLong);

    public abstract void deleteByorderId(Long paramLong);

    public abstract Shipments findById(Long paramLong);

    public abstract Shipments save(Shipments paramShipments);

    public abstract Shipments update(Shipments paramShipments);

    public abstract Shipments deleteById(Long paramLong);

    public abstract Shipments[] deleteByIds(Long[] paramArrayOfLong);
}

