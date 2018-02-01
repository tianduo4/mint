package com.mint.plug.weixin.manager.impl;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.plug.weixin.dao.WeixinDao;
import com.mint.plug.weixin.entity.Weixin;
import com.mint.plug.weixin.manager.WeixinMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WeixinMngImpl
        implements WeixinMng {

    @Autowired
    private WeixinDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(Integer siteId, int pageNo, int pageSize) {
        return this.dao.getPage(siteId, pageNo, pageSize);
    }

    @Transactional(readOnly = true)
    public Weixin findById(Integer id) {
        return this.dao.findById(id);
    }

    @Transactional(readOnly = true)
    public Weixin findBy() {
        return this.dao.findBy();
    }

    @Transactional(readOnly = true)
    public Weixin find(Long siteId) {
        return this.dao.find(siteId);
    }

    public Weixin save(Weixin bean) {
        return this.dao.save(bean);
    }

    public Weixin update(Weixin bean) {
        Updater updater = new Updater(bean);
        return this.dao.updateByUpdater(updater);
    }

    public Weixin deleteById(Integer id) {
        return this.dao.deleteById(id);
    }

    public Weixin[] delete(Integer[] ids) {
        Weixin[] beans = new Weixin[ids.length];
        for (int i = 0; i < ids.length; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }
}

