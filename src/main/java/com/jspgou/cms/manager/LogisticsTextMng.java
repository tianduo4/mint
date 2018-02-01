package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.LogisticsText;

public abstract interface LogisticsTextMng {
    public abstract LogisticsText save(Logistics paramLogistics, String paramString);

    public abstract LogisticsText update(LogisticsText paramLogisticsText);
}

