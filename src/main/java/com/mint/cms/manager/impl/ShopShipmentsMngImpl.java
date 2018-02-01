package com.mint.cms.manager.impl;

import com.mint.cms.dao.ShopShipmentsDao;
import com.mint.cms.entity.ShopShipments;
import com.mint.cms.manager.ShopShipmentsMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopShipmentsMngImpl
        implements ShopShipmentsMng {
    private ShopShipmentsDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ShopShipments findById(Long id) {
        ShopShipments entity = this.dao.findById(id);
        return entity;
    }

    public List<ShopShipments> getList(Boolean isDefault) {
        return this.dao.getList(isDefault);
    }

    public ShopShipments save(ShopShipments bean) {
        this.dao.save(bean);
        return bean;
    }

    public ShopShipments update(ShopShipments bean) {
        Updater updater = new Updater(bean);
        ShopShipments entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopShipments deleteById(Long id) {
        ShopShipments bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopShipments[] deleteByIds(Long[] ids) {
        ShopShipments[] beans = new ShopShipments[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopShipmentsDao dao) {
        this.dao = dao;
    }
}

