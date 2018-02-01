package com.jspgou.core.dao.impl;

import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.core.dao.RoleDao;
import com.jspgou.core.entity.Role;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends HibernateBaseDao<Role, Integer>
        implements RoleDao {
    public List<Role> getList() {
        String hql = "from Role bean order by bean.priority asc";
        return find(hql, new Object[0]);
    }

    public Role findById(Integer id) {
        Role entity = (Role) get(id);
        return entity;
    }

    public Role save(Role bean) {
        getSession().save(bean);
        return bean;
    }

    public Role deleteById(Integer id) {
        Role entity = (Role) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Role> getEntityClass() {
        return Role.class;
    }
}

