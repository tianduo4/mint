package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.PaymentDao;
import com.jspgou.cms.entity.Payment;
import com.jspgou.cms.manager.PaymentMng;
import com.jspgou.cms.manager.ShippingMng;
import com.jspgou.common.hibernate4.Updater;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentMngImpl
        implements PaymentMng {
    private PaymentDao dao;

    @Autowired
    private ShippingMng shippingMng;

    public List<Payment> getListForCart(Long webId) {
        return this.dao.getListForCart(webId, true);
    }

    @Transactional(readOnly = true)
    public List<Payment> getList(Long webId, boolean all) {
        return this.dao.getList(webId, all);
    }

    public Payment[] updatePriority(Long[] ids, Integer[] priority) {
        Payment[] beans = new Payment[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    @Transactional(readOnly = true)
    public List<Payment> getByCode(String code, Long webId) {
        return this.dao.getByCode(code, webId);
    }

    @Transactional(readOnly = true)
    public Payment findById(Long id) {
        Payment entity = this.dao.findById(id);
        return entity;
    }

    public Payment save(Payment bean) {
        this.dao.save(bean);
        return bean;
    }

    public Payment update(Payment bean) {
        Updater updater = new Updater(bean);
        Payment entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Payment deleteById(Long id) {
        Payment bean = this.dao.deleteById(id);
        return bean;
    }

    public Payment[] deleteByIds(Long[] ids) {
        Payment[] beans = new Payment[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public void addShipping(Payment Payment, long[] shippingIds) {
        if (shippingIds != null)
            for (long shippingId : shippingIds)
                Payment.addToShippings(this.shippingMng.findById(Long.valueOf(shippingId)));
    }

    public void updateShipping(Payment Payment, long[] shippingIds) {
        Payment.getShippings().clear();
        if (shippingIds != null)
            for (long shippingId : shippingIds)
                Payment.addToShippings(this.shippingMng.findById(Long.valueOf(shippingId)));
    }

    @Autowired
    public void setDao(PaymentDao dao) {
        this.dao = dao;
    }
}

