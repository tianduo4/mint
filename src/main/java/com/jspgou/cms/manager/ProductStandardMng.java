package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ProductStandard;
import com.jspgou.common.page.Pagination;

import java.util.List;

public abstract interface ProductStandardMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract ProductStandard findById(Long paramLong);

    public abstract ProductStandard findByProductIdAndStandardId(Long paramLong1, Long paramLong2);

    public abstract List<ProductStandard> findByProductId(Long paramLong);

    public abstract ProductStandard save(ProductStandard paramProductStandard);

    public abstract ProductStandard update(ProductStandard paramProductStandard);

    public abstract ProductStandard deleteById(Long paramLong);

    public abstract ProductStandard[] deleteByIds(Long[] paramArrayOfLong);
}

