package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Collect;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

public abstract interface CollectDao {
    public abstract Pagination getList(Integer paramInteger1, Integer paramInteger2, Long paramLong);

    public abstract Collect findById(Integer paramInteger);

    public abstract Collect save(Collect paramCollect);

    public abstract Collect updateByUpdater(Updater<Collect> paramUpdater);

    public abstract Collect deleteById(Integer paramInteger);

    public abstract List<Collect> findByProductId(Long paramLong);

    public abstract Collect findByProductFashionId(Long paramLong);

    public abstract List<Collect> getList(Long paramLong, int paramInt1, int paramInt2);

    public abstract List<Collect> getList(Long paramLong1, Long paramLong2);

    public abstract void deleteByMemberId(Long paramLong);
}

