package com.mint.cms.dao.impl;

import com.mint.cms.dao.ProductTextDao;
import com.mint.cms.entity.ProductText;
import com.mint.common.hibernate4.HibernateBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class ProductTextDaoImpl extends HibernateBaseDao<ProductText, Long>
        implements ProductTextDao {
    public ProductText save(ProductText text) {
        getSession().save(text);
        return text;
    }

    protected Class<ProductText> getEntityClass() {
        return ProductText.class;
    }
}

