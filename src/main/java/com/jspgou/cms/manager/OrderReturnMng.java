package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.OrderReturn;
import com.jspgou.common.page.Pagination;

public abstract interface OrderReturnMng
{
  public abstract Pagination getPage(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract OrderReturn findById(Long paramLong);

  public abstract OrderReturn findByOrderId(Long paramLong);

  public abstract OrderReturn save(OrderReturn paramOrderReturn);

  public abstract OrderReturn save(OrderReturn paramOrderReturn, Order paramOrder, Long paramLong, Boolean paramBoolean, String[] paramArrayOfString1, String[] paramArrayOfString2);

  public abstract OrderReturn update(OrderReturn paramOrderReturn);

  public abstract OrderReturn deleteById(Long paramLong);

  public abstract OrderReturn[] deleteByIds(Long[] paramArrayOfLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.OrderReturnMng
 * JD-Core Version:    0.6.0
 */