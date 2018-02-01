package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShopArticleDao;
import com.jspgou.cms.entity.ShopArticle;
import com.jspgou.cms.entity.ShopArticleContent;
import com.jspgou.cms.entity.ShopChannel;
import com.jspgou.cms.manager.ShopArticleContentMng;
import com.jspgou.cms.manager.ShopArticleMng;
import com.jspgou.cms.manager.ShopChannelMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopArticleMngImpl
        implements ShopArticleMng {
    private ShopArticleContentMng shopArticleContentMng;
    private ShopChannelMng shopChannelMng;
    private ShopArticleDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(Integer channelId, Long webId, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(channelId, webId, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public Pagination getPageForTag(Long webId, Long channelId, int pageNo, int pageSize) {
        Pagination page;
        if (channelId != null)
            page = this.dao.getPageByChannel(channelId, pageNo, pageSize, true);
        else {
            page = this.dao.getPageByWebsite(webId, pageNo, pageSize, true);
        }
        return page;
    }

    public List<ShopArticle> getListForTag(Long webId, Integer channelId, int firstResult, int maxResults) {
        List list;
        if (channelId != null)
            list = this.dao.getListByChannel(channelId, firstResult, maxResults,
                    true);
        else {
            list = this.dao.getListByWebsite(webId, firstResult, maxResults, true);
        }
        return list;
    }

    @Transactional(readOnly = true)
    public ShopArticle findById(Long id) {
        ShopArticle entity = this.dao.findById(id);
        return entity;
    }

    public ShopArticle save(ShopArticle bean, Integer channelId, String content) {
        ShopChannel channel = this.shopChannelMng.findById(channelId);
        bean.setChannel(channel);
        bean.init();
        this.dao.save(bean);
        if (content != null) {
            this.shopArticleContentMng.save(content, bean);
        }
        return bean;
    }

    public ShopArticle update(ShopArticle bean, Integer channelId, String content) {
        ShopArticle entity = findById(bean.getId());
        ShopArticleContent c = entity.getArticleContent();
        if (c != null)
            c.setContent(content);
        else {
            this.shopArticleContentMng.save(content, entity);
        }
        if (channelId != null) {
            entity.setChannel(this.shopChannelMng.findById(channelId));
        }
        Updater updater = new Updater(bean);
        entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopArticle deleteById(Long id) {
        ShopArticle bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopArticle[] deleteByIds(Long[] ids) {
        ShopArticle[] beans = new ShopArticle[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopArticleDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setShopChannelMng(ShopChannelMng shopChannelMng) {
        this.shopChannelMng = shopChannelMng;
    }

    @Autowired
    public void setShopArticleContentMng(ShopArticleContentMng shopArticleContentMng) {
        this.shopArticleContentMng = shopArticleContentMng;
    }
}

