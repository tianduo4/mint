package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseExended;

import java.util.HashSet;
import java.util.Set;

public class Exended extends BaseExended {
    private static final long serialVersionUID = 1L;

    public Exended() {
    }

    public Exended(Long id) {
        super(id);
    }

    public Exended(Long id, String name, String field) {
        super(id,
                name,
                field);
    }

    public void addToProductTypes(ProductType pType) {
        Set types = getProductTypes();
        if (types == null) {
            types = new HashSet();
            setProductTypes(types);
        }
        types.add(pType);
    }

    public Long[] getProductTypeIds() {
        Set<ProductType> set = getProductTypes();
        if (set == null) {
            return null;
        }
        Long[] ids = new Long[set.size()];
        int i = 0;
        for (ProductType productType : set) {
            ids[i] = productType.getId();
            i++;
        }
        return ids;
    }
}

