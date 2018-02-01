package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.LogisticsDao;
import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.LogisticsText;
import com.jspgou.cms.manager.LogisticsMng;
import com.jspgou.cms.manager.LogisticsTextMng;
import com.jspgou.common.hibernate4.Updater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogisticsMngImpl
        implements LogisticsMng {
    private LogisticsTextMng logisticsTextMng;
    private LogisticsDao dao;

    @Transactional(readOnly = true)
    public List<Logistics> getAllList() {
        List list = this.dao.getAllList();
        return list;
    }

    @Transactional(readOnly = true)
    public Logistics findById(Long id) {
        Logistics entity = this.dao.findById(id);
        return entity;
    }

    public Logistics save(Logistics bean, String text) {
        Logistics entity = this.dao.save(bean);
        this.logisticsTextMng.save(entity, text);
        return entity;
    }

    public Logistics update(Logistics bean, String text) {
        Updater updater = new Updater(bean);
        Logistics entity = this.dao.updateByUpdater(updater);
        entity.getLogisticsText().setText(text);
        return entity;
    }

    public Logistics deleteById(Long id) {
        return this.dao.deleteById(id);
    }

    public Logistics[] deleteByIds(Long[] ids) {
        Logistics[] beans = new Logistics[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Logistics[] updatePriority(Long[] ids, Integer[] priority) {
        Logistics[] beans = new Logistics[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    @Autowired
    public void setLogisticsTextMng(LogisticsTextMng logisticsTextMng) {
        this.logisticsTextMng = logisticsTextMng;
    }

    @Autowired
    public void setDao(LogisticsDao dao) {
        this.dao = dao;
    }
}

