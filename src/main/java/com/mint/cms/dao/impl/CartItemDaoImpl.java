package com.mint.cms.dao.impl;

import com.mint.cms.dao.CartItemDao;
import com.mint.cms.entity.CartItem;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CartItemDaoImpl extends HibernateBaseDao<CartItem, Long>
        implements CartItemDao {
    public CartItem findById(Long id) {
        CartItem entity = (CartItem) get(id);
        return entity;
    }

    public CartItem deleteById(Long id) {
        CartItem entity = (CartItem) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public List<CartItem> getlist(Long cartId, Long popularityGroupId) {
        Finder f = Finder.create("select bean from CartItem bean");
        f.append(" where bean.cart.id=:cartId and bean.popularityGroup.id=:popularityGroupId");
        f.setParam("cartId", cartId).setParam("popularityGroupId", popularityGroupId);
        f.append(" order by bean.id desc");
        return find(f);
    }

    public int deleteByProductId(Long productId) {
        String hql = " delete CartItem bean where bean.product.id=:productId";
        return getSession().createQuery(hql).setParameter("productId", productId).executeUpdate();
    }

    public int deleteByProductFactionId(Long productFacId) {
        String hql = " delete CartItem bean where bean.productFash.id=:productFash";
        return getSession().createQuery(hql).setParameter("productFash", productFacId).executeUpdate();
    }

    protected Class<CartItem> getEntityClass() {
        return CartItem.class;
    }
}

