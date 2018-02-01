package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseRelatedgoods;

public class Relatedgoods extends BaseRelatedgoods {
    private static final long serialVersionUID = 1L;

    public Relatedgoods() {
    }

    public Relatedgoods(Long id) {
        super(id);
    }

    public Relatedgoods(Long id, Long productId, Long productIds) {
        super(id,
                productId,
                productIds);
    }
}

