package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopMemberAddress;
import java.util.List;

public abstract interface ShopMemberAddressMng
{
  public abstract List<ShopMemberAddress> getList(Long paramLong);

  public abstract ShopMemberAddress findByMemberDefault(Long paramLong, Boolean paramBoolean);

  public abstract List<ShopMemberAddress> findByMemberId(Long paramLong);

  public abstract ShopMemberAddress findById(Long paramLong);

  public abstract ShopMemberAddress save(ShopMemberAddress paramShopMemberAddress);

  public abstract ShopMemberAddress updateByUpdater(ShopMemberAddress paramShopMemberAddress);

  public abstract ShopMemberAddress deleteById(Long paramLong1, Long paramLong2);

  public abstract void deleteByMemberId(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ShopMemberAddressMng
 * JD-Core Version:    0.6.0
 */