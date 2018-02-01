package com.mint.cms.manager.impl;

import com.mint.cms.dao.PaymentPluginsDao;
import com.mint.cms.entity.PaymentPlugins;
import com.mint.cms.manager.PaymentPluginsMng;
import com.mint.common.hibernate4.Updater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentPluginsMngImpl
        implements PaymentPluginsMng {
    private PaymentPluginsDao dao;

    @Transactional(readOnly = true)
    public List<PaymentPlugins> getList() {
        return this.dao.getList();
    }

    public List<PaymentPlugins> getList1(String platform) {
        return this.dao.getList1(platform);
    }

    public PaymentPlugins[] updatePriority(Long[] ids, Integer[] priority) {
        PaymentPlugins[] beans = new PaymentPlugins[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    public PaymentPlugins findByCode(String code) {
        return this.dao.findByCode(code);
    }

    @Transactional(readOnly = true)
    public PaymentPlugins findById(Long id) {
        PaymentPlugins entity = this.dao.findById(id);
        return entity;
    }

    public PaymentPlugins save(PaymentPlugins bean) {
        this.dao.save(bean);
        return bean;
    }

    public PaymentPlugins update(PaymentPlugins bean) {
        Updater updater = new Updater(bean);
        PaymentPlugins entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public PaymentPlugins deleteById(Long id) {
        PaymentPlugins bean = this.dao.deleteById(id);
        return bean;
    }

    public PaymentPlugins[] deleteByIds(Long[] ids) {
        PaymentPlugins[] beans = new PaymentPlugins[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(PaymentPluginsDao dao) {
        this.dao = dao;
    }
}

