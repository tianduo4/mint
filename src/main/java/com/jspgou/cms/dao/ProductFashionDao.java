package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ProductFashionDao
{
  public abstract Pagination getPage(Long paramLong, int paramInt1, int paramInt2);

  public abstract ProductFashion save(ProductFashion paramProductFashion);

  public abstract ProductFashion deleteById(Long paramLong);

  public abstract ProductFashion updateByUpdater(Updater<ProductFashion> paramUpdater);

  public abstract ProductFashion findById(Long paramLong);

  public abstract List<ProductFashion> findByProductId(Long paramLong);

  public abstract Integer productLackCount(Integer paramInteger1, Integer paramInteger2);

  public abstract Pagination productLack(Integer paramInteger1, Integer paramInteger2, int paramInt1, int paramInt2);

  public abstract Pagination getSaleTopPage(int paramInt1, int paramInt2);

  public abstract ProductFashion getPfashion(Long paramLong1, Long paramLong2);

  public abstract Boolean getOneFashion(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ProductFashionDao
 * JD-Core Version:    0.6.0
 */