package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Gift;
import com.jspgou.cms.entity.GiftExchange;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopMemberAddress;
import com.jspgou.common.page.Pagination;

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

