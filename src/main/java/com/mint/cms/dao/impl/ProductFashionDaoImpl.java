package com.mint.cms.dao.impl;

import com.mint.cms.dao.ProductFashionDao;
import com.mint.cms.entity.ProductFashion;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ProductFashionDaoImpl extends HibernateBaseDao<ProductFashion, Long>
        implements ProductFashionDao {
    protected Class<ProductFashion> getEntityClass() {
        return ProductFashion.class;
    }

    public ProductFashion deleteById(Long id) {
        ProductFashion entity = (ProductFashion) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public ProductFashion findById(Long id) {
        ProductFashion entity = (ProductFashion) get(id);
        return entity;
    }

    public List<ProductFashion> findByProductId(Long productId) {
        Finder f = Finder.create(" from ProductFashion bean where bean.productId.id=:productId ");
        f.setParam("productId", productId);
        f.setCacheable(true);
        return find(f);
    }

    public Pagination getPage(Long productId, int pageNo, int pageSize) {
        Finder f = Finder.create(" from ProductFashion bean where bean.productId.id=:productId ");
        f.setParam("productId", productId);
        f.setCacheable(true);
        return find(f, pageNo, pageSize);
    }

    public ProductFashion save(ProductFashion bean) {
        getSession().save(bean);
        return bean;
    }

    public Pagination productLack(Integer status, Integer count, int pageNo, int pageSize) {
        Finder f = Finder.create("from ProductFashion bean where bean.lackRemind=:status ");
        f.setParam("status", status);
        if (count == null) {
            count = Integer.valueOf(5);
        }
        f.append(" and bean.stockCount < :count").setParam("count", count);
        return find(f, pageNo, pageSize);
    }

    public Integer productLackCount(Integer status, Integer count) {
        String hql = " select count(bean) from ProductFashion bean where bean.lackRemind=:status ";
        if (count == null) {
            count = Integer.valueOf(5);
        }
        hql = hql + " and bean.stockCount < :count";
        Iterator ite = getSession().createQuery(hql).setInteger("count", count.intValue()).setInteger("status", status.intValue()).iterate();
        Integer result = Integer.valueOf(0);
        if (ite.hasNext()) {
            result = Integer.valueOf(Integer.parseInt((String) ite.next()));
        }
        return result;
    }

    public Pagination getSaleTopPage(int pageNo, int pageSize) {
        Finder f = Finder.create(" from ProductFashion bean order by bean.saleCount desc");
        return find(f, pageNo, pageSize);
    }

    public ProductFashion getPfashion(Long productId, Long fashId) {
        return (ProductFashion) getSession().createQuery("from ProductFashion bean where bean.productId.id=:pid and bean.id=:fid")
                .setParameter("pid", productId).setParameter("fid", fashId).uniqueResult();
    }

    public Boolean getOneFashion(Long productId) {
        Finder f = Finder.create("from ProductFashion bean where bean.productId.id=:id").setParam("id", productId);
        List l = find(f);
        if (l.size() <= 1) {
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }
}

