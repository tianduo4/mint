package com.mint.cms.dao;

import com.mint.cms.entity.GiftExchange;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface GiftExchangeDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<GiftExchange> getlist(Long paramLong);

    public abstract GiftExchange findById(Long paramLong);

    public abstract GiftExchange save(GiftExchange paramGiftExchange);

    public abstract GiftExchange updateByUpdater(Updater<GiftExchange> paramUpdater);

    public abstract GiftExchange deleteById(Long paramLong);

    public abstract void deleteByMemberId(Long paramLong);
}

