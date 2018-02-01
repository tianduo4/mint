package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ShopChannelContentDao;
import com.jspgou.cms.entity.ShopChannelContent;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ShopChannelContentDaoImpl extends HibernateBaseDao<ShopChannelContent, Long>
        implements ShopChannelContentDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public ShopChannelContent findById(Long id) {
        ShopChannelContent entity = (ShopChannelContent) get(id);
        return entity;
    }

    public ShopChannelContent save(ShopChannelContent bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopChannelContent deleteById(Long id) {
        ShopChannelContent entity = (ShopChannelContent) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopChannelContent> getEntityClass() {
        return ShopChannelContent.class;
    }
}

