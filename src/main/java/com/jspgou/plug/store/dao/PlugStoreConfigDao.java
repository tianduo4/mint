package com.jspgou.plug.store.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.plug.store.entity.PlugStoreConfig;

public abstract interface PlugStoreConfigDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract PlugStoreConfig findById(Integer paramInteger);

  public abstract PlugStoreConfig save(PlugStoreConfig paramPlugStoreConfig);

  public abstract PlugStoreConfig updateByUpdater(Updater<PlugStoreConfig> paramUpdater);

  public abstract PlugStoreConfig deleteById(Integer paramInteger);

  public abstract Class<PlugStoreConfig> getEntityClass();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.store.dao.PlugStoreConfigDao
 * JD-Core Version:    0.6.0
 */