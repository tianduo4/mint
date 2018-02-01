package com.mint.cms.manager;

import com.mint.cms.entity.ShopChannel;

import java.util.List;

public abstract interface ShopChannelMng {
    public abstract List<ShopChannel> getTopList(Long paramLong);

    public abstract List<ShopChannel> getChildList(Long paramLong, Integer paramInteger);

    public abstract List<ShopChannel> getList(Long paramLong);

    public abstract List<ShopChannel> getArticleList(Long paramLong);

    public abstract List<ShopChannel> getListForParent(Long paramLong, Integer paramInteger);

    public abstract List<ShopChannel> getListForParentNoSort(Long paramLong1, Long paramLong2);

    public abstract List<ShopChannel> getTopListForTag(Long paramLong, Integer paramInteger);

    public abstract ShopChannel getByPath(Long paramLong, String paramString);

    public abstract ShopChannel findById(Integer paramInteger);

    public abstract ShopChannel save(ShopChannel paramShopChannel, Integer paramInteger, String paramString);

    public abstract ShopChannel update(ShopChannel paramShopChannel, Integer paramInteger, String paramString);

    public abstract ShopChannel deleteById(Integer paramInteger);

    public abstract ShopChannel[] deleteByIds(Integer[] paramArrayOfInteger);

    public abstract ShopChannel[] updatePriority(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

    public abstract List<ShopChannel> getAloneChannelList(Long paramLong);

    public abstract List<ShopChannel> getList(Long paramLong1, Long paramLong2, Long paramLong3);
}

