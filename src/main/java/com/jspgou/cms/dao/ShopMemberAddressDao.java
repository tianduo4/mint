package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopMemberAddress;
import com.jspgou.common.hibernate4.Updater;
import java.util.List;

public abstract interface ShopMemberAddressDao
{
  public abstract List<ShopMemberAddress> getList(Long paramLong);

  public abstract List<ShopMemberAddress> findByMemberDefault(Long paramLong, Boolean paramBoolean);

  public abstract ShopMemberAddress findById(Long paramLong);

  public abstract ShopMemberAddress save(ShopMemberAddress paramShopMemberAddress);

  public abstract ShopMemberAddress updateByUpdater(Updater<ShopMemberAddress> paramUpdater);

  public abstract ShopMemberAddress deleteById(Long paramLong);

  public abstract void deleteByMemberId(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShopMemberAddressDao
 * JD-Core Version:    0.6.0
 */