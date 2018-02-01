package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ShopChannelDao;
import com.jspgou.cms.entity.ShopChannel;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ShopChannelDaoImpl extends HibernateBaseDao<ShopChannel, Integer>
        implements ShopChannelDao {
    public List<ShopChannel> getList(Long webId) {
        String hql = "from ShopChannel bean where bean.website.id=?";
        return find(hql, new Object[]{webId});
    }

    public List<ShopChannel> getList(Long webId, Integer type) {
        String hql = "from ShopChannel bean where bean.website.id=? and bean.type=? order by bean.priority";
        return find(hql, new Object[]{webId, type});
    }

    public List<ShopChannel> getList(Long webId, Integer type, Long idBegin, Long idEnd) {
        List channList = getSession().createQuery("from ShopChannel bean where bean.website.id=:webId and bean.type=:type and bean.id >=:idBegin and bean.id <=:idEnd")
                .setParameter("webId", webId).setParameter("type", type)
                .setParameter("idBegin", idBegin).setParameter("idEnd", idEnd).list();
        return channList;
    }

    public List<ShopChannel> getListForParent(Long webId, Long currId) {
        Finder f =
                Finder.create("select node from ShopChannel node,ShopChannel exclude");
        f.append(" where node.lft<exclude.lft and node.rgt>exclude.rgt");
        f.append(" and exclude.id=:currId and node.website.id=:webId");
        f.setParam("webId", webId);
        f.setParam("currId", currId);
        return find(f);
    }

    public List<ShopChannel> getListForChild(Long webId, Integer parentId) {
        Finder f =
                Finder.create("select node from ShopChannel node, ShopChannel parent");
        f.append(" where node.lft>=parent.lft and node.lft<=parent.rgt");
        f.append(" and parent.id=:parentId and node.website.id=:webId");
        f.setParam("webId", webId);
        f.setParam("parentId", parentId);
        return find(f);
    }

    public List<ShopChannel> getTopList(Long webId, boolean cacheable, Integer count) {
        String hql = "from ShopChannel bean where bean.website.id=? and bean.parent.id is null order by bean.priority";

        if (count != null) {
            return getSession().createQuery(hql).setParameter(0, webId).setCacheable(cacheable)
                    .setFirstResult(0).setMaxResults(count.intValue()).list();
        }
        return getSession().createQuery(hql).setParameter(0, webId)
                .setCacheable(cacheable).list();
    }

    public List<ShopChannel> getChildList(Long webId, Integer parentId) {
        Finder f = Finder.create("from ShopChannel bean");
        f.append(" where bean.parent.id=:parentId");
        f.setParam("parentId", parentId);
        f.append(" order by bean.priority asc,bean.id asc");
        return find(f);
    }

    public ShopChannel getByPath(Long webId, String path) {
        String hql = "from ShopChannel bean where bean.website.id=? and bean.path=?";
        return (ShopChannel) findUnique(hql, new Object[]{webId, path});
    }

    public ShopChannel findById(Integer id) {
        ShopChannel entity = (ShopChannel) get(id);
        return entity;
    }

    public ShopChannel save(ShopChannel bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopChannel deleteById(Integer id) {
        ShopChannel entity = (ShopChannel) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopChannel> getEntityClass() {
        return ShopChannel.class;
    }
}

