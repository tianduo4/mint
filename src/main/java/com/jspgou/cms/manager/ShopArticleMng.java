package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopArticle;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface ShopArticleMng
{
  public abstract Pagination getPage(Integer paramInteger, Long paramLong, int paramInt1, int paramInt2);

  public abstract Pagination getPageForTag(Long paramLong1, Long paramLong2, int paramInt1, int paramInt2);

  public abstract List<ShopArticle> getListForTag(Long paramLong, Integer paramInteger, int paramInt1, int paramInt2);

  public abstract ShopArticle findById(Long paramLong);

  public abstract ShopArticle save(ShopArticle paramShopArticle, Integer paramInteger, String paramString);

  public abstract ShopArticle update(ShopArticle paramShopArticle, Integer paramInteger, String paramString);

  public abstract ShopArticle deleteById(Long paramLong);

  public abstract ShopArticle[] deleteByIds(Long[] paramArrayOfLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ShopArticleMng
 * JD-Core Version:    0.6.0
 */