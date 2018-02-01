package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShippingDao;
import com.jspgou.cms.entity.Shipping;
import com.jspgou.cms.manager.ShippingMng;
import com.jspgou.common.hibernate4.Updater;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShippingMngImpl
        implements ShippingMng {
    private ShippingDao dao;

    @Transactional(readOnly = true)
    public List<Shipping> getList(Long webId, boolean all) {
        return this.dao.getList(webId, all, false);
    }

    @Transactional(readOnly = true)
    public List<Shipping> getListForCart(Long webId, Long countryId, int weight, int count) {
        List list = this.dao.getList(webId, false, true);
        Shipping localShipping;
        for (Iterator localIterator = list.iterator(); localIterator.hasNext(); localShipping = (Shipping) localIterator.next())
            ;
        return list;
    }

    @Transactional(readOnly = true)
    public Shipping findById(Long id) {
        Shipping entity = this.dao.findById(id);
        return entity;
    }

    public Shipping save(Shipping bean) {
        this.dao.save(bean);
        return bean;
    }

    public Shipping update(Shipping bean) {
        Shipping entity = findById(bean.getId());
        Updater updater = new Updater(bean);
        entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Shipping deleteById(Long id) {
        Shipping bean = this.dao.deleteById(id);
        return bean;
    }

    public Shipping[] deleteByIds(Long[] ids) {
        Shipping[] beans = new Shipping[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Shipping[] updatePriority(Long[] ids, Integer[] priority) {
        Shipping[] beans = new Shipping[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShippingDao dao) {
        this.dao = dao;
    }
}

