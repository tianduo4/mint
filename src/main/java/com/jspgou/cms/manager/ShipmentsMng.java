package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.Shipments;
import com.jspgou.common.page.Pagination;

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

