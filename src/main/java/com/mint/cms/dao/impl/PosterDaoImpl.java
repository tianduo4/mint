package com.mint.cms.dao.impl;

import com.mint.cms.dao.PosterDao;
import com.mint.cms.entity.Poster;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PosterDaoImpl extends HibernateBaseDao<Poster, Integer>
        implements PosterDao {
    public Poster findById(Integer id) {
        Poster entity = (Poster) get(id);
        return entity;
    }

    public Poster saveOrUpdate(Poster bean) {
        getSession().saveOrUpdate(bean);
        return bean;
    }

    public Poster update(Poster bean) {
        getSession().update(bean);
        return bean;
    }

    public List<Poster> getPage() {
        return getSession().createQuery("from Poster bean order by bean.time desc").list();
    }

    public Poster deleteById(Integer id) {
        Poster entity = (Poster) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Poster> getEntityClass() {
        return Poster.class;
    }
}

