package com.mint.cms.manager;

import com.mint.cms.entity.ProductTag;

import java.util.List;

public abstract interface ProductTagMng {
    public abstract List<ProductTag> getList(Long paramLong);

    public abstract ProductTag findById(Long paramLong);

    public abstract ProductTag save(ProductTag paramProductTag);

    public abstract ProductTag[] updateTagName(Long[] paramArrayOfLong, String[] paramArrayOfString);

    public abstract ProductTag[] deleteByIds(Long[] paramArrayOfLong);
}

