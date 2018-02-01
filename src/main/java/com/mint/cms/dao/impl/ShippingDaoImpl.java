package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShippingDao;
import com.mint.cms.entity.Shipping;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ShippingDaoImpl extends HibernateBaseDao<Shipping, Long>
        implements ShippingDao {
    public List<Shipping> getList(Long webId, boolean all, boolean cacheable) {
        Finder f = Finder.create("from Shipping bean where bean.website.id=:webId");
        f.setParam("webId", webId);
        if (!all) {
            f.append(" and bean.disabled=false");
        }
        f.append(" order by bean.priority");
        f.setCacheable(cacheable);
        return find(f);
    }

    public Shipping findById(Long id) {
        Shipping entity = (Shipping) get(id);
        return entity;
    }

    public Shipping save(Shipping bean) {
        getSession().save(bean);
        return bean;
    }

    public Shipping deleteById(Long id) {
        Shipping entity = (Shipping) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Shipping> getEntityClass() {
        return Shipping.class;
    }
}

