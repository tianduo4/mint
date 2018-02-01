package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShipmentsDao;
import com.mint.cms.entity.Logistics;
import com.mint.cms.entity.Shipments;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository
public class ShipmentsDaoImpl extends HibernateBaseDao<Shipments, Long>
        implements ShipmentsDao {
    public Pagination getPage(Boolean isPrint, int pageNo, int pageSize) {
        Finder f = Finder.create("from Shipments bean where 1=1");

        if (isPrint != null) {
            f.append(" and bean.isPrint=:isPrint");
            f.setParam("isPrint", isPrint);
        }
        return find(f, pageNo, pageSize);
    }

    public List<Logistics> getAllList() {
        Criteria crit = createCriteria(new Criterion[0]);
        crit.addOrder(Order.asc(Logistics.PROP_PRIORITY));
        List list = crit.list();
        return list;
    }

    public List<Shipments> getlist(Long orderId) {
        Finder f = Finder.create("from Shipments bean where bean.indent.id=:id");
        f.setParam("id", orderId);
        return find(f);
    }

    public Shipments findById(Long id) {
        Shipments entity = (Shipments) get(id);
        return entity;
    }

    public Shipments save(Shipments bean) {
        getSession().save(bean);
        return bean;
    }

    public Shipments deleteById(Long id) {
        Shipments entity = (Shipments) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Shipments> getEntityClass() {
        return Shipments.class;
    }
}

