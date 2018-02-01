package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShopDictionaryTypeDao;
import com.mint.cms.entity.ShopDictionaryType;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDictionaryTypeDaoImpl extends HibernateBaseDao<ShopDictionaryType, Long>
        implements ShopDictionaryTypeDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public ShopDictionaryType findById(Long id) {
        ShopDictionaryType entity = (ShopDictionaryType) get(id);
        return entity;
    }

    public List<ShopDictionaryType> findAll() {
        Finder f = Finder.create("from ShopDictionaryType bean ");
        return find(f);
    }

    public ShopDictionaryType save(ShopDictionaryType bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopDictionaryType deleteById(Long id) {
        ShopDictionaryType entity = (ShopDictionaryType) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopDictionaryType> getEntityClass() {
        return ShopDictionaryType.class;
    }
}

