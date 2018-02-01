package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.BrandTextDao;
import com.jspgou.cms.entity.BrandText;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import org.hibernate.Session;
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

