package com.jspgou.plug.weixin.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.plug.weixin.entity.WeixinMenu;
import java.util.List;

public abstract interface WeixinMenuDao
{
  public abstract Pagination getPage(Long paramLong, Integer paramInteger, int paramInt1, int paramInt2);

  public abstract List<WeixinMenu> getList(Long paramLong, Integer paramInteger);

  public abstract WeixinMenu findById(Integer paramInteger);

  public abstract WeixinMenu save(WeixinMenu paramWeixinMenu);

  public abstract WeixinMenu updateByUpdater(Updater<WeixinMenu> paramUpdater);

  public abstract WeixinMenu deleteById(Integer paramInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.dao.WeixinMenuDao
 * JD-Core Version:    0.6.0
 */