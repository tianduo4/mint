package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShopChannelContentDao;
import com.jspgou.cms.entity.ShopChannel;
import com.jspgou.cms.entity.ShopChannelContent;
import com.jspgou.cms.manager.ShopChannelContentMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopChannelContentMngImpl
        implements ShopChannelContentMng {
    private ShopChannelContentDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ShopChannelContent findById(Long id) {
        ShopChannelContent entity = this.dao.findById(id);
        return entity;
    }

    public ShopChannelContent save(String content, ShopChannel channel) {
        ShopChannelContent bean = new ShopChannelContent();
        bean.setContent(content);
        bean.setChannel(channel);
        this.dao.save(bean);
        channel.setChannelContent(bean);
        return bean;
    }

    public ShopChannelContent update(ShopChannelContent bean) {
        Updater updater = new Updater(
                bean);
        ShopChannelContent entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopChannelContent deleteById(Long id) {
        ShopChannelContent bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopChannelContent[] deleteByIds(Long[] ids) {
        ShopChannelContent[] beans = new ShopChannelContent[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopChannelContentDao dao) {
        this.dao = dao;
    }
}

