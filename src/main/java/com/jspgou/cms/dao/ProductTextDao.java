package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ProductText;
import com.jspgou.common.hibernate4.Updater;

public abstract interface ProductTextDao
{
  public abstract ProductText updateByUpdater(Updater<ProductText> paramUpdater);

  public abstract ProductText save(ProductText paramProductText);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ProductTextDao
 * JD-Core Version:    0.6.0
 */