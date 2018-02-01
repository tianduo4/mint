package com.jspgou.plug.weixin.manager;

import com.jspgou.common.page.Pagination;
import com.jspgou.plug.weixin.entity.WeixinMessage;

import java.util.List;

public abstract interface WeixinMessageMng {
    public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

    public abstract List<WeixinMessage> getList(Long paramLong);

    public abstract WeixinMessage getWelcome(Long paramLong);

    public abstract WeixinMessage findByNumber(String paramString, Long paramLong);

    public abstract WeixinMessage findById(Integer paramInteger);

    public abstract WeixinMessage save(WeixinMessage paramWeixinMessage);

    public abstract WeixinMessage update(WeixinMessage paramWeixinMessage);

    public abstract WeixinMessage deleteById(Integer paramInteger);

    public abstract WeixinMessage[] deleteByIds(Integer[] paramArrayOfInteger);
}

