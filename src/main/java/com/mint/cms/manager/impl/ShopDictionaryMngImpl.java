package com.mint.cms.manager.impl;

import com.mint.cms.dao.ShopDictionaryDao;
import com.mint.cms.entity.ShopDictionary;
import com.mint.cms.manager.ShopDictionaryMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopDictionaryMngImpl
        implements ShopDictionaryMng {
    private ShopDictionaryDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    public Pagination getPage(String name, Long typeId, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(name, typeId, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ShopDictionary findById(Long id) {
        ShopDictionary entity = this.dao.findById(id);
        return entity;
    }

    public List<ShopDictionary> getListByType(Long typeId) {
        return this.dao.getListByType(typeId);
    }

    public ShopDictionary save(ShopDictionary bean) {
        this.dao.save(bean);
        return bean;
    }

    public ShopDictionary update(ShopDictionary bean) {
        Updater updater = new Updater(bean);
        ShopDictionary entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopDictionary deleteById(Long id) {
        ShopDictionary bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopDictionary[] deleteByIds(Long[] ids) {
        ShopDictionary[] beans = new ShopDictionary[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public ShopDictionary[] updatePriority(Long[] ids, Integer[] priority) {
        int len = ids.length;
        ShopDictionary[] beans = new ShopDictionary[len];
        for (int i = 0; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopDictionaryDao dao) {
        this.dao = dao;
    }
}

