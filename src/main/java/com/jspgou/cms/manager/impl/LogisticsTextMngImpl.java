package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.LogisticsTextDao;
import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.LogisticsText;
import com.jspgou.cms.manager.LogisticsTextMng;
import com.jspgou.common.hibernate4.Updater;
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

