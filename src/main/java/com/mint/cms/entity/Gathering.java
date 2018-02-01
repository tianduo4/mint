package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseGathering;

public class Gathering extends BaseGathering {
    private static final long serialVersionUID = 1L;

    public Gathering() {
    }

    public Gathering(Long id) {
        super(id);
    }

    public Gathering(Long id, Order indent, ShopAdmin shopAdmin, String bank, String accounts, String drawee, String comment) {
        super(id,
                indent,
                shopAdmin,
                bank,
                accounts,
                drawee,
                comment);
    }
}

