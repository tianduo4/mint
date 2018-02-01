package com.mint.cms.manager;

import com.mint.cms.entity.Cart;
import com.mint.cms.entity.Product;

public abstract interface CartMng {
    public abstract Cart findById(Long paramLong);

    public abstract Cart update(Cart paramCart);

    public abstract Cart deleteById(Long paramLong);

    public abstract Cart addGift(Long paramLong1, int paramInt, boolean paramBoolean, Long paramLong2, Long paramLong3);

    public abstract Cart collectAddItem(Product paramProduct, Long paramLong1, Long paramLong2, int paramInt, boolean paramBoolean, Long paramLong3, Long paramLong4);
}

