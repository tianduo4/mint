package com.jspgou.core.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.core.entity.Website;
import java.util.List;

public abstract interface WebsiteDao
{
  public abstract Website getUniqueWebsite();

  public abstract int countWebsite();

  public abstract Website findByDomain(String paramString);

  public abstract Pagination getAllPage(int paramInt1, int paramInt2);

  public abstract List<Website> getAllList();

  public abstract Website findById(Long paramLong);

  public abstract Website findById1(Long paramLong);

  public abstract Website save(Website paramWebsite);

  public abstract Website updateByUpdater(Updater<Website> paramUpdater);

  public abstract Website deleteById(Long paramLong);

  public abstract Website findByDomain(String paramString, boolean paramBoolean);

  public abstract List<Website> getList(boolean paramBoolean);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.WebsiteDao
 * JD-Core Version:    0.6.0
 */