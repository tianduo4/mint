package com.mint.cms.manager;

import com.mint.cms.entity.Collect;
import com.mint.cms.entity.ShopMember;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface CollectMng {
    public abstract Pagination getList(Integer paramInteger1, Integer paramInteger2, Long paramLong);

    public abstract Collect findById(Integer paramInteger);

    public abstract Collect save(ShopMember paramShopMember, Long paramLong1, Long paramLong2);

    public abstract Collect update(Collect paramCollect, Long paramLong);

    public abstract Collect deleteById(Integer paramInteger);

    public abstract Collect[] deleteByIds(Integer[] paramArrayOfInteger);

    public abstract List<Collect> findByProductId(Long paramLong);

    public abstract Collect findByProductFashionId(Long paramLong);

    public abstract List<Collect> getList(Long paramLong, int paramInt1, int paramInt2);

    public abstract List<Collect> getList(Long paramLong1, Long paramLong2);

    public abstract void deleteByMemberId(Long paramLong);
}

