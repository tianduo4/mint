package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopShipments;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ShopShipmentsDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ShopShipments findById(Long paramLong);

  public abstract ShopShipments save(ShopShipments paramShopShipments);

  public abstract ShopShipments updateByUpdater(Updater<ShopShipments> paramUpdater);

  public abstract ShopShipments deleteById(Long paramLong);

  public abstract List<ShopShipments> getList(Boolean paramBoolean);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShopShipmentsDao
 * JD-Core Version:    0.6.0
 */