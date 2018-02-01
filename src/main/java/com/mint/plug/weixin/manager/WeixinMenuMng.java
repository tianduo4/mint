package com.mint.plug.weixin.manager;

import com.mint.common.page.Pagination;
import com.mint.plug.weixin.entity.WeixinMenu;

import java.util.List;

public abstract interface WeixinMenuMng {
    public abstract Pagination getPage(Long paramLong, Integer paramInteger, int paramInt1, int paramInt2);

    public abstract List<WeixinMenu> getList(Long paramLong, Integer paramInteger);

    public abstract WeixinMenu findById(Integer paramInteger);

    public abstract WeixinMenu save(WeixinMenu paramWeixinMenu);

    public abstract WeixinMenu update(WeixinMenu paramWeixinMenu);

    public abstract WeixinMenu deleteById(Integer paramInteger);

    public abstract WeixinMenu[] deleteByIds(Integer[] paramArrayOfInteger);
}

