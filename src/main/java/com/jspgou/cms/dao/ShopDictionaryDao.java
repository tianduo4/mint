package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopDictionary;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ShopDictionaryDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract Pagination getPage(String paramString, Long paramLong, int paramInt1, int paramInt2);

  public abstract ShopDictionary findById(Long paramLong);

  public abstract List<ShopDictionary> getListByType(Long paramLong);

  public abstract ShopDictionary save(ShopDictionary paramShopDictionary);

  public abstract ShopDictionary updateByUpdater(Updater<ShopDictionary> paramUpdater);

  public abstract ShopDictionary deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShopDictionaryDao
 * JD-Core Version:    0.6.0
 */