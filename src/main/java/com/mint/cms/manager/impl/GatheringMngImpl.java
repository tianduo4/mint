package com.mint.cms.manager.impl;

import com.mint.cms.dao.GatheringDao;
import com.mint.cms.entity.Gathering;
import com.mint.cms.manager.GatheringMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GatheringMngImpl
        implements GatheringMng {
    private GatheringDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public Gathering findById(Long id) {
        Gathering entity = this.dao.findById(id);
        return entity;
    }

    public List<Gathering> getlist(Long orderId) {
        return this.dao.getlist(orderId);
    }

    public void deleteByorderId(Long orderId) {
        List<Gathering> list = getlist(orderId);
        for (Gathering gathering : list)
            deleteById(gathering.getId());
    }

    public Gathering save(Gathering bean) {
        this.dao.save(bean);
        return bean;
    }

    public Gathering update(Gathering bean) {
        Updater updater = new Updater(bean);
        Gathering entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Gathering deleteById(Long id) {
        Gathering bean = this.dao.deleteById(id);
        return bean;
    }

    public Gathering[] deleteByIds(Long[] ids) {
        Gathering[] beans = new Gathering[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(GatheringDao dao) {
        this.dao = dao;
    }
}

