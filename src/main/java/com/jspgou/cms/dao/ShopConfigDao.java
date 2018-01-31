package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopConfig;
import com.jspgou.common.hibernate4.Updater;

public abstract interface ShopConfigDao
{
  public abstract ShopConfig findById(Long paramLong);

  public abstract ShopConfig save(ShopConfig paramShopConfig);

  public abstract ShopConfig updateByUpdater(Updater<ShopConfig> paramUpdater);

  public abstract ShopConfig deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShopConfigDao
 * JD-Core Version:    0.6.0
 */