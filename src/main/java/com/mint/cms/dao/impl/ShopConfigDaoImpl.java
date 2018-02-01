package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShopConfigDao;
import com.mint.cms.entity.ShopConfig;
import com.mint.common.hibernate4.HibernateBaseDao;
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

