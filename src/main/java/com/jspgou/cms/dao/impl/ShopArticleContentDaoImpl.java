package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ShopArticleContentDao;
import com.jspgou.cms.entity.ShopArticleContent;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ShopArticleContentDaoImpl extends HibernateBaseDao<ShopArticleContent, Long>
        implements ShopArticleContentDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public ShopArticleContent findById(Long id) {
        ShopArticleContent entity = (ShopArticleContent) get(id);
        return entity;
    }

    public ShopArticleContent save(ShopArticleContent bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopArticleContent deleteById(Long id) {
        ShopArticleContent entity = (ShopArticleContent) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopArticleContent> getEntityClass() {
        return ShopArticleContent.class;
    }
}

