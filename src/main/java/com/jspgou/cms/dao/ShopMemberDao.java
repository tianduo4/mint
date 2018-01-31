package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopMember;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ShopMemberDao
{
  public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

  public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2, String paramString);

  public abstract ShopMember findById(Long paramLong);

  public abstract ShopMember save(ShopMember paramShopMember);

  public abstract ShopMember updateByUpdater(Updater<ShopMember> paramUpdater);

  public abstract ShopMember deleteById(Long paramLong);

  public abstract ShopMember findUsername(String paramString);

  public abstract ShopMember findByUsername(String paramString);

  public abstract List<ShopMember> getList(String paramString, Long paramLong);

  public abstract int countByUsername(String paramString);

  public abstract ShopMember getByUserId(Long paramLong1, Long paramLong2);

  public abstract Long getMemberCount(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShopMemberDao
 * JD-Core Version:    0.6.0
 */