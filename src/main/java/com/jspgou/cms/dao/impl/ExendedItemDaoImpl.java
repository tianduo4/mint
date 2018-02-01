package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ExendedItemDao;
import com.jspgou.cms.entity.ExendedItem;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ExendedItemDaoImpl extends HibernateBaseDao<ExendedItem, Long>
        implements ExendedItemDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public ExendedItem findById(Long id) {
        ExendedItem entity = (ExendedItem) get(id);
        return entity;
    }

    public ExendedItem save(ExendedItem bean) {
        getSession().save(bean);
        return bean;
    }

    public ExendedItem deleteById(Long id) {
        ExendedItem entity = (ExendedItem) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ExendedItem> getEntityClass() {
        return ExendedItem.class;
    }
}

