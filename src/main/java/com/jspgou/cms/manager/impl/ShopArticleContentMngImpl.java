package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShopArticleContentDao;
import com.jspgou.cms.entity.ShopArticle;
import com.jspgou.cms.entity.ShopArticleContent;
import com.jspgou.cms.manager.ShopArticleContentMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopArticleContentMngImpl
        implements ShopArticleContentMng {
    private ShopArticleContentDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ShopArticleContent findById(Long id) {
        ShopArticleContent entity = this.dao.findById(id);
        return entity;
    }

    public ShopArticleContent save(String content, ShopArticle article) {
        ShopArticleContent bean = new ShopArticleContent();
        bean.setContent(content);
        bean.setArticle(article);
        this.dao.save(bean);
        article.setArticleContent(bean);
        return bean;
    }

    public ShopArticleContent update(ShopArticleContent bean) {
        Updater updater = new Updater(
                bean);
        ShopArticleContent entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopArticleContent deleteById(Long id) {
        ShopArticleContent bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopArticleContent[] deleteByIds(Long[] ids) {
        ShopArticleContent[] beans = new ShopArticleContent[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopArticleContentDao dao) {
        this.dao = dao;
    }
}

