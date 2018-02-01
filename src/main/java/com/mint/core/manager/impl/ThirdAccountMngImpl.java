package com.mint.core.manager.impl;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.core.dao.ThirdAccountDao;
import com.mint.core.entity.ThirdAccount;
import com.mint.core.manager.ThirdAccountMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ThirdAccountMngImpl
        implements ThirdAccountMng {
    private ThirdAccountDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(String username, String source, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(username, source, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ThirdAccount findById(Long id) {
        ThirdAccount entity = this.dao.findById(id);
        return entity;
    }

    @Transactional(readOnly = true)
    public ThirdAccount findByKey(String key) {
        return this.dao.findByKey(key);
    }

    public ThirdAccount save(ThirdAccount bean) {
        this.dao.save(bean);
        return bean;
    }

    public ThirdAccount update(ThirdAccount bean) {
        Updater updater = new Updater(bean);
        bean = this.dao.updateByUpdater(updater);
        return bean;
    }

    public ThirdAccount deleteById(Long id) {
        ThirdAccount bean = this.dao.deleteById(id);
        return bean;
    }

    public ThirdAccount[] deleteByIds(Long[] ids) {
        ThirdAccount[] beans = new ThirdAccount[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ThirdAccountDao dao) {
        this.dao = dao;
    }
}

