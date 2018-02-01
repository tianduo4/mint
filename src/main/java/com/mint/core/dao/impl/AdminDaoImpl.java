package com.mint.core.dao.impl;

import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.core.dao.AdminDao;
import com.mint.core.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDaoImpl extends HibernateBaseDao<Admin, Long>
        implements AdminDao {
    public Admin getByUsername(String username) {
        String f = "from Admin bean where bean.user.username=:username";
        return (Admin) getSession().createQuery(f).setParameter("username", username).uniqueResult();
    }

    public Admin getByUserId(Long userId, Long webId) {
        String s = "from Admin bean where bean.user.id=:userId and bean.website.id=:webId";
        return (Admin) getSession().createQuery(s).setParameter("userId", userId).setParameter("webId", webId).uniqueResult();
    }

    public Admin findById(Long id) {
        Admin entity = (Admin) get(id);
        return entity;
    }

    public Admin save(Admin bean) {
        getSession().save(bean);
        return bean;
    }

    public Admin deleteById(Long id) {
        Admin entity = (Admin) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Admin> getEntityClass() {
        return Admin.class;
    }
}

