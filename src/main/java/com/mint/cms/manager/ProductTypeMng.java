package com.mint.cms.manager;

import com.mint.cms.entity.ProductType;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface ProductTypeMng {
    public abstract List<ProductType> getList(Long paramLong);

    public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

    public abstract ProductType findById(Long paramLong);

    public abstract ProductType save(ProductType paramProductType);

    public abstract ProductType update(ProductType paramProductType);

    public abstract ProductType deleteById(Long paramLong);

    public abstract ProductType[] deleteByIds(Long[] paramArrayOfLong);
}

