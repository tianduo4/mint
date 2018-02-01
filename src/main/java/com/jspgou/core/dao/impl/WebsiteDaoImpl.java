package com.jspgou.core.dao.impl;

import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import com.jspgou.core.dao.WebsiteDao;
import com.jspgou.core.entity.Website;
import com.jspgou.core.entity.base.BaseWebsite;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class WebsiteDaoImpl extends HibernateBaseDao<Website, Long>
        implements WebsiteDao {
    public Website getUniqueWebsite() {
        String hql = "select bean from Website bean";
        return (Website) getSession().createQuery(hql).uniqueResult();
    }

    public Website findByDomain(String s) {
        return (Website) findUniqueByProperty(BaseWebsite.PROP_DOMAIN, s);
    }

    public int countWebsite() {
        String hql = "select count(*) from Website";
        return ((Number) getSession().createQuery(hql).iterate().next()).intValue();
    }

    public Pagination getAllPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public List<Website> getAllList() {
        Criteria crit = createCriteria(new Criterion[0]);
        List list = crit.list();
        return list;
    }

    public Website findById(Long id) {
        Website entity = (Website) get(id);
        Hibernate.initialize(entity);
        return entity;
    }

    public Website findById1(Long id) {
        Website entity = (Website) get(id);
        return entity;
    }

    public Website save(Website bean) {
        getSession().save(bean);
        return bean;
    }

    public Website deleteById(Long id) {
        Website entity = (Website) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public Website findByDomain(String domain, boolean cacheable) {
        String hql = "from Website bean where domain=:domain";
        Query query = getSession().createQuery(hql).setString("domain", domain);
        query.setCacheable(cacheable);
        return (Website) query.uniqueResult();
    }

    protected Class<Website> getEntityClass() {
        return Website.class;
    }

    public List<Website> getList() {
        String hql = "from Website";
        return find(hql, new Object[0]);
    }

    public List<Website> getList(boolean cacheable) {
        String hql = "from Website bean order by bean.id asc";
        return getSession().createQuery(hql).setCacheable(cacheable).list();
    }
}

