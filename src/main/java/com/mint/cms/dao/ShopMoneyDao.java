package com.mint.cms.dao;

import com.mint.cms.entity.ShopMoney;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.Date;

public abstract interface ShopMoneyDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Pagination getPage(Long paramLong, Boolean paramBoolean, Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2);

    public abstract ShopMoney findById(Long paramLong);

    public abstract ShopMoney save(ShopMoney paramShopMoney);

    public abstract ShopMoney updateByUpdater(Updater<ShopMoney> paramUpdater);

    public abstract ShopMoney deleteById(Long paramLong);

    public abstract void deleteByMemberId(Long paramLong);
}

