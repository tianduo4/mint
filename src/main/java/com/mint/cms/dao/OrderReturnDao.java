package com.mint.cms.dao;

import com.mint.cms.entity.OrderReturn;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface OrderReturnDao {
    public abstract Pagination getPage(Integer paramInteger, int paramInt1, int paramInt2);

    public abstract OrderReturn findById(Long paramLong);

    public abstract OrderReturn save(OrderReturn paramOrderReturn);

    public abstract List<OrderReturn> findByOrderId(Long paramLong);

    public abstract OrderReturn updateByUpdater(Updater<OrderReturn> paramUpdater);

    public abstract OrderReturn deleteById(Long paramLong);
}

