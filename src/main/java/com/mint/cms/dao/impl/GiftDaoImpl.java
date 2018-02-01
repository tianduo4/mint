package com.mint.cms.dao.impl;

import com.mint.cms.dao.GiftDao;
import com.mint.cms.entity.Gift;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;
import org.springframework.stereotype.Repository;

@Repository
public class GiftDaoImpl extends HibernateBaseDao<Gift, Long>
        implements GiftDao {
    public Pagination getPageGift(int pageNo, int pageSize) {
        Finder f = Finder.create("from Gift bean");
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Gift findById(Long id) {
        Gift entity = (Gift) get(id);
        return entity;
    }

    public Gift save(Gift bean) {
        getSession().save(bean);
        return bean;
    }

    public Gift deleteById(Long id) {
        Gift entity = (Gift) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Gift> getEntityClass() {
        return Gift.class;
    }
}

