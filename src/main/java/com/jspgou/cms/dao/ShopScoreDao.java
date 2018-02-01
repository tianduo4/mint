package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopScore;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.Date;
import java.util.List;

public abstract interface ShopScoreDao {
    public abstract Pagination getPage(Long paramLong, Boolean paramBoolean1, Boolean paramBoolean2, Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2);

    public abstract List<ShopScore> getlist(String paramString);

    public abstract ShopScore findById(Long paramLong);

    public abstract ShopScore save(ShopScore paramShopScore);

    public abstract ShopScore updateByUpdater(Updater<ShopScore> paramUpdater);

    public abstract ShopScore deleteById(Long paramLong);

    public abstract void deleteByMemberId(Long paramLong);
}

