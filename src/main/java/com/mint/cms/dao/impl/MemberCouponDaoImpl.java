package com.mint.cms.dao.impl;

import com.mint.cms.dao.MemberCouponDao;
import com.mint.cms.entity.MemberCoupon;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MemberCouponDaoImpl extends HibernateBaseDao<MemberCoupon, Long>
        implements MemberCouponDao {
    public MemberCoupon findByCoupon(Long memberId, Long couponId) {
        String hql = "from MemberCoupon bean where bean.member.id=? and bean.coupon.id=?";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, memberId).setParameter(1, couponId);

        query.setMaxResults(1);
        return (MemberCoupon) query.setCacheable(true).uniqueResult();
    }

    public List<MemberCoupon> getList(Long memberId, Date newTime, BigDecimal price) {
        Finder f = Finder.create("select bean from MemberCoupon bean where bean.isuse=false");
        if (memberId != null) {
            f.append(" and bean.member.id=:id");
            f.setParam("id", memberId);
        }
        if (newTime != null) {
            f.append(" and bean.coupon.isusing=true");
            f.append(" and bean.coupon.couponEndTime>:newTime");
            f.append(" and bean.coupon.couponTime<:newTime");
            f.setParam("newTime", newTime);
        }
        if (price != null) {
            f.append(" and bean.coupon.leastPrice<=:price");
            f.setParam("price", price);
        }
        return find(f);
    }

    public List<MemberCoupon> getList(Long memberId) {
        String hql = "from MemberCoupon bean where bean.member.id=:id";
        return getSession().createQuery(hql).setParameter("id", memberId).list();
    }

    public MemberCoupon findById(Long id) {
        MemberCoupon entity = (MemberCoupon) get(id);
        return entity;
    }

    public MemberCoupon update(MemberCoupon bean) {
        getSession().update(bean);
        return bean;
    }

    public MemberCoupon save(MemberCoupon bean) {
        getSession().save(bean);
        return bean;
    }

    public MemberCoupon deleteById(Long id) {
        MemberCoupon entity = (MemberCoupon) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<MemberCoupon> getEntityClass() {
        return MemberCoupon.class;
    }
}

