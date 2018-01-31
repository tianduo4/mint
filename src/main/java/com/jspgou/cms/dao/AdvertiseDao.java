package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Advertise;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface AdvertiseDao
{
  public abstract Pagination getPage(Integer paramInteger, Boolean paramBoolean, int paramInt1, int paramInt2);

  public abstract List<Advertise> getList(Integer paramInteger, Boolean paramBoolean);

  public abstract Advertise findById(Integer paramInteger);

  public abstract Advertise save(Advertise paramAdvertise);

  public abstract Advertise updateByUpdater(Updater<Advertise> paramUpdater);

  public abstract Advertise deleteById(Integer paramInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.AdvertiseDao
 * JD-Core Version:    0.6.0
 */