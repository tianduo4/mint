package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopConfig;

public abstract interface ShopConfigMng
{
  public abstract ShopConfig findById(Long paramLong);

  public abstract ShopConfig save(ShopConfig paramShopConfig);

  public abstract ShopConfig update(ShopConfig paramShopConfig);

  public abstract ShopConfig deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ShopConfigMng
 * JD-Core Version:    0.6.0
 */