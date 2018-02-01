package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseCartItem;
import com.jspgou.core.entity.Website;

public class CartItem extends BaseCartItem {
    private static final long serialVersionUID = 1L;

    public Double getSubtotal() {
        return Double.valueOf(getProduct().getMemberPrice().doubleValue() * getCount().intValue());
    }

    public Double getLimitSubtotal() {
        int count = getCount().intValue();
        Double b1 = getProduct().getProductExt().getSeckillprice();
        Double b3 = Double.valueOf(b1.doubleValue() * count);
        return b3;
    }

    public int getWeightForFreight() {
        Product p = getProduct();

        return p.getWeight().intValue() * getCount().intValue();
    }

    public int getCountForFreigt() {
        return getCount().intValue();
    }

    public CartItem() {
    }

    public CartItem(Long id) {
        super(id);
    }

    public CartItem(Long id, Website website, Cart cart, Product product, Integer count) {
        super(id,
                website,
                cart,
                product,
                count);
    }
}

