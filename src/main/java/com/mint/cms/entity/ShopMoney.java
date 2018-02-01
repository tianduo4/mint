package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseShopMoney;

import java.util.Date;

public class ShopMoney extends BaseShopMoney {
    private static final long serialVersionUID = 1L;

    public ShopMoney() {
    }

    public ShopMoney(Long id) {
        super(id);
    }

    public ShopMoney(Long id, String name, Double money, boolean status, Date createTime) {
        super(id,
                name,
                money,
                status,
                createTime);
    }
}

