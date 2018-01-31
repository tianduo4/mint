package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ProductTag;
import java.util.List;

public abstract interface ProductTagMng
{
  public abstract List<ProductTag> getList(Long paramLong);

  public abstract ProductTag findById(Long paramLong);

  public abstract ProductTag save(ProductTag paramProductTag);

  public abstract ProductTag[] updateTagName(Long[] paramArrayOfLong, String[] paramArrayOfString);

  public abstract ProductTag[] deleteByIds(Long[] paramArrayOfLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ProductTagMng
 * JD-Core Version:    0.6.0
 */