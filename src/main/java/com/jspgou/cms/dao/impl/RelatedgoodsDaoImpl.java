package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.RelatedgoodsDao;
import com.jspgou.cms.entity.Relatedgoods;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class RelatedgoodsDaoImpl extends HibernateBaseDao<Relatedgoods, Long>
        implements RelatedgoodsDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public List<Relatedgoods> findByIdProductId(Long productId) {
        Finder f = Finder.create("from Relatedgoods bean where 1=1");
        if (productId != null) {
            f.append(" and bean.productId=:productId");
            f.setParam("productId", productId);
        }
        return find(f);
    }

    public Relatedgoods findById(Long id) {
        Relatedgoods entity = (Relatedgoods) get(id);
        return entity;
    }

    public Relatedgoods save(Relatedgoods bean) {
        getSession().save(bean);
        return bean;
    }

    public Relatedgoods deleteById(Long id) {
        Relatedgoods entity = (Relatedgoods) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Relatedgoods> getEntityClass() {
        return Relatedgoods.class;
    }
}

