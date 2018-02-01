package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ShopAdminDao;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ShopAdminDaoImpl extends HibernateBaseDao<ShopAdmin, Long>
        implements ShopAdminDao {
    public Pagination getPage(Long webId, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from ShopAdmin bean where bean.website.id=:webId  order by bean.id desc");
        f.setParam("webId", webId);
        return find(f, pageNo, pageSize);
    }

    public ShopAdmin findById(Long id) {
        ShopAdmin entity = (ShopAdmin) get(id);
        return entity;
    }

    public ShopAdmin save(ShopAdmin bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopAdmin deleteById(Long id) {
        ShopAdmin entity = (ShopAdmin) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopAdmin> getEntityClass() {
        return ShopAdmin.class;
    }

    public ShopAdmin getByUsername(String username) {
        String hql = "from ShopAdmin bean where bean.admin.user.username=:username";
        return (ShopAdmin) getSession().createQuery(hql).setParameter("username", username).uniqueResult();
    }

    public ShopAdmin findByName(String name) {
        String sql = "from ShopAdmin bean where bean.admin.user.username=:username";
        return (ShopAdmin) getSession().createQuery(sql).setParameter("username", name).uniqueResult();
    }
}

