package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Message;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.common.page.Pagination;

public abstract interface MessageMng {
    public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

    public abstract Message findById(Long paramLong);

    public abstract Message save(Message paramMessage);

    public abstract Message update(Message paramMessage);

    public abstract Message deleteById(Long paramLong);

    public abstract Message[] deleteByIds(Long[] paramArrayOfLong);

    public abstract void send(Message paramMessage, ShopAdmin paramShopAdmin, String paramString, Long paramLong)
            throws Exception;

    public abstract void trash(ShopAdmin paramShopAdmin, Long[] paramArrayOfLong)
            throws Exception;

    public abstract void revert(ShopAdmin paramShopAdmin, Long[] paramArrayOfLong)
            throws Exception;

    public abstract void clean(ShopAdmin paramShopAdmin, Long[] paramArrayOfLong)
            throws Exception;
}

