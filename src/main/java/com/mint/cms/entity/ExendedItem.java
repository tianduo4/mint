package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseExendedItem;

public class ExendedItem extends BaseExendedItem {
    private static final long serialVersionUID = 1L;

    public ExendedItem() {
    }

    public ExendedItem(Long id) {
        super(id);
    }

    public ExendedItem(Long id, Exended exended, String name) {
        super(id,
                exended,
                name);
    }
}

