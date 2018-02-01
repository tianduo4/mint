package com.jspgou.plug.weixin.dao.impl;

import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import com.jspgou.plug.weixin.dao.WeixinDao;
import com.jspgou.plug.weixin.entity.Weixin;

import java.util.List;

import org.hibernate.Session;

public class WeixinDaoImpl extends HibernateBaseDao<Weixin, Integer>
        implements WeixinDao {
    public Pagination getPage(Integer siteId, int pageNo, int pageSize) {
        Finder f = Finder.create(" from Weixin bean where 1=1");
        f.append(" and bean.site.id=:siteId");
        f.setParam("siteId", siteId);
        return find(f, pageNo, pageSize);
    }

    public Weixin findById(Integer id) {
        return (Weixin) get(id);
    }

    public Weixin findBy() {
        Finder f = Finder.create(" from Weixin bean where 1=1");
        List list = find(f);
        if ((list != null) && (list.size() > 0)) {
            return (Weixin) list.get(0);
        }
        return null;
    }

    public Weixin find(Long siteId) {
        Finder f = Finder.create(" from Weixin bean where 1=1");
        f.append(" and bean.site.id=:siteId").setParam("siteId", siteId);
        List list = find(f);
        if ((list != null) && (list.size() > 0)) {
            return (Weixin) list.get(0);
        }
        return null;
    }

    public Weixin deleteById(Integer id) {
        Weixin entity = (Weixin) get(id);
        if (entity != null) {
            getSession().delete(entity);
            return entity;
        }
        return null;
    }

    public Weixin save(Weixin bean) {
        getSession().save(bean);
        return bean;
    }

    protected Class<Weixin> getEntityClass() {
        return Weixin.class;
    }
}

