package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Brand;
import com.jspgou.cms.entity.BrandText;

public abstract interface BrandTextMng
{
  public abstract BrandText save(Brand paramBrand, String paramString);

  public abstract BrandText update(BrandText paramBrandText);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.BrandTextMng
 * JD-Core Version:    0.6.0
 */