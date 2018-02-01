package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseGiftExchange;

import java.util.Date;

public class GiftExchange extends BaseGiftExchange {
    private static final long serialVersionUID = 1L;

    public GiftExchange() {
    }

    public GiftExchange(Long id) {
        super(id);
    }

    public GiftExchange(Long id, ShopMember member, Gift gift, Date createTime, Integer status) {
        super(id,
                member,
                gift,
                createTime,
                status);
    }
}

