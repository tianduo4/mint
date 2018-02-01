package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.WebserviceAuthDao;
import com.jspgou.cms.entity.WebserviceAuth;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class WebserviceAuthDaoImpl extends HibernateBaseDao<WebserviceAuth, Integer>
        implements WebserviceAuthDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public WebserviceAuth findByUsername(String username) {
        String hql = "from WebserviceAuth bean where bean.username=:username";
        Finder f = Finder.create(hql).setParam("username", username);
        f.setCacheable(true);
        List list = find(f);
        if (list.size() > 0) {
            return (WebserviceAuth) list.get(0);
        }
        return null;
    }

    public WebserviceAuth findById(Integer id) {
        WebserviceAuth entity = (WebserviceAuth) get(id);
        return entity;
    }

    public WebserviceAuth save(WebserviceAuth bean) {
        getSession().save(bean);
        return bean;
    }

    public WebserviceAuth deleteById(Integer id) {
        WebserviceAuth entity = (WebserviceAuth) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<WebserviceAuth> getEntityClass() {
        return WebserviceAuth.class;
    }
}

