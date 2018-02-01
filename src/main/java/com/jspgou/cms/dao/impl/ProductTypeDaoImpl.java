package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ProductTypeDao;
import com.jspgou.cms.entity.ProductType;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ProductTypeDaoImpl extends HibernateBaseDao<ProductType, Long>
        implements ProductTypeDao {
    public List<ProductType> getList(Long webId) {
        String hql = "from ProductType bean where bean.website.id=?";
        return find(hql, new Object[]{webId});
    }

    public Pagination getPage(Long webId, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from ProductType bean where bean.website.id=:webId  order by bean.id desc");
        f.setParam("webId", webId);
        return find(f, pageNo, pageSize);
    }

    public ProductType findById(Long id) {
        ProductType entity = (ProductType) get(id);
        return entity;
    }

    public ProductType save(ProductType bean) {
        getSession().save(bean);
        return bean;
    }

    public ProductType deleteById(Long id) {
        ProductType entity = (ProductType) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ProductType> getEntityClass() {
        return ProductType.class;
    }
}

