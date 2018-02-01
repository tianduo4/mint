package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.entity.ProductTypeProperty;
import com.jspgou.common.page.Pagination;

import java.util.List;

public abstract interface ProductTypePropertyMng {
    public abstract Pagination getList(int paramInt1, int paramInt2, Long paramLong, Boolean paramBoolean, String paramString1, String paramString2);

    public abstract List<ProductTypeProperty> getList(Long paramLong, Boolean paramBoolean);

    public abstract void saveList(List<ProductTypeProperty> paramList);

    public abstract ProductTypeProperty findById(Long paramLong);

    public abstract ProductTypeProperty save(ProductTypeProperty paramProductTypeProperty);

    public abstract void updatePriority(Long[] paramArrayOfLong, Integer[] paramArrayOfInteger, String[] paramArrayOfString, Boolean[] paramArrayOfBoolean);

    public abstract ProductTypeProperty deleteById(Long paramLong);

    public abstract ProductTypeProperty[] deleteByIds(Long[] paramArrayOfLong);

    public abstract ProductTypeProperty update(ProductTypeProperty paramProductTypeProperty);

    public abstract List<ProductTypeProperty> findBySearch(String paramString1, String paramString2);

    public abstract List<ProductTypeProperty> findListByTypeId(Long paramLong);

    public abstract List<ProductTypeProperty> getList(Long paramLong, boolean paramBoolean);

    public abstract List<ProductTypeProperty> getItems(ProductType paramProductType, boolean paramBoolean, String[] paramArrayOfString1, String[] paramArrayOfString2, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Boolean[] paramArrayOfBoolean);
}

