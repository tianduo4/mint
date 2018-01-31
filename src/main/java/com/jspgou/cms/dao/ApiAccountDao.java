package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ApiAccount;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface ApiAccountDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ApiAccount findById(Long paramLong);

  public abstract ApiAccount findByAppId(String paramString);

  public abstract ApiAccount save(ApiAccount paramApiAccount);

  public abstract ApiAccount updateByUpdater(Updater<ApiAccount> paramUpdater);

  public abstract ApiAccount deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ApiAccountDao
 * JD-Core Version:    0.6.0
 */