package com.mint.plug.weixin.manager.impl;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.plug.weixin.dao.WeixinMenuDao;
import com.mint.plug.weixin.entity.WeixinMenu;
import com.mint.plug.weixin.manager.WeixinMenuMng;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WeixinMenuMngImpl
        implements WeixinMenuMng {

    @Autowired
    private WeixinMenuDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(Long siteId, Integer parentId, int pageNo, int pageSize) {
        return this.dao.getPage(siteId, parentId, pageNo, pageSize);
    }

    @Transactional(readOnly = true)
    public List<WeixinMenu> getList(Long siteId, Integer count) {
        return this.dao.getList(siteId, count);
    }

    @Transactional(readOnly = true)
    public WeixinMenu findById(Integer id) {
        return this.dao.findById(id);
    }

    public WeixinMenu save(WeixinMenu bean) {
        return this.dao.save(bean);
    }

    public WeixinMenu update(WeixinMenu bean) {
        Updater updater = new Updater(bean);
        return this.dao.updateByUpdater(updater);
    }

    public WeixinMenu deleteById(Integer id) {
        return this.dao.deleteById(id);
    }

    public WeixinMenu[] deleteByIds(Integer[] ids) {
        WeixinMenu[] beans = new WeixinMenu[ids.length];
        for (int i = 0; i < ids.length; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }
}

