package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Brand;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface BrandDao
{
  public abstract List<Brand> getBrandList(String paramString);

  public abstract Pagination getPage(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<Brand> getAllList();

  public abstract List<Brand> getList();

  public abstract List<Brand> getList(Long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract Brand findById(Long paramLong);

  public abstract Brand save(Brand paramBrand);

  public abstract Brand updateByUpdater(Updater<Brand> paramUpdater);

  public abstract Brand deleteById(Long paramLong);

  public abstract List<Brand> getListByCate(Integer paramInteger);

  public abstract Brand getsiftBrand();

  public abstract int countByBrandName(String paramString);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.BrandDao
 * JD-Core Version:    0.6.0
 */