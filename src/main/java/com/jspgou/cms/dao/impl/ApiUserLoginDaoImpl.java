package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ApiUserLoginDao;
import com.jspgou.cms.entity.ApiUserLogin;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ApiUserLoginDaoImpl extends HibernateBaseDao<ApiUserLogin, Long>
        implements ApiUserLoginDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public ApiUserLogin findById(Long id) {
        ApiUserLogin entity = (ApiUserLogin) get(id);
        return entity;
    }

    public ApiUserLogin save(ApiUserLogin bean) {
        getSession().save(bean);
        return bean;
    }

    public ApiUserLogin deleteById(Long id) {
        ApiUserLogin entity = (ApiUserLogin) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public ApiUserLogin findBySessionKey(String sessionKey) {
        return (ApiUserLogin) findUniqueByProperty("sessionKey", sessionKey);
    }

    public ApiUserLogin findByUsername(String username) {
        return (ApiUserLogin) findUniqueByProperty("username", username);
    }

    public ApiUserLogin findUserLogin(String username, String sessionKey) {
        String hql = "from ApiUserLogin bean where 1=1";
        Finder f = Finder.create(hql);
        if (StringUtils.isNotBlank(username)) {
            f.append(" and bean.username=:username").setParam("username", username);
        }
        if (StringUtils.isNotBlank(sessionKey)) {
            f.append(" and bean.sessionKey=:sessionKey").setParam("sessionKey", sessionKey);
        }
        List li = find(f);
        if (li.size() > 0) {
            return (ApiUserLogin) li.get(0);
        }
        return null;
    }

    protected Class<ApiUserLogin> getEntityClass() {
        return ApiUserLogin.class;
    }
}

