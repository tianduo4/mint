package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.CartItemDao;
import com.jspgou.cms.entity.CartItem;
import com.jspgou.cms.manager.CartItemMng;
import com.jspgou.common.hibernate4.Updater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartItemMngImpl
        implements CartItemMng {

    @Autowired
    private CartItemDao dao;

    @Transactional(readOnly = true)
    public CartItem findById(Long id) {
        CartItem entity = this.dao.findById(id);
        return entity;
    }

    public List<CartItem> getlist(Long cartId, Long popularityGroupId) {
        return this.dao.getlist(cartId, popularityGroupId);
    }

    public CartItem updateByUpdater(CartItem bean) {
        Updater updater = new Updater(bean);
        return this.dao.updateByUpdater(updater);
    }

    public CartItem deleteById(Long id) {
        CartItem bean = this.dao.deleteById(id);
        return bean;
    }

    public CartItem[] deleteByIds(Long[] ids) {
        CartItem[] beans = new CartItem[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public int deleteByProductId(Long productId) {
        return this.dao.deleteByProductId(productId);
    }

    public int deleteByProductFactionId(Long productFacId) {
        return this.dao.deleteByProductFactionId(productFacId);
    }
}

