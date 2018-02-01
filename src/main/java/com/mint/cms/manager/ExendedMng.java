package com.mint.cms.manager;

import com.mint.cms.entity.Exended;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ExendedMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Exended findById(Long paramLong);

    public abstract List<Exended> getList();

    public abstract List<Exended> getList(Long paramLong);

    public abstract Exended save(Exended paramExended, Long[] paramArrayOfLong);

    public abstract Exended update(Exended paramExended, Long[] paramArrayOfLong);

    public abstract Exended deleteById(Long paramLong);

    public abstract Exended[] deleteByIds(Long[] paramArrayOfLong);

    public abstract Exended[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);
}

