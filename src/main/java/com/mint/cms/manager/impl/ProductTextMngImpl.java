package com.mint.cms.manager.impl;

import com.mint.cms.dao.ProductTextDao;
import com.mint.cms.entity.ProductText;
import com.mint.cms.manager.ProductTextMng;
import com.mint.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductTextMngImpl
        implements ProductTextMng {
    private ProductTextDao dao;

    public ProductText update(ProductText bean) {
        Updater updater = new Updater(bean);
        ProductText entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ProductText save(ProductText bean) {
        return this.dao.save(bean);
    }

    @Autowired
    public void setDao(ProductTextDao dao) {
        this.dao = dao;
    }
}

