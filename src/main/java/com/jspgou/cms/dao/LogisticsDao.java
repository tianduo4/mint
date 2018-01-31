package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Logistics;
import com.jspgou.common.hibernate4.Updater;
import java.util.List;

public abstract interface LogisticsDao
{
  public abstract List<Logistics> getAllList();

  public abstract Logistics findById(Long paramLong);

  public abstract Logistics save(Logistics paramLogistics);

  public abstract Logistics updateByUpdater(Updater<Logistics> paramUpdater);

  public abstract Logistics deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.LogisticsDao
 * JD-Core Version:    0.6.0
 */