package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Logistics;

import java.util.List;

public abstract interface LogisticsMng {
    public abstract List<Logistics> getAllList();

    public abstract Logistics findById(Long paramLong);

    public abstract Logistics save(Logistics paramLogistics, String paramString);

    public abstract Logistics update(Logistics paramLogistics, String paramString);

    public abstract Logistics deleteById(Long paramLong);

    public abstract Logistics[] deleteByIds(Long[] paramArrayOfLong);

    public abstract Logistics[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);
}

