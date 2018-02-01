package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Relatedgoods;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

public abstract interface RelatedgoodsDao {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<Relatedgoods> findByIdProductId(Long paramLong);

    public abstract Relatedgoods findById(Long paramLong);

    public abstract Relatedgoods save(Relatedgoods paramRelatedgoods);

    public abstract Relatedgoods updateByUpdater(Updater<Relatedgoods> paramUpdater);

    public abstract Relatedgoods deleteById(Long paramLong);
}

