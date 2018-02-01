package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShopDictionaryDao;
import com.mint.cms.entity.ShopDictionary;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDictionaryDaoImpl extends HibernateBaseDao<ShopDictionary, Long>
        implements ShopDictionaryDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public Pagination getPage(String name, Long typeId, int pageNo, int pageSize) {
        Finder f = Finder.create("from ShopDictionary bean where 1=1");
        if (name != null) {
            f.append(" and bean.name like :name");
            f.setParam("name", "%" + name + "%");
        }
        if (typeId != null) {
            f.append(" and bean.shopDictionaryType.id=:typeId");
            f.setParam("typeId", typeId);
        }
        f.append(" order by bean.priority asc, bean.id asc");
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public ShopDictionary findById(Long id) {
        ShopDictionary entity = (ShopDictionary) get(id);
        return entity;
    }

    public List<ShopDictionary> getListByType(Long typeId) {
        Finder f = Finder.create("from ShopDictionary bean where 1=1");
        if (typeId != null) {
            f.append(" and bean.shopDictionaryType.id=:typeId");
            f.setParam("typeId", typeId);
        }
        return find(f);
    }

    public ShopDictionary save(ShopDictionary bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopDictionary deleteById(Long id) {
        ShopDictionary entity = (ShopDictionary) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopDictionary> getEntityClass() {
        return ShopDictionary.class;
    }
}

