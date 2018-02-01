package com.mint.cms.dao;

import com.mint.cms.entity.CustomerService;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

public abstract interface CustomerServiceDao {
    public abstract Pagination getPagination(Boolean paramBoolean, int paramInt1, int paramInt2);

    public abstract List<CustomerService> getList(Boolean paramBoolean);

    public abstract CustomerService findById(Long paramLong);

    public abstract CustomerService updateByUpdater(Updater<CustomerService> paramUpdater);

    public abstract CustomerService deleteById(Long paramLong);

    public abstract CustomerService save(CustomerService paramCustomerService);
}

