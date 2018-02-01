package com.mint.cms.manager;

import com.mint.cms.entity.PaymentPlugins;

import java.util.List;

public abstract interface PaymentPluginsMng {
    public abstract PaymentPlugins[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);

    public abstract List<PaymentPlugins> getList();

    public abstract PaymentPlugins findById(Long paramLong);

    public abstract PaymentPlugins findByCode(String paramString);

    public abstract PaymentPlugins save(PaymentPlugins paramPaymentPlugins);

    public abstract PaymentPlugins update(PaymentPlugins paramPaymentPlugins);

    public abstract PaymentPlugins deleteById(Long paramLong);

    public abstract PaymentPlugins[] deleteByIds(Long[] paramArrayOfLong);

    public abstract List<PaymentPlugins> getList1(String paramString);
}

