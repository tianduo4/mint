package com.mint.cms.dao;

import com.mint.cms.entity.ShopMemberAddress;
import com.mint.common.hibernate4.Updater;

import java.util.List;

public abstract interface ShopMemberAddressDao {
    public abstract List<ShopMemberAddress> getList(Long paramLong);

    public abstract List<ShopMemberAddress> findByMemberDefault(Long paramLong, Boolean paramBoolean);

    public abstract ShopMemberAddress findById(Long paramLong);

    public abstract ShopMemberAddress save(ShopMemberAddress paramShopMemberAddress);

    public abstract ShopMemberAddress updateByUpdater(Updater<ShopMemberAddress> paramUpdater);

    public abstract ShopMemberAddress deleteById(Long paramLong);

    public abstract void deleteByMemberId(Long paramLong);
}

