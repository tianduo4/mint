package com.mint.cms.entity;

import com.mint.cms.entity.base.BasePopularityItem;

public class PopularityItem extends BasePopularityItem {
    private static final long serialVersionUID = 1L;

    public PopularityItem() {
    }

    public PopularityItem(Long id) {
        super(id);
    }

    public PopularityItem(Long id, Cart cart, Integer count) {
        super(id,
                cart,
                count);
    }
}

