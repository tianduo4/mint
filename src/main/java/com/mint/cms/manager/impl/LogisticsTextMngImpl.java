package com.mint.cms.manager.impl;

import com.mint.cms.dao.LogisticsTextDao;
import com.mint.cms.entity.Logistics;
import com.mint.cms.entity.LogisticsText;
import com.mint.cms.manager.LogisticsTextMng;
import com.mint.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogisticsTextMngImpl
        implements LogisticsTextMng {
    private LogisticsTextDao dao;

    public LogisticsText save(Logistics logistics, String text) {
        LogisticsText bean = new LogisticsText();
        bean.setLogistics(logistics);
        bean.setText(text);
        this.dao.save(bean);
        return bean;
    }

    public LogisticsText update(LogisticsText bean) {
        LogisticsText entity = this.dao.updateByUpdater(new Updater(bean));
        return entity;
    }

    @Autowired
    public void setDao(LogisticsTextDao dao) {
        this.dao = dao;
    }
}

