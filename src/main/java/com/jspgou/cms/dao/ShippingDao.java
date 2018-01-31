package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Shipping;
import com.jspgou.common.hibernate4.Updater;
import java.util.List;

public abstract interface ShippingDao
{
  public abstract List<Shipping> getList(Long paramLong, boolean paramBoolean1, boolean paramBoolean2);

  public abstract Shipping findById(Long paramLong);

  public abstract Shipping save(Shipping paramShipping);

  public abstract Shipping updateByUpdater(Updater<Shipping> paramUpdater);

  public abstract Shipping deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShippingDao
 * JD-Core Version:    0.6.0
 */