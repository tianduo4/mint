package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.LogisticsText;

public abstract interface LogisticsTextMng
{
  public abstract LogisticsText save(Logistics paramLogistics, String paramString);

  public abstract LogisticsText update(LogisticsText paramLogisticsText);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.LogisticsTextMng
 * JD-Core Version:    0.6.0
 */