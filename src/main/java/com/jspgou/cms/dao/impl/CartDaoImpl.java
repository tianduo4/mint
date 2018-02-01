package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.CartDao;
import com.jspgou.cms.entity.Cart;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class CartDaoImpl extends HibernateBaseDao<Cart, Long>
        implements CartDao {
    public Cart findById(Long id) {
        Cart entity = (Cart) get(id);
        return entity;
    }

    public Cart saveOrUpdate(Cart bean) {
        getSession().saveOrUpdate(bean);
        return bean;
    }

    public Cart update(Cart bean) {
        getSession().update(bean);
        return bean;
    }

    public Cart deleteById(Long id) {
        Cart entity = (Cart) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Cart> getEntityClass() {
        return Cart.class;
    }
}

