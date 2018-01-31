package com.jspgou.plug.weixin.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.plug.weixin.entity.Weixin;

public abstract interface WeixinDao
{
  public abstract Pagination getPage(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract Weixin save(Weixin paramWeixin);

  public abstract Weixin deleteById(Integer paramInteger);

  public abstract Weixin findById(Integer paramInteger);

  public abstract Weixin find(Long paramLong);

  public abstract Weixin updateByUpdater(Updater<Weixin> paramUpdater);

  public abstract Weixin findBy();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.dao.WeixinDao
 * JD-Core Version:    0.6.0
 */