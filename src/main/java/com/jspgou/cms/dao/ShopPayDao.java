package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopPay;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface ShopPayDao
{
  public abstract Pagination getPageShopPay(int paramInt1, int paramInt2);

  public abstract ShopPay findById(Integer paramInteger);

  public abstract ShopPay save(ShopPay paramShopPay);

  public abstract ShopPay updateByUpdater(Updater<ShopPay> paramUpdater);

  public abstract ShopPay deleteById(Integer paramInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShopPayDao
 * JD-Core Version:    0.6.0
 */