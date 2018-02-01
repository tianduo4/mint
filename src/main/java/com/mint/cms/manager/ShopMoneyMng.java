package com.mint.cms.manager;

import com.mint.cms.entity.ShopMoney;
import com.mint.common.page.Pagination;

import java.util.Date;

public abstract interface ShopMoneyMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Pagination getPage(Long paramLong, Boolean paramBoolean, Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2);

    public abstract ShopMoney findById(Long paramLong);

    public abstract ShopMoney save(ShopMoney paramShopMoney);

    public abstract ShopMoney update(ShopMoney paramShopMoney);

    public abstract ShopMoney deleteById(Long paramLong);

    public abstract ShopMoney[] deleteByIds(Long[] paramArrayOfLong);

    public abstract void deleteByMemberId(Long paramLong);
}

