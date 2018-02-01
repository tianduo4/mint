package com.mint.cms.dao;

import com.mint.cms.entity.Gift;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface GiftDao {
    public abstract Pagination getPageGift(int paramInt1, int paramInt2);

    public abstract Gift findById(Long paramLong);

    public abstract Gift save(Gift paramGift);

    public abstract Gift updateByUpdater(Updater<Gift> paramUpdater);

    public abstract Gift deleteById(Long paramLong);
}

