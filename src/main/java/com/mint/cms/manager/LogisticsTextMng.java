package com.mint.cms.manager;

import com.mint.cms.entity.Logistics;
import com.mint.cms.entity.LogisticsText;

public abstract interface LogisticsTextMng {
    public abstract LogisticsText save(Logistics paramLogistics, String paramString);

    public abstract LogisticsText update(LogisticsText paramLogisticsText);
}

