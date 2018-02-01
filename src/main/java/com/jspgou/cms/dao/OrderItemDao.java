package com.jspgou.cms.dao;

import com.jspgou.cms.entity.OrderItem;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.Date;
import java.util.List;

public abstract interface OrderItemDao {
    public abstract List<Object[]> profitTop(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2);

    public abstract Integer totalCount(Long paramLong1, Long paramLong2);

    public abstract List<Object[]> getOrderItem(Date paramDate1, Date paramDate2);

    public abstract OrderItem findByMember(Long paramLong1, Long paramLong2, Long paramLong3);

    public abstract OrderItem findById(Long paramLong);

    public abstract OrderItem updateByUpdater(Updater<OrderItem> paramUpdater);

    public abstract Pagination getPageForMember(Long paramLong, Integer paramInteger, int paramInt1, int paramInt2);

    public abstract Pagination getPageForProuct(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageProductSaleRank(Long paramLong, String paramString, Integer paramInteger, int paramInt1, int paramInt2, Date paramDate1, Date paramDate2);
}

