package com.mint.cms.manager;

import com.mint.cms.entity.Relatedgoods;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface RelatedgoodsMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract List<Relatedgoods> findByIdProductId(Long paramLong);

    public abstract Relatedgoods findById(Long paramLong);

    public abstract Relatedgoods save(Relatedgoods paramRelatedgoods);

    public abstract Relatedgoods update(Relatedgoods paramRelatedgoods);

    public abstract Relatedgoods deleteById(Long paramLong);

    public abstract Relatedgoods[] deleteByIds(Long[] paramArrayOfLong);

    public abstract void addProduct(Long paramLong, Long[] paramArrayOfLong);

    public abstract void updateProduct(Long paramLong, Long[] paramArrayOfLong);

    public abstract void deleteProduct(Long paramLong);
}

