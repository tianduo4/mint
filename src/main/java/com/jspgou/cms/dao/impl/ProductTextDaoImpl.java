package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ProductTextDao;
import com.jspgou.cms.entity.ProductText;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import org.hibernate.Session;
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

