package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.StandardDao;
import com.jspgou.cms.entity.Standard;
import com.jspgou.cms.manager.StandardMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StandardMngImpl
        implements StandardMng {
    private StandardDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public Standard findById(Long id) {
        Standard entity = this.dao.findById(id);
        return entity;
    }

    public List<Standard> findByTypeId(Long typeId) {
        return this.dao.findByTypeId(typeId);
    }

    public Standard save(Standard bean) {
        this.dao.save(bean);
        return bean;
    }

    public Standard update(Standard bean) {
        Updater updater = new Updater(bean);
        Standard entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Standard deleteById(Long id) {
        Standard bean = this.dao.deleteById(id);
        return bean;
    }

    public Standard[] deleteByIds(Long[] ids) {
        Standard[] beans = new Standard[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Standard[] updatePriority(Long[] ids, Integer[] priority) {
        int len = ids.length;
        Standard[] beans = new Standard[len];
        for (int i = 0; i < len; i++) {
            beans[i] = findById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(StandardDao dao) {
        this.dao = dao;
    }
}

