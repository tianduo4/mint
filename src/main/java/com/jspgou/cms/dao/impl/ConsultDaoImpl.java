package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ConsultDao;
import com.jspgou.cms.entity.Consult;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultDaoImpl extends HibernateBaseDao<Consult, Long>
        implements ConsultDao {
    public Consult findById(Long id) {
        Consult entity = (Consult) get(id);
        return entity;
    }

    public Consult saveOrUpdate(Consult bean) {
        getSession().saveOrUpdate(bean);
        return bean;
    }

    public List<Consult> findByProductId(Long productId) {
        Finder f = Finder.create("from Consult bean where bean.product.id=:id").setParam("id", productId);
        return find(f);
    }

    public Consult getSameConsult(Long memberId) {
        Iterator it = getSession().createQuery("from Consult bean where bean.member.id=:id order by bean.id desc")
                .setParameter("id", memberId).setMaxResults(1).iterate();
        if (it.hasNext()) {
            return (Consult) it.next();
        }
        return null;
    }

    public Pagination getPage(Long productId, String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize, boolean cache) {
        Finder f = Finder.create("from Consult bean where 1=1 ");
        if (productId != null) {
            f.append(" and bean.product.id=:id");
            f.setParam("id", productId);
        }
        if (!StringUtils.isBlank(userName)) {
            f.append(" and bean.member.member.user.username like:userName");
            f.setParam("userName", "%" + userName + "%");
        }
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.product.name like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        if (startTime != null) {
            f.append(" and bean.time>:startTime");
            f.setParam("startTime", startTime);
        }
        if (endTime != null) {
            f.append(" and bean.time<:endTime");
            f.setParam("endTime", endTime);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getVisiblePage(String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize) {
        Finder f = Finder.create("from Consult bean where 1=1 ");
        if (!StringUtils.isBlank(userName)) {
            f.append(" and bean.member.member.user.username like:userName");
            f.setParam("userName", "%" + userName + "%");
        }
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.product.name like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        if (startTime != null) {
            f.append(" and bean.time>:startTime");
            f.setParam("startTime", startTime);
        }
        if (endTime != null) {
            f.append(" and bean.time<:endTime");
            f.setParam("endTime", endTime);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageByMember(Long memberId, int pageNo, int pageSize, boolean cache) {
        Finder f = Finder.create("from Consult bean");
        if (memberId != null) {
            f.append(" where bean.member.id=:id");
            f.setParam("id", memberId);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Consult update(Consult bean) {
        getSession().update(bean);
        return bean;
    }

    public void deleteByMemberId(Long memberId) {
        String sql = "delete from jc_shop_consult where member_id = " + memberId;
        getSession().createSQLQuery(sql).executeUpdate();
    }

    public Consult deleteById(Long id) {
        Consult entity = (Consult) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Consult> getEntityClass() {
        return Consult.class;
    }

    public Long getProductConsult() {
        Finder finder = Finder.create("select count(1) from Consult bean  where bean.adminReplay is null ");
        List list = find(finder);
        return (Long) list.get(0);
    }
}

