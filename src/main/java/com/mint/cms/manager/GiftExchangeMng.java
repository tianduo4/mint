package com.mint.cms.manager;

import com.mint.cms.entity.Gift;
import com.mint.cms.entity.GiftExchange;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.entity.ShopMemberAddress;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface GiftExchangeMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract GiftExchange save(Gift paramGift, ShopMemberAddress paramShopMemberAddress, ShopMember paramShopMember, Integer paramInteger);

    public abstract List<GiftExchange> getlist(Long paramLong);

    public abstract GiftExchange findById(Long paramLong);

    public abstract GiftExchange save(GiftExchange paramGiftExchange);

    public abstract GiftExchange update(GiftExchange paramGiftExchange);

    public abstract GiftExchange deleteById(Long paramLong);

    public abstract GiftExchange[] deleteByIds(Long[] paramArrayOfLong);

    public abstract void deleteByMemberId(Long paramLong);
}

