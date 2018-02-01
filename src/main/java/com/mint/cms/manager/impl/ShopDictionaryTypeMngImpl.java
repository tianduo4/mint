package com.mint.cms.manager.impl;

import com.mint.cms.dao.ShopDictionaryTypeDao;
import com.mint.cms.entity.ShopDictionaryType;
import com.mint.cms.manager.ShopDictionaryTypeMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopDictionaryTypeMngImpl
        implements ShopDictionaryTypeMng {
    private ShopDictionaryTypeDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ShopDictionaryType findById(Long id) {
        ShopDictionaryType entity = this.dao.findById(id);
        return entity;
    }

    public List<ShopDictionaryType> findAll() {
        return this.dao.findAll();
    }

    public ShopDictionaryType save(ShopDictionaryType bean) {
        this.dao.save(bean);
        return bean;
    }

    public ShopDictionaryType update(ShopDictionaryType bean) {
        Updater updater = new Updater(bean);
        ShopDictionaryType entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopDictionaryType deleteById(Long id) {
        ShopDictionaryType bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopDictionaryType[] deleteByIds(Long[] ids) {
        ShopDictionaryType[] beans = new ShopDictionaryType[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopDictionaryTypeDao dao) {
        this.dao = dao;
    }
}

