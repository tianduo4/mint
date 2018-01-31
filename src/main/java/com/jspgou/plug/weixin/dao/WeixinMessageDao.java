package com.jspgou.plug.weixin.dao;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.plug.weixin.entity.WeixinMessage;
import java.util.List;

public abstract interface WeixinMessageDao
{
  public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

  public abstract List<WeixinMessage> getList(Long paramLong);

  public abstract WeixinMessage getWelcome(Long paramLong);

  public abstract WeixinMessage findByNumber(String paramString, Long paramLong);

  public abstract WeixinMessage save(WeixinMessage paramWeixinMessage);

  public abstract WeixinMessage findById(Integer paramInteger);

  public abstract WeixinMessage deleteById(Integer paramInteger);

  public abstract WeixinMessage updateByUpdater(Updater<WeixinMessage> paramUpdater);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.dao.WeixinMessageDao
 * JD-Core Version:    0.6.0
 */