package com.jspgou.cms.manager;

import com.jspgou.cms.entity.StandardType;
import com.jspgou.common.page.Pagination;

import java.util.List;

public abstract interface StandardTypeMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract StandardType getByField(String paramString);

    public abstract StandardType findById(Long paramLong);

    public abstract List<StandardType> getList();

    public abstract List<StandardType> getList(Integer paramInteger);

    public abstract StandardType save(StandardType paramStandardType);

    public abstract StandardType update(StandardType paramStandardType);

    public abstract StandardType deleteById(Long paramLong);

    public abstract StandardType[] deleteByIds(Long[] paramArrayOfLong);

    public abstract StandardType[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);

    public abstract StandardType addStandard(StandardType paramStandardType, String[] paramArrayOfString1, String[] paramArrayOfString2, Integer[] paramArrayOfInteger);

    public abstract StandardType updateStandard(StandardType paramStandardType, Long[] paramArrayOfLong, String[] paramArrayOfString1, String[] paramArrayOfString2, Integer[] paramArrayOfInteger);
}

