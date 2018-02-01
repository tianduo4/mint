package com.mint.cms.dao;

import com.mint.cms.entity.ProductTag;
import com.mint.common.hibernate4.Updater;

import java.util.List;

public abstract interface ProductTagDao {
    public abstract List<ProductTag> getList(Long paramLong);

    public abstract ProductTag findById(Long paramLong);

    public abstract ProductTag save(ProductTag paramProductTag);

    public abstract ProductTag updateByUpdater(Updater<ProductTag> paramUpdater);

    public abstract ProductTag deleteById(Long paramLong);
}

