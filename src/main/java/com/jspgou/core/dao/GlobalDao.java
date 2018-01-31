package com.jspgou.core.dao;

import com.jspgou.core.entity.Global;

public abstract interface GlobalDao
{
  public abstract Global findById(Long paramLong);

  public abstract Global update(Global paramGlobal);

  public abstract Global findIdkey();
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.GlobalDao
 * JD-Core Version:    0.6.0
 */