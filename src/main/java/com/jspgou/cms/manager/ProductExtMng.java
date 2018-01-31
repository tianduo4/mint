package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductExt;

public abstract interface ProductExtMng
{
  public abstract ProductExt save(ProductExt paramProductExt, Product paramProduct);

  public abstract ProductExt saveOrUpdate(ProductExt paramProductExt, Product paramProduct);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ProductExtMng
 * JD-Core Version:    0.6.0
 */