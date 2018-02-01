package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Payment;

import java.util.List;

public abstract interface PaymentMng {
    public abstract List<Payment> getListForCart(Long paramLong);

    public abstract Payment[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);

    public abstract List<Payment> getList(Long paramLong, boolean paramBoolean);

    public abstract List<Payment> getByCode(String paramString, Long paramLong);

    public abstract Payment findById(Long paramLong);

    public abstract Payment save(Payment paramPayment);

    public abstract Payment update(Payment paramPayment);

    public abstract Payment deleteById(Long paramLong);

    public abstract Payment[] deleteByIds(Long[] paramArrayOfLong);

    public abstract void addShipping(Payment paramPayment, long[] paramArrayOfLong);

    public abstract void updateShipping(Payment paramPayment, long[] paramArrayOfLong);
}

