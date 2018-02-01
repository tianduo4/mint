package com.mint.cms.dao;

import com.mint.cms.entity.Order;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public abstract interface OrderDao {
    public abstract Pagination getPageForMember(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageForMember1(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageForOrderReturn(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageForMember2(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageForMember3(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPage(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3, Long paramLong4, Date paramDate1, Date paramDate2, Double paramDouble1, Double paramDouble2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Long paramLong5, int paramInt1, int paramInt2);

    public abstract Pagination getPage1(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3, Long paramLong4, Date paramDate1, Date paramDate2, Double paramDouble1, Double paramDouble2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Long paramLong5, int paramInt1, int paramInt2);

    public abstract Pagination getPage(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3, Long paramLong4, Date paramDate1, Date paramDate2, Double paramDouble1, Double paramDouble2, Integer paramInteger, Long paramLong5, int paramInt1, int paramInt2);

    public abstract Order findById(Long paramLong);

    public abstract Order findByCode(Long paramLong);

    public abstract Order save(Order paramOrder);

    public abstract Order updateByUpdater(Updater<Order> paramUpdater);

    public abstract Order deleteById(Long paramLong);

    public abstract Order updateById(Long paramLong);

    public abstract List<Object> getTotlaOrder();

    public abstract List<Order> getlist(Date paramDate);

    public abstract BigDecimal getMemberMoneyByYear(Long paramLong);

    public abstract Integer[] getOrderByMember(Long paramLong);

    public abstract Pagination getOrderByReturn(Long paramLong, Integer paramInteger1, Integer paramInteger2);

    public abstract List<Order> getCountByStatus(Date paramDate1, Date paramDate2, Integer paramInteger);

    public abstract List<Order> getCountByStatus1(Date paramDate1, Date paramDate2, Integer paramInteger);

    public abstract List<Order> getStatisticByYear(Integer paramInteger1, Integer paramInteger2);

    public abstract List<Order> getStatisticByYear1(Integer paramInteger1, Integer paramInteger2);

    public abstract List<Order> getOrderList(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3, Long paramLong4, Date paramDate1, Date paramDate2, Double paramDouble1, Double paramDouble2, Integer paramInteger, Long paramLong5, int paramInt1, int paramInt2);

    public abstract List<Object[]> findListByIds(Long[] paramArrayOfLong);

    public abstract BigDecimal getOrderSale(Date paramDate, Long paramLong);

    public abstract Long getOrderCount(Date paramDate, Long paramLong);

    public abstract Long getUnSendOrderCount(Long paramLong);

    public abstract Long getUnPayOrderCount(Long paramLong);

    public abstract Long getReturnCount(Long paramLong);

    public abstract List getOrderSale(Long paramLong, String paramString1, String paramString2, String paramString3)
            throws ParseException;
}

