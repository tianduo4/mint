package com.mint.cms.dao.impl;

import com.mint.cms.dao.OrderReturnDao;
import com.mint.cms.entity.OrderReturn;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class OrderReturnDaoImpl extends HibernateBaseDao<OrderReturn, Long>
        implements OrderReturnDao {
    public Pagination getPage(Integer status, int pageNo, int pageSize) {
        Finder f = Finder.create("from OrderReturn bean where 1=1");
        if (status != null) {
            f.append(" and bean.status=:status");
            f.setParam("status", status);
        }
        return find(f, pageNo, pageSize);
    }

    public OrderReturn findById(Long id) {
        OrderReturn entity = (OrderReturn) get(id);
        return entity;
    }

    public List<OrderReturn> findByOrderId(Long orderId) {
        Finder f = Finder.create("from OrderReturn bean where bean.order.id=:orderId");
        f.setParam("orderId", orderId);
        return find(f);
    }

    public OrderReturn save(OrderReturn bean) {
        getSession().save(bean);
        return bean;
    }

    public OrderReturn deleteById(Long id) {
        OrderReturn entity = (OrderReturn) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<OrderReturn> getEntityClass() {
        return OrderReturn.class;
    }
}

