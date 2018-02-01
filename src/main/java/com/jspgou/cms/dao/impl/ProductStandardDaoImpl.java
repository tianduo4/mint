package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ProductStandardDao;
import com.jspgou.cms.entity.ProductStandard;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ProductStandardDaoImpl extends HibernateBaseDao<ProductStandard, Long>
        implements ProductStandardDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public ProductStandard findById(Long id) {
        ProductStandard entity = (ProductStandard) get(id);
        return entity;
    }

    public List<ProductStandard> findByProductIdAndStandardId(Long productId, Long standardId) {
        Finder f = Finder.create("from ProductStandard bean where 1=1");
        f.append(" and bean.product.id=:productId").setParam("productId", productId);
        f.append(" and bean.standard.id=:standardId").setParam("standardId", standardId);
        return find(f);
    }

    public List<ProductStandard> findByProductId(Long productId) {
        Finder f = Finder.create("from ProductStandard bean where 1=1");
        f.append(" and bean.product.id=:productId").setParam("productId", productId);
        return find(f);
    }

    public ProductStandard save(ProductStandard bean) {
        getSession().save(bean);
        return bean;
    }

    public ProductStandard deleteById(Long id) {
        ProductStandard entity = (ProductStandard) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ProductStandard> getEntityClass() {
        return ProductStandard.class;
    }
}

