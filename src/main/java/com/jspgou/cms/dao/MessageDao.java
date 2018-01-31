package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Message;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface MessageDao
{
  public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

  public abstract Message findById(Long paramLong);

  public abstract Message save(Message paramMessage);

  public abstract Message updateByUpdater(Updater<Message> paramUpdater);

  public abstract Message deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.MessageDao
 * JD-Core Version:    0.6.0
 */