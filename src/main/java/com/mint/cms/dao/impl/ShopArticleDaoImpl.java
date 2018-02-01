package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShopArticleDao;
import com.mint.cms.entity.ShopArticle;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ShopArticleDaoImpl extends HibernateBaseDao<ShopArticle, Long>
        implements ShopArticleDao {
    public Pagination getPage(Integer channelId, Long webId, int pageNo, int pageSize) {
        Finder f = Finder.create("select bean from ShopArticle bean ");
        if (channelId != null) {
            f.append(" inner join bean.channel channel,ShopChannel parent");
            f.append(" where channel.lft between parent.lft and parent.rgt");
            f.append(" and parent.id=:parentId");
            f.setParam("parentId", channelId);
            f.append(" and bean.website.id=:webId");
            f.setParam("webId", webId);
        } else {
            f.append(" where bean.website.id=:webId");
            f.setParam("webId", webId);
        }
        f.append(" order by bean.publishTime desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageByChannel(Long channelId, int pageNo, int pageSize, boolean cacheable) {
        Finder f = Finder.create("from ShopArticle bean");
        f.append(" where bean.channel.id=:channelId");
        f.append(" order by bean.publishTime desc");
        f.setParam("channelId", channelId);
        f.setCacheable(cacheable);
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageByWebsite(Long webId, int pageNo, int pageSize, boolean cacheable) {
        Finder f = Finder.create("from ShopArticle bean");
        f.append(" where bean.website.id=:webId");
        f.append(" order by bean.publishTime desc");
        f.setParam("webId", webId);
        f.setCacheable(cacheable);
        return find(f, pageNo, pageSize);
    }

    public List<ShopArticle> getListByChannel(Integer channelId, int firstResult, int maxResults, boolean cacheable) {
        Finder f = Finder.create("from ShopArticle bean");
        f.append(" where bean.channel.id=:channelId");
        f.setParam("channelId", channelId);
        f.setCacheable(cacheable);
        f.setFirstResult(firstResult);
        f.setMaxResults(maxResults);
        return find(f);
    }

    public List<ShopArticle> getListByWebsite(Long webId, int firstResult, int maxResults, boolean cacheable) {
        Finder f = Finder.create("from ShopArticle bean");
        f.append(" where bean.website.id=:webId");
        f.setParam("webId", webId);
        f.setCacheable(cacheable);
        f.setFirstResult(firstResult);
        f.setMaxResults(maxResults);
        return find(f);
    }

    public ShopArticle findById(Long id) {
        ShopArticle entity = (ShopArticle) get(id);
        return entity;
    }

    public ShopArticle save(ShopArticle bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopArticle deleteById(Long id) {
        ShopArticle entity = (ShopArticle) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopArticle> getEntityClass() {
        return ShopArticle.class;
    }
}

