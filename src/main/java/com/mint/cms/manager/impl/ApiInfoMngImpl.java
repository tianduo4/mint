package com.mint.cms.manager.impl;

import com.mint.cms.dao.ApiInfoDao;
import com.mint.cms.entity.ApiInfo;
import com.mint.cms.manager.ApiInfoMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApiInfoMngImpl
        implements ApiInfoMng {
    private ApiInfoDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ApiInfo findById(Long id) {
        ApiInfo entity = this.dao.findById(id);
        return entity;
    }

    public ApiInfo findByApiUrl(String apiUrl) {
        return this.dao.findByApiUrl(apiUrl);
    }

    public ApiInfo save(ApiInfo bean) {
        bean.init();
        this.dao.save(bean);
        return bean;
    }

    public ApiInfo update(ApiInfo bean) {
        Updater updater = new Updater(bean);
        ApiInfo entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ApiInfo deleteById(Long id) {
        ApiInfo bean = this.dao.deleteById(id);
        return bean;
    }

    public ApiInfo[] deleteByIds(Long[] ids) {
        ApiInfo[] beans = new ApiInfo[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ApiInfoDao dao) {
        this.dao = dao;
    }
}

