package com.mint.cms.manager.impl;

import com.mint.cms.dao.ApiAccountDao;
import com.mint.cms.entity.ApiAccount;
import com.mint.cms.manager.ApiAccountMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApiAccountMngImpl
        implements ApiAccountMng {
    private ApiAccountDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ApiAccount findById(Long id) {
        ApiAccount entity = this.dao.findById(id);
        return entity;
    }

    public ApiAccount save(ApiAccount bean) {
        this.dao.save(bean);
        return bean;
    }

    public ApiAccount update(ApiAccount bean) {
        Updater updater = new Updater(bean);
        ApiAccount entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ApiAccount deleteById(Long id) {
        ApiAccount bean = this.dao.deleteById(id);
        return bean;
    }

    public ApiAccount[] deleteByIds(Long[] ids) {
        ApiAccount[] beans = new ApiAccount[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public ApiAccount findByAppId(String appId) {
        return this.dao.findByAppId(appId);
    }

    @Autowired
    public void setDao(ApiAccountDao dao) {
        this.dao = dao;
    }
}

