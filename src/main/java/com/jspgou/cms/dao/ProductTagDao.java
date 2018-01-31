package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ProductTag;
import com.jspgou.common.hibernate4.Updater;
import java.util.List;

public abstract interface ProductTagDao
{
  public abstract List<ProductTag> getList(Long paramLong);

  public abstract ProductTag findById(Long paramLong);

  public abstract ProductTag save(ProductTag paramProductTag);

  public abstract ProductTag updateByUpdater(Updater<ProductTag> paramUpdater);

  public abstract ProductTag deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ProductTagDao
 * JD-Core Version:    0.6.0
 */