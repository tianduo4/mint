package com.mint.cms.dao;

import com.mint.cms.entity.PaymentPlugins;
import com.mint.common.hibernate4.Updater;

import java.util.List;

public abstract interface PaymentPluginsDao {
    public abstract List<PaymentPlugins> getList();

    public abstract PaymentPlugins findByCode(String paramString);

    public abstract PaymentPlugins findById(Long paramLong);

    public abstract PaymentPlugins save(PaymentPlugins paramPaymentPlugins);

    public abstract PaymentPlugins updateByUpdater(Updater<PaymentPlugins> paramUpdater);

    public abstract PaymentPlugins deleteById(Long paramLong);

    public abstract List<PaymentPlugins> getList1(String paramString);
}

