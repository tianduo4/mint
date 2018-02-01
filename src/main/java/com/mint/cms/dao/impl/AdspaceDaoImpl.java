package com.mint.cms.dao.impl;

import com.mint.cms.dao.AdspaceDao;
import com.mint.cms.entity.Adspace;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class AdspaceDaoImpl extends HibernateBaseDao<Adspace, Integer>
        implements AdspaceDao {
    public Adspace findById(Integer id) {
        Adspace entity = (Adspace) get(id);
        return entity;
    }

    public List<Adspace> getList() {
        return getSession().createQuery("from Adspace bean ").list();
    }

    public Adspace save(Adspace bean) {
        getSession().save(bean);
        return bean;
    }

    public Adspace deleteById(Integer id) {
        Adspace entity = (Adspace) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Adspace> getEntityClass() {
        return Adspace.class;
    }
}

