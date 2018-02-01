package com.mint.cms.dao.impl;

import com.mint.cms.dao.ExendedDao;
import com.mint.cms.entity.Exended;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ExendedDaoImpl extends HibernateBaseDao<Exended, Long>
        implements ExendedDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Finder f = Finder.create("from Exended bean where 1=1");
        f.append(" order by bean.priority");
        return find(f, pageNo, pageSize);
    }

    public Exended findById(Long id) {
        Exended entity = (Exended) get(id);
        return entity;
    }

    public List<Exended> getList() {
        Finder f = Finder.create("from Exended bean where 1=1");
        f.append(" order by bean.priority");
        return find(f);
    }

    public List<Exended> getList(Long typeId) {
        Finder f = Finder.create("select bean from Exended bean");
        if (typeId != null) {
            f.append(" inner join bean.productTypes ptype ");
            f.append(" where ptype.id=:typeId").setParam("typeId", typeId);
        }
        f.append(" order by bean.priority");
        return find(f);
    }

    public Exended save(Exended bean) {
        getSession().save(bean);
        return bean;
    }

    public Exended deleteById(Long id) {
        Exended entity = (Exended) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Exended> getEntityClass() {
        return Exended.class;
    }
}

