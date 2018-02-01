package com.mint.plug.weixin.dao;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.plug.weixin.entity.Weixin;

public abstract interface WeixinDao {
    public abstract Pagination getPage(Integer paramInteger, int paramInt1, int paramInt2);

    public abstract Weixin save(Weixin paramWeixin);

    public abstract Weixin deleteById(Integer paramInteger);

    public abstract Weixin findById(Integer paramInteger);

    public abstract Weixin find(Long paramLong);

    public abstract Weixin updateByUpdater(Updater<Weixin> paramUpdater);

    public abstract Weixin findBy();
}

