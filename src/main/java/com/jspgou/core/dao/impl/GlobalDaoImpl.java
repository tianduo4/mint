package com.jspgou.core.dao.impl;

import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.core.dao.GlobalDao;
import com.jspgou.core.entity.Global;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalDaoImpl extends HibernateBaseDao<Global, Long>
        implements GlobalDao {
    public Global findById(Long id) {
        Global global = (Global) get(id);
        return global;
    }

    public Global update(Global bean) {
        getSession().update(bean);
        return bean;
    }

    protected Class<Global> getEntityClass() {
        return Global.class;
    }

    public Global findIdkey() {
        String hql = "from Global bean where 1=1";
        Query query = getSession().createQuery(hql);
        query.setMaxResults(1);
        return (Global) query.uniqueResult();
    }
}

