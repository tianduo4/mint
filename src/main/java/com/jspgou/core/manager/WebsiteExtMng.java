package com.jspgou.core.manager;

import com.jspgou.core.entity.WebsiteExt.ConfigLogin;
import java.util.Map;

public abstract interface WebsiteExtMng
{
  public abstract Map<String, String> getMap();

  public abstract WebsiteExt.ConfigLogin getConfigLogin();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.WebsiteExtMng
 * JD-Core Version:    0.6.0
 */