package com.jspgou.cms.dao;

import com.jspgou.cms.entity.PopularityGroup;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface PopularityGroupDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract PopularityGroup findById(Long paramLong);

  public abstract PopularityGroup save(PopularityGroup paramPopularityGroup);

  public abstract PopularityGroup updateByUpdater(Updater<PopularityGroup> paramUpdater);

  public abstract PopularityGroup deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.PopularityGroupDao
 * JD-Core Version:    0.6.0
 */