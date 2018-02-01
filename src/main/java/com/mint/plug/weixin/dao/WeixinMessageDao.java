package com.mint.plug.weixin.dao;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.plug.weixin.entity.WeixinMessage;

import java.util.List;

public abstract interface WeixinMessageDao {
    public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

    public abstract List<WeixinMessage> getList(Long paramLong);

    public abstract WeixinMessage getWelcome(Long paramLong);

    public abstract WeixinMessage findByNumber(String paramString, Long paramLong);

    public abstract WeixinMessage save(WeixinMessage paramWeixinMessage);

    public abstract WeixinMessage findById(Integer paramInteger);

    public abstract WeixinMessage deleteById(Integer paramInteger);

    public abstract WeixinMessage updateByUpdater(Updater<WeixinMessage> paramUpdater);
}

