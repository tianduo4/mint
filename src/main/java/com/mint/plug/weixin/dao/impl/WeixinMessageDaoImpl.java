package com.mint.plug.weixin.dao.impl;

import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;
import com.mint.plug.weixin.dao.WeixinMessageDao;
import com.mint.plug.weixin.entity.WeixinMessage;

import java.util.List;

public class WeixinMessageDaoImpl extends HibernateBaseDao<WeixinMessage, Integer>
        implements WeixinMessageDao {
    public Pagination getPage(Long siteId, int pageNo, int pageSize) {
        Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.welcome=false").setParam("siteId", siteId);
        return find(f, pageNo, pageSize);
    }

    public List<WeixinMessage> getList(Long siteId) {
        Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.welcome=false order by bean.number");
        f.setParam("siteId", siteId);
        return find(f);
    }

    public WeixinMessage getWelcome(Long siteId) {
        Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.welcome=true order by bean.number");
        f.setParam("siteId", siteId);
        List lists = find(f);
        if ((lists != null) && (lists.size() > 0)) {
            return (WeixinMessage) lists.get(0);
        }
        return null;
    }

    public WeixinMessage findByNumber(String number, Long siteId) {
        Finder f = Finder.create(" from WeixinMessage bean where bean.site.id=:siteId and bean.number=:number order by bean.id desc");
        f.setParam("number", number);
        f.setParam("siteId", siteId);
        List lists = find(f);
        if ((lists != null) && (lists.size() > 0)) {
            return (WeixinMessage) lists.get(0);
        }
        return null;
    }

    public WeixinMessage findById(Integer id) {
        return (WeixinMessage) get(id);
    }

    public WeixinMessage save(WeixinMessage bean) {
        getSession().save(bean);
        return bean;
    }

    public WeixinMessage deleteById(Integer id) {
        WeixinMessage entity = (WeixinMessage) get(id);
        if (entity != null) {
            getSession().delete(entity);
            return entity;
        }
        return null;
    }

    protected Class<WeixinMessage> getEntityClass() {
        return WeixinMessage.class;
    }
}

