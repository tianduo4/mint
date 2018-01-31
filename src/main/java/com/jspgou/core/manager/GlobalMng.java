package com.jspgou.core.manager;

import com.jspgou.core.entity.ConfigAttr;
import com.jspgou.core.entity.Global;

public abstract interface GlobalMng
{
  public abstract Global get();

  public abstract Global findById(Long paramLong);

  public abstract Global update(Global paramGlobal);

  public abstract void updateConfigAttr(ConfigAttr paramConfigAttr);

  public abstract Global findIdkey();

  public abstract Global updateGlobalPwd(Long paramLong, String paramString);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.GlobalMng
 * JD-Core Version:    0.6.0
 */