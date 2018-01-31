package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Brand;
import com.jspgou.common.page.Pagination;
import java.util.List;

public abstract interface BrandMng
{
  public abstract List<Brand> getAllList();

  public abstract List<Brand> getListByCateoryId(Integer paramInteger);

  public abstract List<Brand> getBrandList(String paramString);

  public abstract Pagination getPage(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<Brand> getList();

  public abstract List<Brand> getListForTag(Long paramLong, int paramInt1, int paramInt2);

  public abstract Brand findById(Long paramLong);

  public abstract Brand save(Brand paramBrand, String paramString);

  public abstract Brand update(Brand paramBrand, String paramString);

  public abstract Brand deleteById(Long paramLong);

  public abstract Brand[] deleteByIds(Long[] paramArrayOfLong);

  public abstract Brand[] updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger);

  public abstract Brand getsiftBrand();

  public abstract boolean brandNameNotExist(String paramString);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.BrandMng
 * JD-Core Version:    0.6.0
 */