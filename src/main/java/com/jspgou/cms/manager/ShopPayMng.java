package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopPay;

public abstract interface ShopPayMng
{
  public abstract ShopPay findById(Integer paramInteger);

  public abstract ShopPay save(ShopPay paramShopPay);

  public abstract ShopPay updateByUpdater(ShopPay paramShopPay);

  public abstract ShopPay deleteById(Integer paramInteger);

  public abstract ShopPay[] deleteByIds(Integer[] paramArrayOfInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ShopPayMng
 * JD-Core Version:    0.6.0
 */