package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopDictionaryType;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ShopDictionaryTypeMng
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ShopDictionaryType findById(Long paramLong);

  public abstract List<ShopDictionaryType> findAll();

  public abstract ShopDictionaryType save(ShopDictionaryType paramShopDictionaryType);

  public abstract ShopDictionaryType update(ShopDictionaryType paramShopDictionaryType);

  public abstract ShopDictionaryType deleteById(Long paramLong);

  public abstract ShopDictionaryType[] deleteByIds(Long[] paramArrayOfLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ShopDictionaryTypeMng
 * JD-Core Version:    0.6.0
 */