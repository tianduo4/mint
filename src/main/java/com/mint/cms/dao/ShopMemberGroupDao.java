package com.mint.cms.dao;

import com.mint.cms.entity.ShopMemberGroup;
import com.mint.common.hibernate4.Updater;

import java.util.List;

public abstract interface ShopMemberGroupDao {
    public abstract List<ShopMemberGroup> getList(Long paramLong, boolean paramBoolean);

    public abstract List<ShopMemberGroup> getList();

    public abstract ShopMemberGroup findById(Long paramLong);

    public abstract ShopMemberGroup save(ShopMemberGroup paramShopMemberGroup);

    public abstract ShopMemberGroup updateByUpdater(Updater<ShopMemberGroup> paramUpdater);

    public abstract ShopMemberGroup deleteById(Long paramLong);
}

