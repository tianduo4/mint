package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.PopularityItemDao;
import com.jspgou.cms.entity.Cart;
import com.jspgou.cms.entity.PopularityItem;
import com.jspgou.cms.manager.PopularityGroupMng;
import com.jspgou.cms.manager.PopularityItemMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PopularityItemMngImpl
        implements PopularityItemMng {
    private PopularityItemDao dao;

    @Autowired
    private PopularityGroupMng popularityGroupMng;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    public List<PopularityItem> getlist(Long cartId, Long popularityGroupId) {
        return this.dao.getlist(cartId, popularityGroupId);
    }

    public PopularityItem findById(Long cartId, Long popularityId) {
        return this.dao.findById(cartId, popularityId);
    }

    @Transactional(readOnly = true)
    public PopularityItem findById(Long id) {
        PopularityItem entity = this.dao.findById(id);
        return entity;
    }

    public PopularityItem save(PopularityItem bean) {
        this.dao.save(bean);
        return bean;
    }

    public PopularityItem update(PopularityItem bean) {
        Updater updater = new Updater(bean);
        PopularityItem entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public PopularityItem deleteById(Long id) {
        PopularityItem bean = this.dao.deleteById(id);
        return bean;
    }

    public PopularityItem[] deleteByIds(Long[] ids) {
        PopularityItem[] beans = new PopularityItem[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public void save(Cart cart, Long popularityId) {
        if (popularityId != null) {
            PopularityItem bean = findById(cart.getId(), popularityId);
            if (bean != null) {
                bean.setCount(Integer.valueOf(bean.getCount().intValue() + 1));
                update(bean);
            } else {
                bean = new PopularityItem();
                bean.setCart(cart);
                bean.setPopularityGroup(this.popularityGroupMng.findById(popularityId));
                bean.setCount(Integer.valueOf(1));
                save(bean);
            }
        }
    }

    @Autowired
    public void setDao(PopularityItemDao dao) {
        this.dao = dao;
    }
}

