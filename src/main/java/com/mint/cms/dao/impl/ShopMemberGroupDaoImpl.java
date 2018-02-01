package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShopMemberGroupDao;
import com.mint.cms.entity.ShopMemberGroup;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ShopMemberGroupDaoImpl extends HibernateBaseDao<ShopMemberGroup, Long>
        implements ShopMemberGroupDao {
    public List<ShopMemberGroup> getList(Long webId, boolean cacheable) {
        String hql = "from ShopMemberGroup bean where bean.website.id=:webId order by bean.score";
        return getSession().createQuery(hql).setParameter("webId", webId)
                .setCacheable(cacheable).list();
    }

    public List<ShopMemberGroup> getList() {
        String hql = "from ShopMemberGroup bean order by bean.id asc";
        return find(hql, new Object[0]);
    }

    public ShopMemberGroup findById(Long id) {
        ShopMemberGroup entity = (ShopMemberGroup) get(id);
        return entity;
    }

    public ShopMemberGroup save(ShopMemberGroup bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopMemberGroup deleteById(Long id) {
        ShopMemberGroup entity = (ShopMemberGroup) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopMemberGroup> getEntityClass() {
        return ShopMemberGroup.class;
    }
}

