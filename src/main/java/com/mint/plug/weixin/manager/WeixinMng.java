package com.mint.plug.weixin.manager;

import com.mint.common.page.Pagination;
import com.mint.plug.weixin.entity.Weixin;

public abstract interface WeixinMng {
    public abstract Pagination getPage(Integer paramInteger, int paramInt1, int paramInt2);

    public abstract Weixin findById(Integer paramInteger);

    public abstract Weixin find(Long paramLong);

    public abstract Weixin save(Weixin paramWeixin);

    public abstract Weixin update(Weixin paramWeixin);

    public abstract Weixin deleteById(Integer paramInteger);

    public abstract Weixin[] delete(Integer[] paramArrayOfInteger);

    public abstract Weixin findBy();
}

