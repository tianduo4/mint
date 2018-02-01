package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.common.page.Pagination;

public abstract interface ShopAdminMng {
    public abstract ShopAdmin getByUserId(Long paramLong1, Long paramLong2);

    public abstract ShopAdmin getByUsername(String paramString);

    public abstract ShopAdmin register(String paramString1, String paramString2, Boolean paramBoolean, String paramString3, String paramString4, boolean paramBoolean1, Long paramLong, ShopAdmin paramShopAdmin);

    public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

    public abstract ShopAdmin findById(Long paramLong);

    public abstract ShopAdmin save(ShopAdmin paramShopAdmin);

    public abstract ShopAdmin update(ShopAdmin paramShopAdmin, String paramString1, Boolean paramBoolean1, String paramString2, Boolean paramBoolean2);

    public abstract ShopAdmin deleteById(Long paramLong);

    public abstract ShopAdmin[] deleteByIds(Long[] paramArrayOfLong);

    public abstract ShopAdmin findByName(String paramString);

    public abstract void register(String paramString1, String paramString2, Boolean paramBoolean1, String paramString3, String paramString4, Boolean paramBoolean2, Long paramLong, Integer[] paramArrayOfInteger, ShopAdmin paramShopAdmin);

    public abstract void update(String paramString1, Boolean paramBoolean1, String paramString2, Boolean paramBoolean2, ShopAdmin paramShopAdmin, Integer[] paramArrayOfInteger);

    public abstract void delete(Long[] paramArrayOfLong);
}

