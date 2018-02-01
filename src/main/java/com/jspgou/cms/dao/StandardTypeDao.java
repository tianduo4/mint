package com.jspgou.cms.dao;

import com.jspgou.cms.entity.StandardType;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

public abstract interface StandardTypeDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract StandardType getByField(String paramString);

    public abstract StandardType findById(Long paramLong);

    public abstract List<StandardType> getList();

    public abstract List<StandardType> getList(Integer paramInteger);

    public abstract StandardType save(StandardType paramStandardType);

    public abstract StandardType updateByUpdater(Updater<StandardType> paramUpdater);

    public abstract StandardType deleteById(Long paramLong);
}

