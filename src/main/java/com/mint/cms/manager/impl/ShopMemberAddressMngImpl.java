package com.mint.cms.manager.impl;

import com.mint.cms.dao.ShopMemberAddressDao;
import com.mint.cms.entity.Cart;
import com.mint.cms.entity.ShopMemberAddress;
import com.mint.cms.manager.CartMng;
import com.mint.cms.manager.ShopMemberAddressMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.common.hibernate4.Updater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopMemberAddressMngImpl
        implements ShopMemberAddressMng {
    private ShopMemberAddressDao dao;
    private ShopMemberMng shopMemberMng;
    private CartMng cartMng;

    @Transactional(readOnly = true)
    public List<ShopMemberAddress> getList(Long memberId) {
        return this.dao.getList(memberId);
    }

    public ShopMemberAddress findByMemberDefault(Long memberId, Boolean isDefault) {
        List list = this.dao.findByMemberDefault(memberId, isDefault);
        if (list.size() >= 1) {
            return (ShopMemberAddress) list.get(0);
        }
        return null;
    }

    public List<ShopMemberAddress> findByMemberId(Long memberId) {
        return this.dao.findByMemberDefault(memberId, null);
    }

    @Transactional(readOnly = true)
    public ShopMemberAddress findById(Long id) {
        ShopMemberAddress entity = this.dao.findById(id);
        return entity;
    }

    public ShopMemberAddress save(ShopMemberAddress bean) {
        return this.dao.save(bean);
    }

    public ShopMemberAddress updateByUpdater(ShopMemberAddress bean) {
        Updater updater = new Updater(bean);
        return this.dao.updateByUpdater(updater);
    }

    public ShopMemberAddress deleteById(Long id, Long memberId) {
        Cart cart = this.cartMng.findById(memberId);

        ShopMemberAddress bean = this.dao.deleteById(id);
        return bean;
    }

    @Autowired
    public void setDao(ShopMemberAddressDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setShopMemberMng(ShopMemberMng shopMemberMng) {
        this.shopMemberMng = shopMemberMng;
    }

    @Autowired
    public void setCartMng(CartMng cartMng) {
        this.cartMng = cartMng;
    }

    public void deleteByMemberId(Long memberId) {
        this.dao.deleteByMemberId(memberId);
    }
}

