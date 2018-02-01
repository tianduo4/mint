package com.mint.plug.weixin.dao;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.plug.weixin.entity.WeixinMenu;

import java.util.List;

public abstract interface WeixinMenuDao {
    public abstract Pagination getPage(Long paramLong, Integer paramInteger, int paramInt1, int paramInt2);

    public abstract List<WeixinMenu> getList(Long paramLong, Integer paramInteger);

    public abstract WeixinMenu findById(Integer paramInteger);

    public abstract WeixinMenu save(WeixinMenu paramWeixinMenu);

    public abstract WeixinMenu updateByUpdater(Updater<WeixinMenu> paramUpdater);

    public abstract WeixinMenu deleteById(Integer paramInteger);
}

