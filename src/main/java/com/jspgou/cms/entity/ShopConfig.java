package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseShopConfig;

public class ShopConfig extends BaseShopConfig {
    private static final long serialVersionUID = 1L;

    public ShopConfig() {
    }

    public ShopConfig(Long id) {
        super(id);
    }

    public ShopConfig(Long id, ShopMemberGroup registerGroup, String shopPriceName, String marketPriceName, Integer favoriteSize, Boolean registerAuto) {
        super(id,
                registerGroup,
                shopPriceName,
                marketPriceName,
                favoriteSize,
                registerAuto);
    }
}

