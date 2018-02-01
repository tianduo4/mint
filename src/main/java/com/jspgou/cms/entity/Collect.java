package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseCollect;

public class Collect extends BaseCollect {
    private static final long serialVersionUID = 1L;

    public Collect() {
    }

    public Collect(Integer id) {
        super(id);
    }

    public Collect(Integer id, ShopMember member, Product product) {
        super(id,
                member,
                product);
    }
}

