package com.mint.plug.weixin.manager.impl;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.plug.weixin.dao.WeixinMessageDao;
import com.mint.plug.weixin.entity.WeixinMessage;
import com.mint.plug.weixin.manager.WeixinMessageMng;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WeixinMessageMngImpl
        implements WeixinMessageMng {

    @Autowired
    private WeixinMessageDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(Long siteId, int pageNo, int pageSize) {
        return this.dao.getPage(siteId, pageNo, pageSize);
    }

    @Transactional(readOnly = true)
    public List<WeixinMessage> getList(Long siteId) {
        return this.dao.getList(siteId);
    }

    @Transactional(readOnly = true)
    public WeixinMessage getWelcome(Long siteId) {
        return this.dao.getWelcome(siteId);
    }

    @Transactional(readOnly = true)
    public WeixinMessage findByNumber(String number, Long siteId) {
        return this.dao.findByNumber(number, siteId);
    }

    @Transactional(readOnly = true)
    public WeixinMessage findById(Integer id) {
        return this.dao.findById(id);
    }

    public WeixinMessage save(WeixinMessage bean) {
        return this.dao.save(bean);
    }

    public WeixinMessage update(WeixinMessage bean) {
        Updater updater = new Updater(bean);
        return this.dao.updateByUpdater(updater);
    }

    public WeixinMessage deleteById(Integer id) {
        return this.dao.deleteById(id);
    }

    public WeixinMessage[] deleteByIds(Integer[] ids) {
        WeixinMessage[] beans = new WeixinMessage[ids.length];
        for (int i = 0; i < ids.length; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }
}

