package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ExendedItem;
import com.jspgou.common.page.Pagination;

public abstract interface ExendedItemMng
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ExendedItem findById(Long paramLong);

  public abstract ExendedItem save(ExendedItem paramExendedItem);

  public abstract ExendedItem update(ExendedItem paramExendedItem);

  public abstract ExendedItem deleteById(Long paramLong);

  public abstract ExendedItem[] deleteByIds(Long[] paramArrayOfLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ExendedItemMng
 * JD-Core Version:    0.6.0
 */