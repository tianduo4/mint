package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopScore;
import com.jspgou.common.page.Pagination;
import java.util.Date;
import java.util.List;

public abstract interface ShopScoreMng
{
  public abstract Pagination getPage(Long paramLong, Boolean paramBoolean1, Boolean paramBoolean2, Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2);

  public abstract List<ShopScore> getlist(String paramString);

  public abstract ShopScore findById(Long paramLong);

  public abstract ShopScore save(ShopScore paramShopScore);

  public abstract ShopScore update(ShopScore paramShopScore);

  public abstract ShopScore deleteById(Long paramLong);

  public abstract ShopScore[] deleteByIds(Long[] paramArrayOfLong);

  public abstract void deleteByMemberId(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ShopScoreMng
 * JD-Core Version:    0.6.0
 */