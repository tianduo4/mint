package com.mint.cms.dao.impl;

import com.mint.cms.dao.GatheringDao;
import com.mint.cms.entity.Gathering;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class GatheringDaoImpl extends HibernateBaseDao<Gathering, Long>
        implements GatheringDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public List<Gathering> getlist(Long orderId) {
        Finder f = Finder.create("from Gathering bean where bean.indent.id=:id");
        f.setParam("id", orderId);
        return find(f);
    }

    public Gathering findById(Long id) {
        Gathering entity = (Gathering) get(id);
        return entity;
    }

    public Gathering save(Gathering bean) {
        getSession().save(bean);
        return bean;
    }

    public Gathering deleteById(Long id) {
        Gathering entity = (Gathering) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Gathering> getEntityClass() {
        return Gathering.class;
    }
}

