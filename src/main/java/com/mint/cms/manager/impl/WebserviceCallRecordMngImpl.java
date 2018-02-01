package com.mint.cms.manager.impl;

import com.mint.cms.dao.WebserviceCallRecordDao;
import com.mint.cms.entity.WebserviceCallRecord;
import com.mint.cms.manager.WebserviceAuthMng;
import com.mint.cms.manager.WebserviceCallRecordMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WebserviceCallRecordMngImpl
        implements WebserviceCallRecordMng {

    @Autowired
    private WebserviceAuthMng webserviceAuthMng;
    private WebserviceCallRecordDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public WebserviceCallRecord findById(Integer id) {
        WebserviceCallRecord entity = this.dao.findById(id);
        return entity;
    }

    public WebserviceCallRecord save(String clientUsername, String serviceCode) {
        WebserviceCallRecord record = new WebserviceCallRecord();
        record.setAuth(this.webserviceAuthMng.findByUsername(clientUsername));
        record.setRecordTime(Calendar.getInstance().getTime());
        record.setServiceCode(serviceCode);
        return save(record);
    }

    public WebserviceCallRecord save(WebserviceCallRecord bean) {
        this.dao.save(bean);
        return bean;
    }

    public WebserviceCallRecord update(WebserviceCallRecord bean) {
        Updater updater = new Updater(bean);
        WebserviceCallRecord entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public WebserviceCallRecord deleteById(Integer id) {
        WebserviceCallRecord bean = this.dao.deleteById(id);
        return bean;
    }

    public WebserviceCallRecord[] deleteByIds(Integer[] ids) {
        WebserviceCallRecord[] beans = new WebserviceCallRecord[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(WebserviceCallRecordDao dao) {
        this.dao = dao;
    }
}

