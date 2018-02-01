package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShopShipmentsDao;
import com.mint.cms.entity.ShopShipments;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ShopShipmentsDaoImpl extends HibernateBaseDao<ShopShipments, Long>
        implements ShopShipmentsDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);

        return page;
    }

    public List<ShopShipments> getList(Boolean isDefault) {
        Finder f = Finder.create("from ShopShipments bean where 1=1");
        if (isDefault != null) {
            f.append(" and bean.isDefault=:isDefault");
            f.setParam("isDefault", isDefault);
        }
        return find(f);
    }

    public ShopShipments findById(Long id) {
        ShopShipments entity = (ShopShipments) get(id);
        return entity;
    }

    public ShopShipments save(ShopShipments bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopShipments deleteById(Long id) {
        ShopShipments entity = (ShopShipments) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopShipments> getEntityClass() {
        return ShopShipments.class;
    }
}

