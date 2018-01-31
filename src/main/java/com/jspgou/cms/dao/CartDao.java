package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Cart;

public abstract interface CartDao
{
  public abstract Cart findById(Long paramLong);

  public abstract Cart saveOrUpdate(Cart paramCart);

  public abstract Cart update(Cart paramCart);

  public abstract Cart deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.CartDao
 * JD-Core Version:    0.6.0
 */