package com.jspgou.core.manager;

import com.jspgou.common.page.Pagination;
import com.jspgou.core.entity.Website;
import java.util.List;
import java.util.Map;

public abstract interface WebsiteMng
{
  public abstract Website getWebsite(String paramString);

  public abstract Pagination getAllPage(int paramInt1, int paramInt2);

  public abstract List<Website> getAllList();

  public abstract Website findById(Long paramLong);

  public abstract Website save(Website paramWebsite);

  public abstract Website update1(Website paramWebsite, Integer paramInteger1, Integer paramInteger2);

  public abstract Website update(Website paramWebsite);

  public abstract Website deleteById(Long paramLong);

  public abstract Website[] deleteByIds(Long[] paramArrayOfLong);

  public abstract Website get();

  public abstract void updateSsoAttr(Map<String, String> paramMap);

  public abstract void updateAttr(Long paramLong, Map<String, String> paramMap);

  public abstract Website findByDomain(String paramString, boolean paramBoolean);

  public abstract List<Website> getListFromCache();

  public abstract void updateTplSolution(Long paramLong, String paramString, boolean paramBoolean);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.WebsiteMng
 * JD-Core Version:    0.6.0
 */