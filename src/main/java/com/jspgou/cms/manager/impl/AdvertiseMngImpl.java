package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.AdvertiseDao;
import com.jspgou.cms.entity.Advertise;
import com.jspgou.cms.manager.AdspaceMng;
import com.jspgou.cms.manager.AdvertiseMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdvertiseMngImpl
        implements AdvertiseMng {
    private AdspaceMng adspaceMng;
    private AdvertiseDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(Integer adspaceId, Boolean enabled, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(adspaceId, enabled, pageNo,
                pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public List<Advertise> getList(Integer adspaceId, Boolean enabled) {
        return this.dao.getList(adspaceId, enabled);
    }

    @Transactional(readOnly = true)
    public Advertise findById(Integer id) {
        Advertise entity = this.dao.findById(id);
        return entity;
    }

    public Advertise save(Advertise bean, Integer adspaceId, Map<String, String> attr) {
        bean.setAdspace(this.adspaceMng.findById(adspaceId));
        bean.setAttr(attr);
        bean.init();
        this.dao.save(bean);
        return bean;
    }

    public Advertise update(Advertise bean, Integer adspaceId, Map<String, String> attr) {
        Updater updater = new Updater(bean);
        bean = this.dao.updateByUpdater(updater);
        bean.setAdspace(this.adspaceMng.findById(adspaceId));
        bean.getAttr().clear();
        bean.getAttr().putAll(attr);
        return bean;
    }

    public Advertise deleteById(Integer id) {
        Advertise bean = this.dao.deleteById(id);
        return bean;
    }

    public Advertise[] deleteByIds(Integer[] ids) {
        Advertise[] beans = new Advertise[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public void display(Integer id) {
        Advertise ad = findById(id);
        if (ad != null)
            ad.setDisplayCount(Integer.valueOf(ad.getDisplayCount().intValue() + 1));
    }

    public void click(Integer id) {
        Advertise ad = findById(id);
        if (ad != null)
            ad.setClickCount(Integer.valueOf(ad.getClickCount().intValue() + 1));
    }

    @Autowired
    public void setAdspaceMng(AdspaceMng adspaceMng) {
        this.adspaceMng = adspaceMng;
    }

    @Autowired
    public void setDao(AdvertiseDao dao) {
        this.dao = dao;
    }
}

