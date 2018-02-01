package com.jspgou.core.dao.impl;

import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import com.jspgou.core.dao.LogDao;
import com.jspgou.core.entity.Log;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImpl extends HibernateBaseDao<Log, Long>
        implements LogDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public Log findById(Long id) {
        Log entity = (Log) get(id);
        return entity;
    }

    public Log save(Log bean) {
        getSession().save(bean);
        return bean;
    }

    public Log deleteById(Long id) {
        Log entity = (Log) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Log> getEntityClass() {
        return Log.class;
    }
}

