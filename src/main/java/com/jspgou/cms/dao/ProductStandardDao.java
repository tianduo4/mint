package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ProductStandard;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ProductStandardDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ProductStandard findById(Long paramLong);

  public abstract List<ProductStandard> findByProductIdAndStandardId(Long paramLong1, Long paramLong2);

  public abstract List<ProductStandard> findByProductId(Long paramLong);

  public abstract ProductStandard save(ProductStandard paramProductStandard);

  public abstract ProductStandard updateByUpdater(Updater<ProductStandard> paramUpdater);

  public abstract ProductStandard deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ProductStandardDao
 * JD-Core Version:    0.6.0
 */