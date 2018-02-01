package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ProductExtDao;
import com.jspgou.cms.entity.ProductExt;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import org.hibernate.Session;
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

