package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ApiInfo;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface ApiInfoDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ApiInfo findById(Long paramLong);

  public abstract ApiInfo findByApiUrl(String paramString);

  public abstract ApiInfo save(ApiInfo paramApiInfo);

  public abstract ApiInfo updateByUpdater(Updater<ApiInfo> paramUpdater);

  public abstract ApiInfo deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ApiInfoDao
 * JD-Core Version:    0.6.0
 */