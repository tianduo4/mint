package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.AdvertiseDao;
import com.jspgou.cms.entity.Advertise;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class AdvertiseDaoImpl extends HibernateBaseDao<Advertise, Integer>
        implements AdvertiseDao {
    public Pagination getPage(Integer adspaceId, Boolean enabled, int pageNo, int pageSize) {
        Finder f = Finder.create("from Advertise bean where 1=1");
        if (adspaceId != null) {
            f.append(" and bean.adspace.id=:adspaceId");
            f.setParam("adspaceId", adspaceId);
        }
        if (enabled != null) {
            f.append(" and bean.enabled=:enabled");
            f.setParam("enabled", enabled);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public List<Advertise> getList(Integer adspaceId, Boolean enabled) {
        Finder f = Finder.create("from Advertise bean where 1=1");
        if (adspaceId != null) {
            f.append(" and bean.adspace.id=:adspaceId");
            f.setParam("adspaceId", adspaceId);
        }
        if (enabled != null) {
            f.append(" and bean.enabled=:enabled");
            f.setParam("enabled", enabled);
        }
        return find(f);
    }

    public Advertise findById(Integer id) {
        Advertise entity = (Advertise) get(id);
        return entity;
    }

    public Advertise save(Advertise bean) {
        getSession().save(bean);
        return bean;
    }

    public Advertise deleteById(Integer id) {
        Advertise entity = (Advertise) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Advertise> getEntityClass() {
        return Advertise.class;
    }
}

