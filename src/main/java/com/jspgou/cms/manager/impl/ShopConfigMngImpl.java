package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShopConfigDao;
import com.jspgou.cms.entity.ShopConfig;
import com.jspgou.cms.manager.ShopConfigMng;
import com.jspgou.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopConfigMngImpl
        implements ShopConfigMng {
    private ShopConfigDao dao;

    @Transactional(readOnly = true)
    public ShopConfig findById(Long id) {
        ShopConfig entity = this.dao.findById(id);
        return entity;
    }

    public ShopConfig save(ShopConfig bean) {
        this.dao.save(bean);
        return bean;
    }

    public ShopConfig update(ShopConfig bean) {
        Updater updater = new Updater(bean);
        ShopConfig entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopConfig deleteById(Long id) {
        ShopConfig bean = this.dao.deleteById(id);
        return bean;
    }

    @Autowired
    public void setDao(ShopConfigDao dao) {
        this.dao = dao;
    }
}

