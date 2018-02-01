package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.CartDao;
import com.jspgou.cms.entity.Cart;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.manager.CartMng;
import com.jspgou.cms.manager.GiftMng;
import com.jspgou.cms.manager.PopularityGroupMng;
import com.jspgou.cms.manager.ProductFashionMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.core.manager.MemberMng;
import com.jspgou.core.manager.WebsiteMng;
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

