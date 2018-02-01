package com.mint.cms.dao;

import com.mint.cms.entity.Payment;
import com.mint.common.hibernate4.Updater;

import java.util.List;

public abstract interface PaymentDao {
    public abstract List<Payment> getListForCart(Long paramLong, boolean paramBoolean);

    public abstract List<Payment> getList(Long paramLong, boolean paramBoolean);

    public abstract List<Payment> getByCode(String paramString, Long paramLong);

    public abstract Payment findById(Long paramLong);

    public abstract Payment save(Payment paramPayment);

    public abstract Payment updateByUpdater(Updater<Payment> paramUpdater);

    public abstract Payment deleteById(Long paramLong);
}

