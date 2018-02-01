package com.mint.cms.dao;

import com.mint.cms.entity.Category;
import com.mint.common.hibernate4.Updater;

import java.util.List;

public abstract interface CategoryDao {
    public abstract List<Category> getAll(Long paramLong);

    public abstract Category getByPath(Long paramLong, String paramString, boolean paramBoolean);

    public abstract List<Category> getListForParent(Long paramLong, Integer paramInteger);

    public abstract List<Category> getListForChild(Long paramLong, Integer paramInteger);

    public abstract List<Category> getTopList(Long paramLong, boolean paramBoolean);

    public abstract List<Category> getChildList(Long paramLong, Integer paramInteger);

    public abstract int countPath(Long paramLong, String paramString);

    public abstract Category findById(Integer paramInteger);

    public abstract Category save(Category paramCategory);

    public abstract Category updateByUpdater(Updater<Category> paramUpdater);

    public abstract Category deleteById(Integer paramInteger);

    public abstract List<Category> getListByptype(Long paramLong1, Long paramLong2, Integer paramInteger);
}

