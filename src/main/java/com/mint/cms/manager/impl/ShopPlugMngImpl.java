package com.mint.cms.manager.impl;

import com.mint.cms.dao.ShopPlugDao;
import com.mint.cms.entity.ShopPlug;
import com.mint.cms.manager.ShopPlugMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopPlugMngImpl
        implements ShopPlugMng {
    private ShopPlugDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    public List<ShopPlug> getList(String author, Boolean used) {
        return this.dao.getList(author, used);
    }

    @Transactional(readOnly = true)
    public ShopPlug findById(Long id) {
        ShopPlug entity = this.dao.findById(id);
        return entity;
    }

    @Transactional(readOnly = true)
    public ShopPlug findByPath(String plugPath) {
        return this.dao.findByPath(plugPath);
    }

    public ShopPlug save(ShopPlug bean) {
        this.dao.save(bean);
        return bean;
    }

    public ShopPlug update(ShopPlug bean) {
        Updater updater = new Updater(bean);
        bean = this.dao.updateByUpdater(updater);
        return bean;
    }

    public ShopPlug deleteById(Long id) {
        ShopPlug bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopPlug[] deleteByIds(Long[] ids) {
        ShopPlug[] beans = new ShopPlug[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopPlugDao dao) {
        this.dao = dao;
    }
}

