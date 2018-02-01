package com.mint.cms.manager;

import com.mint.cms.entity.Cart;
import com.mint.cms.entity.Gathering;
import com.mint.cms.entity.Order;
import com.mint.cms.entity.ShopMember;
import com.mint.common.page.Pagination;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

public abstract interface OrderMng {
    public abstract void updateSaleCount(Long paramLong);

    public abstract void updateliRun(Long paramLong);

    public abstract List<Order> getlist();

    public abstract void abolishOrder();

    public abstract Pagination getPageForOrderReturn(Long paramLong, int paramInt1, int paramInt2);

    public abstract Order createOrder(Cart paramCart, Long[] paramArrayOfLong, Long paramLong1, Long paramLong2, Long paramLong3, String paramString1, String paramString2, ShopMember paramShopMember, Long paramLong4, String paramString3);

    public abstract Pagination getPageForMember(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageForMember2(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageForMember1(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPageForMember3(Long paramLong, int paramInt1, int paramInt2);

    public abstract Pagination getPage(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3, Long paramLong4, Date paramDate1, Date paramDate2, Double paramDouble1, Double paramDouble2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Long paramLong5, int paramInt1, int paramInt2);

    public abstract Pagination getPage(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3, Long paramLong4, Date paramDate1, Date paramDate2, Double paramDouble1, Double paramDouble2, Integer paramInteger, Long paramLong5, int paramInt1, int paramInt2);

    public abstract Order findById(Long paramLong);

    public abstract Order findByCode(Long paramLong);

    public abstract Order save(Order paramOrder);

    public abstract Order updateByUpdater(Order paramOrder);

    public abstract Order deleteById(Long paramLong);

    public abstract Order[] updateByIds(Long[] paramArrayOfLong);

    public abstract Order[] deleteByIds(Long[] paramArrayOfLong);

    public abstract List<Object> getTotlaOrder();

    public abstract BigDecimal getMemberMoneyByYear(Long paramLong);

    public abstract Pagination getOrderByReturn(Long paramLong, Integer paramInteger1, Integer paramInteger2);

    public abstract List<Order> getCountByStatus(Date paramDate1, Date paramDate2, Integer paramInteger);

    public abstract List<Order> getCountByStatus1(Date paramDate1, Date paramDate2, Integer paramInteger);

    public abstract List<Order> getStatisticByYear(Integer paramInteger1, Integer paramInteger2);

    public abstract List<Order> getStatisticByYear1(Integer paramInteger1, Integer paramInteger2);

    public abstract List<Order> getOrderList(Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3, Long paramLong4, Date paramDate1, Date paramDate2, Double paramDouble1, Double paramDouble2, Integer paramInteger, Long paramLong5, int paramInt1, int paramInt2);

    public abstract List<Object[]> findListByIds(Long[] paramArrayOfLong);

    public abstract Order updateById(Long paramLong);

    public abstract BigDecimal getOrderSale(Date paramDate, Long paramLong);

    public abstract Long getOrderCount(Date paramDate, Long paramLong);

    public abstract Long getUnSendOrderCount(Long paramLong);

    public abstract Long getUnPayOrderCount(Long paramLong);

    public abstract Long getReturnCount(Long paramLong);

    public abstract JSONObject getOrderSale(Long paramLong, String paramString1, String paramString2, String paramString3)
            throws ParseException;

    public abstract void updateOrderPay(Gathering paramGathering, Order paramOrder);

    public abstract void updateOrderCanel(Order paramOrder);
}

