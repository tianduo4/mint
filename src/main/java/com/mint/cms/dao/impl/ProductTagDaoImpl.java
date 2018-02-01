package com.mint.cms.dao.impl;

import com.mint.cms.dao.ProductTagDao;
import com.mint.cms.entity.ProductTag;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ProductTagDaoImpl extends HibernateBaseDao<ProductTag, Long>
        implements ProductTagDao {
    public List<ProductTag> getList(Long webId) {
        String hql = "from ProductTag bean where bean.website.id=?";
        return find(hql, new Object[]{webId});
    }

    public ProductTag findById(Long id) {
        ProductTag entity = (ProductTag) get(id);
        return entity;
    }

    public ProductTag save(ProductTag bean) {
        getSession().save(bean);
        return bean;
    }

    public ProductTag deleteById(Long id) {
        ProductTag entity = (ProductTag) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ProductTag> getEntityClass() {
        return ProductTag.class;
    }
}

