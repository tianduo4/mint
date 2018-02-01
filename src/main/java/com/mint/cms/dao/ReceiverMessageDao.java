package com.mint.cms.dao;

import com.mint.cms.entity.ReceiverMessage;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.List;

public abstract interface ReceiverMessageDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Pagination getPage(Long paramLong, int paramInt1, Integer paramInteger, int paramInt2);

    public abstract Pagination getPage(Long paramLong1, Long paramLong2, String paramString, Date paramDate1, Date paramDate2, Boolean paramBoolean1, Integer paramInteger, Boolean paramBoolean2, int paramInt1, int paramInt2);

    public abstract List<ReceiverMessage> getList(Long paramLong1, Long paramLong2, String paramString, Date paramDate1, Date paramDate2, Boolean paramBoolean1, Integer paramInteger, Boolean paramBoolean2);

    public abstract Pagination getUnreadPage(Long paramLong, int paramInt1, int paramInt2);

    public abstract ReceiverMessage findById(Long paramLong);

    public abstract ReceiverMessage save(ReceiverMessage paramReceiverMessage);

    public abstract ReceiverMessage updateByUpdater(Updater<ReceiverMessage> paramUpdater);

    public abstract ReceiverMessage deleteById(Long paramLong);
}

