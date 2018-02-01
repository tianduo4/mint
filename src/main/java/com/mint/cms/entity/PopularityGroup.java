package com.mint.cms.entity;

import com.mint.cms.entity.base.BasePopularityGroup;

import java.util.HashSet;
import java.util.Set;

public class PopularityGroup extends BasePopularityGroup {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (getPrice() == null) {
            setPrice(Double.valueOf(0.0D));
        }
        if (getPrivilege() == null)
            setPrivilege(Double.valueOf(0.0D));
    }

    public void addToProducts(Product product) {
        if (product == null) {
            return;
        }
        Set set = getProducts();
        if (set == null) {
            set = new HashSet();
            setProducts(set);
        }
        set.add(product);
    }

    public PopularityGroup() {
    }

    public PopularityGroup(Long id) {
        super(id);
    }

    public PopularityGroup(Long id, String name, Double price, Double privilege) {
        super(id,
                name,
                price,
                privilege);
    }
}

