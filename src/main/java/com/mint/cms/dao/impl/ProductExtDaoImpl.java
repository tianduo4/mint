package com.mint.cms.dao.impl;

import com.mint.cms.dao.ProductExtDao;
import com.mint.cms.entity.ProductExt;
import com.mint.common.hibernate4.HibernateBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class ProductExtDaoImpl extends HibernateBaseDao<ProductExt, Long>
        implements ProductExtDao {
    public ProductExt findById(Long id) {
        ProductExt entity = (ProductExt) get(id);
        return entity;
    }

    public ProductExt save(ProductExt bean) {
        getSession().save(bean);
        return bean;
    }

    protected Class<ProductExt> getEntityClass() {
        return ProductExt.class;
    }
}

