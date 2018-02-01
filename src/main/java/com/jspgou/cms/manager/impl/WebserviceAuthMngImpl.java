package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.WebserviceAuthDao;
import com.jspgou.cms.entity.WebserviceAuth;
import com.jspgou.cms.manager.WebserviceAuthMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.security.encoder.PwdEncoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WebserviceAuthMngImpl
        implements WebserviceAuthMng {

    @Autowired
    private PwdEncoder pwdEncoder;
    private WebserviceAuthDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public boolean isPasswordValid(String username, String password) {
        WebserviceAuth auth = findByUsername(username);
        if ((auth != null) && (auth.getEnable())) {
            return this.pwdEncoder.isPasswordValid(auth.getPassword(), password);
        }
        return false;
    }

    @Transactional(readOnly = true)
    public WebserviceAuth findByUsername(String username) {
        WebserviceAuth entity = this.dao.findByUsername(username);
        return entity;
    }

    @Transactional(readOnly = true)
    public WebserviceAuth findById(Integer id) {
        WebserviceAuth entity = this.dao.findById(id);
        return entity;
    }

    public WebserviceAuth save(WebserviceAuth bean) {
        this.dao.save(bean);
        return bean;
    }

    public WebserviceAuth update(WebserviceAuth bean) {
        Updater updater = new Updater(bean);
        WebserviceAuth entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public WebserviceAuth update(Integer id, String username, String password, String system, Boolean enable) {
        WebserviceAuth entity = findById(id);
        if (StringUtils.isNotBlank(username)) {
            entity.setUsername(username);
        }
        if (StringUtils.isNotBlank(password)) {
            entity.setPassword(this.pwdEncoder.encodePassword(password));
        }
        if (StringUtils.isNotBlank(system)) {
            entity.setSystem(system);
        }
        if (enable != null) {
            entity.setEnable(enable.booleanValue());
        }
        return entity;
    }

    public WebserviceAuth deleteById(Integer id) {
        WebserviceAuth bean = this.dao.deleteById(id);
        return bean;
    }

    public WebserviceAuth[] deleteByIds(Integer[] ids) {
        WebserviceAuth[] beans = new WebserviceAuth[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(WebserviceAuthDao dao) {
        this.dao = dao;
    }
}

