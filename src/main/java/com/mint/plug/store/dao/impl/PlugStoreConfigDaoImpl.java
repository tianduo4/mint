package com.mint.plug.store.dao.impl;

import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;
import com.mint.plug.store.dao.PlugStoreConfigDao;
import com.mint.plug.store.entity.PlugStoreConfig;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class PlugStoreConfigDaoImpl extends HibernateBaseDao<PlugStoreConfig, Integer>
        implements PlugStoreConfigDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public PlugStoreConfig findById(Integer id) {
        return (PlugStoreConfig) get(id);
    }

    public PlugStoreConfig save(PlugStoreConfig bean) {
        getSession().save(bean);
        return bean;
    }

    public PlugStoreConfig deleteById(Integer id) {
        PlugStoreConfig entity = (PlugStoreConfig) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public Class<PlugStoreConfig> getEntityClass() {
        return PlugStoreConfig.class;
    }
}

