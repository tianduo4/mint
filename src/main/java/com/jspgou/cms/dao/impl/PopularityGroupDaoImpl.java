package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.PopularityGroupDao;
import com.jspgou.cms.entity.PopularityGroup;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class PopularityGroupDaoImpl extends HibernateBaseDao<PopularityGroup, Long>
        implements PopularityGroupDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public PopularityGroup findById(Long id) {
        PopularityGroup entity = (PopularityGroup) get(id);
        return entity;
    }

    public PopularityGroup save(PopularityGroup bean) {
        getSession().save(bean);
        return bean;
    }

    public PopularityGroup deleteById(Long id) {
        PopularityGroup entity = (PopularityGroup) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<PopularityGroup> getEntityClass() {
        return PopularityGroup.class;
    }
}

