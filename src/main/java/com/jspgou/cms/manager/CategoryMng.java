package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Brand;
import com.jspgou.cms.entity.Category;

import java.util.List;
import java.util.Map;

public abstract interface CategoryMng {
    public abstract List<Category> getAll(Long paramLong);

    public abstract Category getByPath(Long paramLong, String paramString);

    public abstract Category getByPathForTag(Long paramLong, String paramString);

    public abstract List<Category> getListForParent(Long paramLong, Integer paramInteger);

    public abstract List<Category> getListForProduct(Long paramLong, Integer paramInteger);

    public abstract List<Category> getTopList(Long paramLong);

    public abstract List<Category> getChildList(Long paramLong, Integer paramInteger);

    public abstract List<Category> getTopListForTag(Long paramLong);

    public abstract List<Category> getList(Long paramLong);

    public abstract boolean checkPath(Long paramLong, String paramString);

    public abstract Category findById(Integer paramInteger);

    public abstract Category save(Category paramCategory, Integer paramInteger, Long paramLong, Long[] paramArrayOfLong1, Long[] paramArrayOfLong2);

    public abstract Category update(Category paramCategory, Integer paramInteger, Long paramLong, Long[] paramArrayOfLong1, Map<String, String> paramMap, Long[] paramArrayOfLong2);

    public abstract Category deleteById(Integer paramInteger);

    public abstract Category[] deleteByIds(Integer[] paramArrayOfInteger);

    public abstract Category[] updatePriority(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

    public abstract List<Category> getListBypType(Long paramLong1, Long paramLong2, Integer paramInteger);

    public abstract List<Brand> getBrandByCate(Integer paramInteger);
}

