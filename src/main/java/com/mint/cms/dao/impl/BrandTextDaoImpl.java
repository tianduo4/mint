package com.mint.cms.dao.impl;

import com.mint.cms.dao.BrandTextDao;
import com.mint.cms.entity.BrandText;
import com.mint.common.hibernate4.HibernateBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class BrandTextDaoImpl extends HibernateBaseDao<BrandText, Long>
        implements BrandTextDao {
    public BrandText save(BrandText bean) {
        getSession().save(bean);
        return bean;
    }

    protected Class<BrandText> getEntityClass() {
        return BrandText.class;
    }
}

