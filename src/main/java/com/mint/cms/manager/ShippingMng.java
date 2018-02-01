package com.mint.cms.manager;

import com.mint.cms.entity.Shipping;

import java.util.List;

public abstract interface ShippingMng {
    public abstract List<Shipping> getList(Long paramLong, boolean paramBoolean);

    public abstract List<Shipping> getListForCart(Long paramLong1, Long paramLong2, int paramInt1, int paramInt2);

    public abstract Shipping findById(Long paramLong);

    public abstract Shipping save(Shipping paramShipping);

    public abstract Shipping update(Shipping paramShipping);

    public abstract Shipping deleteById(Long paramLong);

    public abstract Shipping[] deleteByIds(Long[] paramArrayOfLong);

    public abstract Shipping[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);
}

