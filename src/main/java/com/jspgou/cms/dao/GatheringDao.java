package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Gathering;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface GatheringDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract List<Gathering> getlist(Long paramLong);

  public abstract Gathering findById(Long paramLong);

  public abstract Gathering save(Gathering paramGathering);

  public abstract Gathering updateByUpdater(Updater<Gathering> paramUpdater);

  public abstract Gathering deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.GatheringDao
 * JD-Core Version:    0.6.0
 */