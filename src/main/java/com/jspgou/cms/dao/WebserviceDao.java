package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Webservice;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface WebserviceDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract List<Webservice> getList(String paramString);

  public abstract Webservice findById(Integer paramInteger);

  public abstract Webservice save(Webservice paramWebservice);

  public abstract Webservice updateByUpdater(Updater<Webservice> paramUpdater);

  public abstract Webservice deleteById(Integer paramInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.WebserviceDao
 * JD-Core Version:    0.6.0
 */