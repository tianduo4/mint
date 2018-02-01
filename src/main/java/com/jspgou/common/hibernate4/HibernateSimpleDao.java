package com.jspgou.common.hibernate4;

import com.jspgou.common.page.Pagination;
import com.jspgou.common.util.MyBeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


public abstract class HibernateSimpleDao {
    protected Logger log = LoggerFactory.getLogger(getClass());
    protected static final String ORDER_ENTRIES = "orderEntries";
    protected SessionFactory sessionFactory;

    protected List find(String hql, Object[] values) {
        return createQuery(hql, values).list();
    }

    protected Object findUnique(String hql, Object[] values) {
        return createQuery(hql, values).uniqueResult();
    }

    protected Pagination find(Finder finder, int pageNo, int pageSize) {
        int totalCount = countQueryResult(finder);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        p.setList(list);
        return p;
    }

    protected Pagination findBigDataPage(Finder finder, int pageNo, int pageSize) {
        int totalCount = countQueryResult(finder);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        return p;
    }

    protected Pagination findBigData(Finder finder, int pageNo, int pageSize) {
        int totalCount = pageNo * pageSize;
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        p.setList(list);
        return p;
    }

    protected Pagination findByGroup(Finder finder, String selectSql, int pageNo, int pageSize) {
        return findByTotalCount(finder, pageNo, pageSize, countQueryResultByGroup(finder, selectSql));
    }

    protected List find(Finder finder) {
        Query query = finder.createQuery(getSession());
        List list = query.list();
        return list;
    }

    protected Query createQuery(String queryString, Object[] values) {
        Assert.hasText(queryString);
        Query queryObject = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject;
    }

    protected Pagination findByCriteria(Criteria crit, int pageNo, int pageSize) {
        CriteriaImpl impl = (CriteriaImpl) crit;

        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();
        List orderEntries;
        try {
            orderEntries = (List)
                    MyBeanUtils.getFieldValue(impl, "orderEntries");
            MyBeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
        } catch (Exception e) {
            throw new RuntimeException(
                    "cannot read/write 'orderEntries' from CriteriaImpl", e);
        }

        int totalCount = ((Number) crit.setProjection(Projections.rowCount())
                .uniqueResult()).intValue();
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }

        crit.setProjection(projection);
        if (projection == null) {
            crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null)
            crit.setResultTransformer(transformer);
        try {
            MyBeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            throw new RuntimeException(
                    "set 'orderEntries' to CriteriaImpl faild", e);
        }
        crit.setFirstResult(p.getFirstResult());
        crit.setMaxResults(p.getPageSize());
        p.setList(crit.list());
        return p;
    }

    protected int countQueryResult(Finder finder) {
        Query query = getSession().createQuery(finder.getRowCountHql());
        finder.setParamsToQuery(query);
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        return ((Number) query.iterate().next()).intValue();
    }

    protected int countQueryResultByGroup(Finder finder, String selectSql) {
        Query query = getSession().createQuery(finder.getRowCountTotalHql(selectSql));
        setParamsToQuery(finder, query);
        return ((Number) query.iterate().next()).intValue();
    }

    private Pagination findByTotalCount(Finder finder, int pageNo, int pageSize, int totalCount) {
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        p.setList(list);
        return p;
    }

    private Query setParamsToQuery(Finder finder, Query query) {
        finder.setParamsToQuery(query);
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        return query;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionfactory) {
        this.sessionFactory = sessionfactory;
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}

