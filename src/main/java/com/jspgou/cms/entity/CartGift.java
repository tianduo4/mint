package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseCartGift;
import com.jspgou.core.entity.Website;

public class CartGift extends BaseCartGift {
    private static final long serialVersionUID = 1L;

    public CartGift() {
    }

    public CartGift(Long id) {
        super(id);
    }

    public CartGift(Long id, Website website, Cart cart, Gift gift, Integer count) {
        super(id,
                website,
                cart,
                gift,
                count);
    }
}

