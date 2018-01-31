package com.jspgou.cms.dao;

import com.jspgou.cms.entity.LogisticsText;
import com.jspgou.common.hibernate4.Updater;

public abstract interface LogisticsTextDao
{
  public abstract LogisticsText save(LogisticsText paramLogisticsText);

  public abstract LogisticsText updateByUpdater(Updater<LogisticsText> paramUpdater);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.LogisticsTextDao
 * JD-Core Version:    0.6.0
 */