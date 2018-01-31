package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ProductType;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ProductTypeDao
{
  public abstract List<ProductType> getList(Long paramLong);

  public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

  public abstract ProductType findById(Long paramLong);

  public abstract ProductType save(ProductType paramProductType);

  public abstract ProductType updateByUpdater(Updater<ProductType> paramUpdater);

  public abstract ProductType deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ProductTypeDao
 * JD-Core Version:    0.6.0
 */