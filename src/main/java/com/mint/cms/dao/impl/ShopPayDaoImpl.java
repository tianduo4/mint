package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShopPayDao;
import com.mint.cms.entity.ShopPay;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;
import org.springframework.stereotype.Repository;

@Repository
public class ShopPayDaoImpl extends HibernateBaseDao<ShopPay, Integer>
        implements ShopPayDao {
    public Pagination getPageShopPay(int pageNo, int pageSize) {
        Finder f = Finder.create("from ShopPay bean");
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public ShopPay findById(Integer id) {
        ShopPay entity = (ShopPay) get(id);
        return entity;
    }

    public ShopPay save(ShopPay bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopPay deleteById(Integer id) {
        ShopPay entity = (ShopPay) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopPay> getEntityClass() {
        return ShopPay.class;
    }
}

