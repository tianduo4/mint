package com.mint.cms.dao;

import com.mint.cms.entity.Address;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface AddressDao {
    public abstract List<Address> getListById(Long paramLong);

    public abstract Pagination getPage(int paramInt1, int paramInt2);

    public abstract Pagination getPageByParentId(Long paramLong, int paramInt1, int paramInt2);

    public abstract Address findById(Long paramLong);

    public abstract Address save(Address paramAddress);

    public abstract Address updateByUpdater(Updater<Address> paramUpdater);

    public abstract Address deleteById(Long paramLong);
}

