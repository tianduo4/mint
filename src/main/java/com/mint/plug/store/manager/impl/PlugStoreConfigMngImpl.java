package com.mint.plug.store.manager.impl;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.common.security.encoder.PwdEncoder;
import com.mint.plug.store.dao.PlugStoreConfigDao;
import com.mint.plug.store.entity.PlugStoreConfig;
import com.mint.plug.store.manager.PlugStoreConfigMng;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlugStoreConfigMngImpl
        implements PlugStoreConfigMng {

    @Autowired
    private PlugStoreConfigDao dao;

    @Autowired
    private PwdEncoder pwdEncoder;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public PlugStoreConfig findById(Integer id) {
        return this.dao.findById(id);
    }

    @Transactional(readOnly = true)
    public PlugStoreConfig getDefault() {
        return findById(Integer.valueOf(1));
    }

    public PlugStoreConfig save(PlugStoreConfig bean) {
        this.dao.save(bean);
        return bean;
    }

    public PlugStoreConfig update(PlugStoreConfig bean, String password) {
        Updater updater = new Updater(bean);
        if (StringUtils.isNotBlank(password))
            bean.setPassword(this.pwdEncoder.encodePassword(password));
        else {
            updater.exclude("password");
        }
        bean = this.dao.updateByUpdater(updater);
        return bean;
    }

    public PlugStoreConfig deleteById(Integer id) {
        PlugStoreConfig bean = this.dao.deleteById(id);
        return bean;
    }

    public PlugStoreConfig[] deleteByIds(Integer[] ids) {
        PlugStoreConfig[] beans = new PlugStoreConfig[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }
}

