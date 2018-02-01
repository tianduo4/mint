package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseProductTag;
import com.mint.core.entity.Website;

public class ProductTag extends BaseProductTag {
    private static final long serialVersionUID = 1L;

    public void increaseCount() {
        addCount(1);
    }

    public void reduceCount() {
        addCount(-1);
    }

    public void addCount(int count) {
        Integer c = getCount();
        if (c != null) {
            count += c.intValue();
        }
        if (count < 0) {
            count = 0;
        }
        setCount(Integer.valueOf(count));
    }

    public void init() {
        setCount(Integer.valueOf(0));
    }

    public ProductTag() {
    }

    public ProductTag(Long id) {
        super(id);
    }

    public ProductTag(Long id, Website website, String name, Integer count) {
        super(id,
                website,
                name,
                count);
    }
}

