package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.StandardTypeDao;
import com.jspgou.cms.entity.StandardType;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class StandardTypeDaoImpl extends HibernateBaseDao<StandardType, Long>
        implements StandardTypeDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Finder f = Finder.create("from StandardType bean where 1=1");
        f.append(" order by bean.priority");
        return find(f, pageNo, pageSize);
    }

    public StandardType getByField(String field) {
        return (StandardType) findUniqueByProperty("field", field);
    }

    public StandardType findById(Long id) {
        StandardType entity = (StandardType) get(id);
        return entity;
    }

    public List<StandardType> getList() {
        Finder f = Finder.create("from StandardType bean where 1=1");
        f.append(" order by bean.priority");
        return find(f);
    }

    public List<StandardType> getList(Integer categoryId) {
        Finder f = Finder.create("select bean from StandardType bean ");
        f.append(" inner join bean.categorys category");
        f.append(" where category.id=:categoryId").setParam("categoryId", categoryId);
        f.append(" order by bean.priority");
        return find(f);
    }

    public StandardType save(StandardType bean) {
        getSession().save(bean);
        return bean;
    }

    public StandardType deleteById(Long id) {
        StandardType entity = (StandardType) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<StandardType> getEntityClass() {
        return StandardType.class;
    }
}

