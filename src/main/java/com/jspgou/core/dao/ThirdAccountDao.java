package com.jspgou.core.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.core.entity.ThirdAccount;

public abstract interface ThirdAccountDao
{
  public abstract Pagination getPage(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract ThirdAccount findById(Long paramLong);

  public abstract ThirdAccount findByKey(String paramString);

  public abstract ThirdAccount save(ThirdAccount paramThirdAccount);

  public abstract ThirdAccount updateByUpdater(Updater<ThirdAccount> paramUpdater);

  public abstract ThirdAccount deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.ThirdAccountDao
 * JD-Core Version:    0.6.0
 */