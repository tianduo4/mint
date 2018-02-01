package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShopMoneyDao;
import com.jspgou.cms.entity.ShopMoney;
import com.jspgou.cms.manager.ShopMoneyMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopMoneyMngImpl
        implements ShopMoneyMng {
    private ShopMoneyDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    public Pagination getPage(Long memberId, Boolean status, Date startTime, Date endTime, Integer pageSize, Integer pageNo) {
        Pagination page = this.dao.getPage(memberId, status,
                startTime, endTime, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ShopMoney findById(Long id) {
        ShopMoney entity = this.dao.findById(id);
        return entity;
    }

    public ShopMoney save(ShopMoney bean) {
        this.dao.save(bean);
        return bean;
    }

    public ShopMoney update(ShopMoney bean) {
        Updater updater = new Updater(bean);
        ShopMoney entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopMoney deleteById(Long id) {
        ShopMoney bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopMoney[] deleteByIds(Long[] ids) {
        ShopMoney[] beans = new ShopMoney[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopMoneyDao dao) {
        this.dao = dao;
    }

    public void deleteByMemberId(Long memberId) {
        this.dao.deleteByMemberId(memberId);
    }
}

