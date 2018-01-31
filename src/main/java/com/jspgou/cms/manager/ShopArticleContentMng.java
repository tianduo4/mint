package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopArticle;
import com.jspgou.cms.entity.ShopArticleContent;
import com.jspgou.common.page.Pagination;

public abstract interface ShopArticleContentMng
{
  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract ShopArticleContent findById(Long paramLong);

  public abstract ShopArticleContent save(String paramString, ShopArticle paramShopArticle);

  public abstract ShopArticleContent update(ShopArticleContent paramShopArticleContent);

  public abstract ShopArticleContent deleteById(Long paramLong);

  public abstract ShopArticleContent[] deleteByIds(Long[] paramArrayOfLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ShopArticleContentMng
 * JD-Core Version:    0.6.0
 */