package com.jspgou.cms.manager;

import com.jspgou.cms.entity.PaymentPlugins;
import java.util.List;

public abstract interface PaymentPluginsMng
{
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

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.PaymentPluginsMng
 * JD-Core Version:    0.6.0
 */