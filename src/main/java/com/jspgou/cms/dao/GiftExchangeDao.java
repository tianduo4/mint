package com.jspgou.cms.dao;

import com.jspgou.cms.entity.GiftExchange;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface GiftExchangeDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract List<GiftExchange> getlist(Long paramLong);

  public abstract GiftExchange findById(Long paramLong);

  public abstract GiftExchange save(GiftExchange paramGiftExchange);

  public abstract GiftExchange updateByUpdater(Updater<GiftExchange> paramUpdater);

  public abstract GiftExchange deleteById(Long paramLong);

  public abstract void deleteByMemberId(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.GiftExchangeDao
 * JD-Core Version:    0.6.0
 */