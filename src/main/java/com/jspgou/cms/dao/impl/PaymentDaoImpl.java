package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.PaymentDao;
import com.jspgou.cms.entity.Payment;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDaoImpl extends HibernateBaseDao<Payment, Long>
        implements PaymentDao {
    public List<Payment> getListForCart(Long webId, boolean cacheable) {
        String hql = "from Payment bean where bean.website.id=:webId and bean.disabled=false order by bean.priority";
        return getSession().createQuery(hql).setParameter("webId", webId)
                .setCacheable(cacheable).list();
    }

    public List<Payment> getList(Long webId, boolean all) {
        Finder f =
                Finder.create("from Payment bean where bean.website.id=:webId");
        f.setParam("webId", webId);
        if (!all)
            f.append(" bean.disabled=false order by bean.priority");
        else {
            f.append(" order by bean.disabled,bean.priority");
        }
        return find(f);
    }

    public List<Payment> getByCode(String code, Long webId) {
        String hql = "from Payment bean where bean.website.id=? and bean.code=?";
        return find(hql, new Object[]{webId, code});
    }

    public Payment findById(Long id) {
        Payment entity = (Payment) get(id);
        return entity;
    }

    public Payment save(Payment bean) {
        getSession().save(bean);
        return bean;
    }

    public Payment deleteById(Long id) {
        Payment entity = (Payment) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Payment> getEntityClass() {
        return Payment.class;
    }
}

