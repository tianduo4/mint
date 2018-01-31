package com.jspgou.cms.manager;

import com.jspgou.cms.entity.OrderItem;
import com.jspgou.common.page.Pagination;
import java.util.Date;
import java.util.List;

public abstract interface OrderItemMng
{
  public abstract List<Object[]> profitTop(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2);

  public abstract Integer totalCount(Long paramLong1, Long paramLong2);

  public abstract List<Object[]> getOrderItem(Date paramDate1, Date paramDate2);

  public abstract Pagination getPageByMember(Integer paramInteger, Long paramLong, int paramInt1, int paramInt2);

  public abstract OrderItem findByMember(Long paramLong1, Long paramLong2, Long paramLong3);

  public abstract OrderItem findById(Long paramLong);

  public abstract OrderItem[] findById(Long[] paramArrayOfLong);

  public abstract OrderItem updateByUpdater(OrderItem paramOrderItem);

  public abstract OrderItem[] updateByUpdaters(OrderItem[] paramArrayOfOrderItem);

  public abstract Pagination getOrderItem(Integer paramInteger1, Integer paramInteger2, Long paramLong);

  public abstract Pagination getPageProductSaleRank(Long paramLong, String paramString, Integer paramInteger, int paramInt1, int paramInt2, Date paramDate1, Date paramDate2);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.OrderItemMng
 * JD-Core Version:    0.6.0
 */