package com.mint.cms.dao;

import com.mint.cms.entity.Message;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

public abstract interface MessageDao {
    public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

    public abstract Message findById(Long paramLong);

    public abstract Message save(Message paramMessage);

    public abstract Message updateByUpdater(Updater<Message> paramUpdater);

    public abstract Message deleteById(Long paramLong);
}

