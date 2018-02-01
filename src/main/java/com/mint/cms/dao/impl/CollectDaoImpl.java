package com.mint.cms.dao.impl;

import com.mint.cms.dao.CollectDao;
import com.mint.cms.entity.Collect;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CollectDaoImpl extends HibernateBaseDao<Collect, Integer>
        implements CollectDao {
    public Pagination getList(Integer pageSize, Integer pageNo, Long memberId) {
        String hql = "from Collect bean where 1=1 and bean.member.id=:id";
        Finder f = Finder.create(hql).setParam("id", memberId);
        return find(f, pageNo.intValue(), pageSize.intValue());
    }

    public Collect findById(Integer id) {
        Collect entity = (Collect) get(id);
        return entity;
    }

    public List<Collect> findByProductId(Long productId) {
        Finder f = Finder.create("from Collect bean where bean.product.id=:id").setParam("id", productId);
        return find(f);
    }

    public Collect findByProductFashionId(Long id) {
        Iterator list = getSession().createQuery("from Collect bean where bean.fashion.id=:id").setParameter("id", id).iterate();
        if (list.hasNext()) {
            return (Collect) list.next();
        }
        return null;
    }

    public List<Collect> getList(Long memberId, int firstResult, int maxResults) {
        Finder f = Finder.create("from Collect bean");
        f.append(" where bean.user.id=:memberId").setParam("memberId", memberId);
        f.setCacheable(true);
        f.setFirstResult(firstResult);
        f.setMaxResults(maxResults);
        return find(f);
    }

    public List<Collect> getList(Long productId, Long memberId) {
        Finder f = Finder.create("select bean from Collect bean where 1=1");
        f.append(" and bean.user.id=:memberId").setParam("memberId", memberId);
        f.append(" and bean.product.id=:productId").setParam("productId", productId);
        return find(f);
    }

    public void deleteByMemberId(Long memeberId) {
        String sql = "delete from jc_shop_collect  where member_id=" + memeberId;
        getSession().createSQLQuery(sql).executeUpdate();
    }

    public Collect save(Collect bean) {
        getSession().save(bean);
        return bean;
    }

    public Collect deleteById(Integer id) {
        Collect entity = (Collect) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Collect> getEntityClass() {
        return Collect.class;
    }
}

