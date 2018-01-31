package com.jspgou.cms.dao;

import com.jspgou.cms.entity.ShopArticle;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ShopArticleDao
{
  public abstract Pagination getPage(Integer paramInteger, Long paramLong, int paramInt1, int paramInt2);

  public abstract Pagination getPageByChannel(Long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract Pagination getPageByWebsite(Long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract List<ShopArticle> getListByChannel(Integer paramInteger, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract List<ShopArticle> getListByWebsite(Long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract ShopArticle findById(Long paramLong);

  public abstract ShopArticle save(ShopArticle paramShopArticle);

  public abstract ShopArticle updateByUpdater(Updater<ShopArticle> paramUpdater);

  public abstract ShopArticle deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ShopArticleDao
 * JD-Core Version:    0.6.0
 */