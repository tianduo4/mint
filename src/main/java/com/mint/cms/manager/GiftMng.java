package com.mint.cms.manager;

import com.mint.cms.entity.Gift;
import com.mint.common.page.Pagination;

public abstract interface GiftMng {
    public abstract Pagination getPageGift(int paramInt1, int paramInt2);

    public abstract Gift findById(Long paramLong);

    public abstract Gift save(Gift paramGift);

    public abstract Gift updateByUpdater(Gift paramGift);

    public abstract Gift deleteById(Long paramLong);

    public abstract Gift updateByGiftnumb(Long paramLong1, Integer paramInteger, Long paramLong2);

    public abstract Gift[] deleteByIds(Long[] paramArrayOfLong);
}

