package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.WebserviceDao;
import com.jspgou.cms.entity.Webservice;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class WebserviceDaoImpl extends HibernateBaseDao<Webservice, Integer>
        implements WebserviceDao {
    public Pagination getPage(int pageNo, int pageSize) {
        String hql = "from Webservice bean";
        Finder f = Finder.create(hql);
        return find(f, pageNo, pageSize);
    }

    public List<Webservice> getList(String type) {
        String hql = "from Webservice bean where bean.type=:type";
        Finder f = Finder.create(hql).setParam("type", type);
        f.setCacheable(true);
        return find(f);
    }

    public Webservice findById(Integer id) {
        Webservice entity = (Webservice) get(id);
        return entity;
    }

    public Webservice save(Webservice bean) {
        getSession().save(bean);
        return bean;
    }

    public Webservice deleteById(Integer id) {
        Webservice entity = (Webservice) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Webservice> getEntityClass() {
        return Webservice.class;
    }
}

