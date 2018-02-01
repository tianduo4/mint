package com.mint.cms.manager;

import com.mint.cms.entity.Address;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface AddressMng {
    public abstract List<Address> getListById(Long paramLong);

    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Pagination getPageByParentId(Long paramLong, int paramInt1, int paramInt2);

    public abstract Address findById(Long paramLong);

    public abstract Address save(Address paramAddress);

    public abstract Address update(Address paramAddress);

    public abstract Address deleteById(Long paramLong);

    public abstract Address[] deleteByIds(Long[] paramArrayOfLong);

    public abstract Address[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);
}

