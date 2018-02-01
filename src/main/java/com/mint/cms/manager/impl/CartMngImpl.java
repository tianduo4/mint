package com.mint.cms.manager.impl;

import com.mint.cms.dao.CartDao;
import com.mint.cms.entity.Cart;
import com.mint.cms.entity.Product;
import com.mint.cms.entity.ProductFashion;
import com.mint.cms.manager.CartMng;
import com.mint.cms.manager.GiftMng;
import com.mint.cms.manager.PopularityGroupMng;
import com.mint.cms.manager.ProductFashionMng;
import com.mint.cms.manager.ProductMng;
import com.mint.core.manager.MemberMng;
import com.mint.core.manager.WebsiteMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartMngImpl
        implements CartMng {

    @Autowired
    private PopularityGroupMng popularityGroupMng;

    @Autowired
    private CartDao dao;

    @Autowired
    private GiftMng giftMng;

    @Autowired
    private MemberMng memberMng;

    @Autowired
    private ProductMng productMng;

    @Autowired
    private WebsiteMng websiteMng;

    @Autowired
    private ProductFashionMng fashionMng;

    @Transactional(readOnly = true)
    public Cart findById(Long id) {
        Cart entity = this.dao.findById(id);
        return entity;
    }

    public Cart collectAddItem(Product product, Long fashId, Long popularityId, int count, boolean isAdd, Long memberId, Long webId) {
        Cart cart = findById(memberId);
        if (cart == null) {
            cart = new Cart();
            cart.setMember(this.memberMng.findById(memberId));
            cart.setWebsite(this.websiteMng.findById(webId));
            cart.init();
        }
        if (fashId != null) {
            ProductFashion productFashion = this.fashionMng.findById(fashId);
            cart.addItem(productFashion.getProductId(), productFashion, this.popularityGroupMng.findById(popularityId), count, isAdd);
        } else {
            cart.addItem(product, null, this.popularityGroupMng.findById(popularityId), count, isAdd);
        }
        cart = this.dao.saveOrUpdate(cart);
        return cart;
    }

    public Cart addGift(Long giftId, int count, boolean isAdd, Long memberId, Long webId) {
        Cart cart = findById(memberId);
        if (cart == null) {
            cart = new Cart();
            cart.setMember(this.memberMng.findById(memberId));
            cart.setWebsite(this.websiteMng.findById(webId));
        }
        cart.addGift(this.giftMng.findById(giftId), count, isAdd);
        this.dao.saveOrUpdate(cart);
        return cart;
    }

    public Cart update(Cart cart) {
        return this.dao.update(cart);
    }

    public Cart deleteById(Long id) {
        Cart bean = this.dao.deleteById(id);
        return bean;
    }
}

