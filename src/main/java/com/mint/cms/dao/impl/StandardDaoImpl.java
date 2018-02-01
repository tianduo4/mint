package com.mint.cms.dao.impl;

import com.mint.cms.dao.StandardDao;
import com.mint.cms.entity.Standard;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class StandardDaoImpl extends HibernateBaseDao<Standard, Long>
        implements StandardDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Finder f = Finder.create("from Standard bean where 1=1 ");
        f.append(" order by bean.priority");
        return find(f, pageNo, pageSize);
    }

    public Standard findById(Long id) {
        Standard entity = (Standard) get(id);
        return entity;
    }

    public List<Standard> findByTypeId(Long typeId) {
        String hql = "from Standard bean where 1=1";
        Finder f = Finder.create(hql);
        f.append(" and bean.type.id=:typeId").setParam("typeId", typeId);
        f.append(" order by bean.priority");
        return find(f);
    }

    public Standard save(Standard bean) {
        getSession().save(bean);
        return bean;
    }

    public Standard deleteById(Long id) {
        Standard entity = (Standard) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Standard> getEntityClass() {
        return Standard.class;
    }
}

