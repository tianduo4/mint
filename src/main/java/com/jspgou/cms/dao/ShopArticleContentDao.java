package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopArticleContent;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

public abstract interface ShopArticleContentDao
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ShopArticleContent findById(Long paramLong);

  public abstract ShopArticleContent save(ShopArticleContent paramShopArticleContent);

  public abstract ShopArticleContent updateByUpdater(Updater<ShopArticleContent> paramUpdater);

  public abstract ShopArticleContent deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShopArticleContentDao
 * JD-Core Version:    0.6.0
 */