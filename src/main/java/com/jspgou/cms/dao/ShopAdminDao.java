package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface ShopAdminDao
{
  public abstract ShopAdmin getByUsername(String paramString);

  public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

  public abstract ShopAdmin findById(Long paramLong);

  public abstract ShopAdmin save(ShopAdmin paramShopAdmin);

  public abstract ShopAdmin updateByUpdater(Updater<ShopAdmin> paramUpdater);

  public abstract ShopAdmin deleteById(Long paramLong);

  public abstract ShopAdmin findByName(String paramString);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShopAdminDao
 * JD-Core Version:    0.6.0
 */