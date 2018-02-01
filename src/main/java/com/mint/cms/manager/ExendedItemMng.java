package com.mint.cms.manager;

import com.mint.cms.entity.ExendedItem;
import com.mint.common.page.Pagination;

public abstract interface ExendedItemMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ExendedItem findById(Long paramLong);

    public abstract ExendedItem save(ExendedItem paramExendedItem);

    public abstract ExendedItem update(ExendedItem paramExendedItem);

    public abstract ExendedItem deleteById(Long paramLong);

    public abstract ExendedItem[] deleteByIds(Long[] paramArrayOfLong);
}

