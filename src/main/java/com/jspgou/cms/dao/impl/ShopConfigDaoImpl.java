package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ShopConfigDao;
import com.jspgou.cms.entity.ShopConfig;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ShopConfigDaoImpl extends HibernateBaseDao<ShopConfig, Long>
        implements ShopConfigDao {
    public ShopConfig findById(Long id) {
        ShopConfig entity = (ShopConfig) get(id);
        return entity;
    }

    public ShopConfig save(ShopConfig bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopConfig deleteById(Long id) {
        ShopConfig entity = (ShopConfig) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopConfig> getEntityClass() {
        return ShopConfig.class;
    }
}

