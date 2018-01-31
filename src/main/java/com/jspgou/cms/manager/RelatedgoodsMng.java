package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Relatedgoods;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface RelatedgoodsMng
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract List<Relatedgoods> findByIdProductId(Long paramLong);

  public abstract Relatedgoods findById(Long paramLong);

  public abstract Relatedgoods save(Relatedgoods paramRelatedgoods);

  public abstract Relatedgoods update(Relatedgoods paramRelatedgoods);

  public abstract Relatedgoods deleteById(Long paramLong);

  public abstract Relatedgoods[] deleteByIds(Long[] paramArrayOfLong);

  public abstract void addProduct(Long paramLong, Long[] paramArrayOfLong);

  public abstract void updateProduct(Long paramLong, Long[] paramArrayOfLong);

  public abstract void deleteProduct(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.RelatedgoodsMng
 * JD-Core Version:    0.6.0
 */