package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShopChannelDao;
import com.jspgou.cms.entity.ShopChannel;
import com.jspgou.cms.entity.ShopChannelContent;
import com.jspgou.cms.manager.ShopChannelContentMng;
import com.jspgou.cms.manager.ShopChannelMng;
import com.jspgou.common.hibernate4.Updater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopChannelMngImpl
        implements ShopChannelMng {
    private ShopChannelContentMng shopChannelContentMng;
    private ShopChannelDao dao;

    public List<ShopChannel> getTopList(Long webId) {
        return this.dao.getTopList(webId, false, null);
    }

    public List<ShopChannel> getChildList(Long wegId, Integer parentId) {
        return this.dao.getChildList(wegId, parentId);
    }

    @Transactional(readOnly = true)
    public List<ShopChannel> getList(Long webId) {
        List list = this.dao.getTopList(webId, false, null);
        List allList = new ArrayList();
        addChildToList(allList, list);
        return allList;
    }

    @Transactional(readOnly = true)
    public List<ShopChannel> getArticleList(Long webId) {
        return this.dao.getList(webId, Integer.valueOf(2));
    }

    @Transactional(readOnly = true)
    public List<ShopChannel> getAloneChannelList(Long webId) {
        return this.dao.getList(webId, Integer.valueOf(1));
    }

    public List<ShopChannel> getList(Long webId, Long idBegin, Long idEnd) {
        return this.dao.getList(webId, Integer.valueOf(2), idBegin, idEnd);
    }

    @Transactional(readOnly = true)
    public List<ShopChannel> getListForParent(Long webId, Integer currId) {
        List allList = getList(webId);
        List childList = this.dao.getListForChild(webId, currId);
        allList.removeAll(childList);
        return allList;
    }

    @Transactional(readOnly = true)
    public List<ShopChannel> getListForParentNoSort(Long webId, Long currId) {
        return this.dao.getListForParent(webId, currId);
    }

    @Transactional(readOnly = true)
    public List<ShopChannel> getTopListForTag(Long webId, Integer count) {
        return this.dao.getTopList(webId, true, count);
    }

    private void addChildToList(List<ShopChannel> to, Collection<ShopChannel> from) {
        for (ShopChannel chnl : from) {
            to.add(chnl);
            Collection child = chnl.getChild();
            if ((child != null) && (child.size() > 0))
                addChildToList(to, child);
        }
    }

    public ShopChannel getByPath(Long webId, String path) {
        return this.dao.getByPath(webId, path);
    }

    @Transactional(readOnly = true)
    public ShopChannel findById(Integer id) {
        ShopChannel entity = this.dao.findById(id);
        return entity;
    }

    public ShopChannel save(ShopChannel bean, Integer parentId, String content) {
        ShopChannel parent = null;
        if (parentId != null) {
            parent = findById(parentId);
            bean.setParent(parent);
        }

        this.dao.save(bean);
        if (content != null) {
            this.shopChannelContentMng.save(content, bean);
        }
        return bean;
    }

    public ShopChannel update(ShopChannel bean, Integer parentId, String content) {
        ShopChannel entity = findById(bean.getId());
        ShopChannelContent c = entity.getChannelContent();
        if (c != null)
            c.setContent(content);
        else {
            this.shopChannelContentMng.save(content, entity);
        }
        if (parentId != null)
            entity.setParent(findById(parentId));
        else {
            entity.setParent(null);
        }
        Updater updater = new Updater(bean);
        entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopChannel deleteById(Integer id) {
        ShopChannel bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopChannel[] deleteByIds(Integer[] ids) {
        ShopChannel[] beans = new ShopChannel[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public ShopChannel[] updatePriority(Integer[] ids, Integer[] priority) {
        ShopChannel[] beans = new ShopChannel[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopChannelDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setShopChannelContentMng(ShopChannelContentMng shopChannelContentMng) {
        this.shopChannelContentMng = shopChannelContentMng;
    }
}

