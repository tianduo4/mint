package com.mint.cms.manager.impl;

import com.mint.cms.dao.ShopPayDao;
import com.mint.cms.entity.ShopPay;
import com.mint.cms.manager.ShopPayMng;
import com.mint.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopPayMngImpl
        implements ShopPayMng {

    @Autowired
    private ShopPayDao dao;

    public ShopPay deleteById(Integer id) {
        return this.dao.deleteById(id);
    }

    public ShopPay[] deleteByIds(Integer[] ids) {
        ShopPay[] beans = new ShopPay[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public ShopPay findById(Integer id) {
        return this.dao.findById(id);
    }

    public ShopPay save(ShopPay bean) {
        return this.dao.save(bean);
    }

    public ShopPay updateByUpdater(ShopPay bean) {
        Updater updater = new Updater(bean);

        return this.dao.updateByUpdater(updater);
    }
}

