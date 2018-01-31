package com.jspgou.cms.manager;

import com.jspgou.cms.entity.CustomerService;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface CustomerServiceMng
{
  public abstract Pagination getPagination(Boolean paramBoolean, int paramInt1, int paramInt2);

  public abstract List<CustomerService> getList();

  public abstract CustomerService findById(Long paramLong);

  public abstract CustomerService update(CustomerService paramCustomerService);

  public abstract CustomerService[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);

  public abstract CustomerService[] deleteByIds(Long[] paramArrayOfLong);

  public abstract CustomerService save(CustomerService paramCustomerService);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.CustomerServiceMng
 * JD-Core Version:    0.6.0
 */