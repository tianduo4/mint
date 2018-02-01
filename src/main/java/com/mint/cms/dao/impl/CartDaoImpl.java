package com.mint.cms.dao.impl;

import com.mint.cms.dao.CartDao;
import com.mint.cms.entity.Cart;
import com.mint.common.hibernate4.HibernateBaseDao;
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

