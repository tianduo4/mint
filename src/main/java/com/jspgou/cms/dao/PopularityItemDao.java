package com.jspgou.cms.dao;

import com.jspgou.cms.entity.PopularityItem;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface PopularityItemDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract List<PopularityItem> getlist(Long paramLong1, Long paramLong2);

  public abstract PopularityItem findById(Long paramLong1, Long paramLong2);

  public abstract PopularityItem findById(Long paramLong);

  public abstract PopularityItem save(PopularityItem paramPopularityItem);

  public abstract PopularityItem updateByUpdater(Updater<PopularityItem> paramUpdater);

  public abstract PopularityItem deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.PopularityItemDao
 * JD-Core Version:    0.6.0
 */