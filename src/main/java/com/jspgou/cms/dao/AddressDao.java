package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Address;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface AddressDao
{
  public abstract List<Address> getListById(Long paramLong);

  public abstract Pagination getPage(int paramInt1, int paramInt2);

  public abstract Pagination getPageByParentId(Long paramLong, int paramInt1, int paramInt2);

  public abstract Address findById(Long paramLong);

  public abstract Address save(Address paramAddress);

  public abstract Address updateByUpdater(Updater<Address> paramUpdater);

  public abstract Address deleteById(Long paramLong);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.AddressDao
 * JD-Core Version:    0.6.0
 */