package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ProductExt;
import com.jspgou.common.hibernate4.Updater;

public abstract interface ProductExtDao
{
  public abstract ProductExt findById(Long paramLong);

  public abstract ProductExt save(ProductExt paramProductExt);

  public abstract ProductExt updateByUpdater(Updater<ProductExt> paramUpdater);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ProductExtDao
 * JD-Core Version:    0.6.0
 */