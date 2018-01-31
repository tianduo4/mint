package com.jspgou.core.manager;

import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.Website;

public abstract interface AdminMng
{
  public abstract Admin getByUsername(String paramString);

  public abstract Admin getByUserId(Long paramLong1, Long paramLong2);

  public abstract Admin register(String paramString1, String paramString2, String paramString3, String paramString4, Boolean paramBoolean1, Website paramWebsite, Boolean paramBoolean2);

  public abstract Admin findById(Long paramLong);

  public abstract Admin save(Admin paramAdmin);

  public abstract Admin update(Admin paramAdmin);

  public abstract Admin deleteById(Long paramLong);

  public abstract Admin[] deleteByIds(Long[] paramArrayOfLong);

  public abstract void updateRole(Admin paramAdmin, Integer[] paramArrayOfInteger);

  public abstract void addRole(Admin paramAdmin, Integer[] paramArrayOfInteger);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.AdminMng
 * JD-Core Version:    0.6.0
 */