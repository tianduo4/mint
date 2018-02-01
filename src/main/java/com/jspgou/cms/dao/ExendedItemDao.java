package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ExendedItem;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface ExendedItemDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ExendedItem findById(Long paramLong);

    public abstract ExendedItem save(ExendedItem paramExendedItem);

    public abstract ExendedItem updateByUpdater(Updater<ExendedItem> paramUpdater);

    public abstract ExendedItem deleteById(Long paramLong);
}

