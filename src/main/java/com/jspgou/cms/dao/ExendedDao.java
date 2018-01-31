package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Exended;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ExendedDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract Exended findById(Long paramLong);

  public abstract List<Exended> getList();

  public abstract List<Exended> getList(Long paramLong);

  public abstract Exended save(Exended paramExended);

  public abstract Exended updateByUpdater(Updater<Exended> paramUpdater);

  public abstract Exended deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ExendedDao
 * JD-Core Version:    0.6.0
 */