package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ApiRecord;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface ApiRecordDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ApiRecord findById(Long paramLong);

  public abstract ApiRecord save(ApiRecord paramApiRecord);

  public abstract ApiRecord updateByUpdater(Updater<ApiRecord> paramUpdater);

  public abstract ApiRecord deleteById(Long paramLong);

  public abstract ApiRecord findBySign(String paramString1, String paramString2);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ApiRecordDao
 * JD-Core Version:    0.6.0
 */