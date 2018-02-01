package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShipmentsDao;
import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.Shipments;
import com.jspgou.cms.manager.ShipmentsMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShipmentsMngImpl
        implements ShipmentsMng {
    private ShipmentsDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(Boolean isPrint, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(isPrint, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public List<Logistics> getAllList() {
        List list = this.dao.getAllList();
        return list;
    }

    public List<Shipments> getlist(Long orderId) {
        return this.dao.getlist(orderId);
    }

    public void deleteByorderId(Long orderId) {
        List<Shipments> list = getlist(orderId);
        for (Shipments shipments : list) {
            deleteById(shipments.getId());
        }
    }

    @Transactional(readOnly = true)
    public Shipments findById(Long id) {
        Shipments entity = this.dao.findById(id);
        return entity;
    }

    public Shipments save(Shipments bean) {
        this.dao.save(bean);
        return bean;
    }

    public Shipments update(Shipments bean) {
        Updater updater = new Updater(bean);
        Shipments entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Shipments deleteById(Long id) {
        Shipments bean = this.dao.deleteById(id);
        return bean;
    }

    public Shipments[] deleteByIds(Long[] ids) {
        Shipments[] beans = new Shipments[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShipmentsDao dao) {
        this.dao = dao;
    }
}

