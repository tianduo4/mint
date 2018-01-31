package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ApiUserLogin;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface ApiUserLoginDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ApiUserLogin findById(Long paramLong);

  public abstract ApiUserLogin save(ApiUserLogin paramApiUserLogin);

  public abstract ApiUserLogin updateByUpdater(Updater<ApiUserLogin> paramUpdater);

  public abstract ApiUserLogin deleteById(Long paramLong);

  public abstract ApiUserLogin findBySessionKey(String paramString);

  public abstract ApiUserLogin findByUsername(String paramString);

  public abstract ApiUserLogin findUserLogin(String paramString1, String paramString2);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ApiUserLoginDao
 * JD-Core Version:    0.6.0
 */