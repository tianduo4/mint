package com.mint.cms.manager.impl;

import com.mint.cms.dao.BrandTextDao;
import com.mint.cms.entity.Brand;
import com.mint.cms.entity.BrandText;
import com.mint.cms.manager.BrandTextMng;
import com.mint.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BrandTextMngImpl
        implements BrandTextMng {
    private BrandTextDao dao;

    public BrandText save(Brand brand, String text) {
        BrandText bean = new BrandText();
        bean.setBrand(brand);
        bean.setText(text);
        this.dao.save(bean);
        return bean;
    }

    public BrandText update(BrandText bean) {
        BrandText entity = this.dao.updateByUpdater(new Updater(bean));
        return entity;
    }

    @Autowired
    public void setDao(BrandTextDao dao) {
        this.dao = dao;
    }
}

