package com.mint.cms.dao.impl;

import com.mint.cms.dao.CouponDao;
import com.mint.cms.entity.Coupon;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CouponDaoImpl extends HibernateBaseDao<Coupon, Long>
        implements CouponDao {
    public Pagination getPage(int pageNo, int pageSize, Integer categoryId) {
        Finder f = Finder.create("from Coupon bean");
        if (categoryId != null) {
            f.append(" where bean.category.id = :categoryId ");
            f.setParam("categoryId", categoryId);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageByUsing(Date newTime, int pageNo, int pageSize) {
        Finder f = Finder.create("from Coupon bean where bean.isusing=true");
        if (newTime != null) {
            f.append(" and bean.couponEndTime>:newTime");
            f.setParam("newTime", newTime);
        }
        f.append(" order by bean.id asc");
        return find(f, pageNo, pageSize);
    }

    public List<Coupon> getList() {
        String hql = "from Coupon bean where 1=1";
        return getSession().createQuery(hql).list();
    }

    public Coupon findById(Long id) {
        Coupon entity = (Coupon) get(id);
        return entity;
    }

    public Coupon save(Coupon bean) {
        getSession().save(bean);
        return bean;
    }

    public Coupon deleteById(Long id) {
        Coupon entity = (Coupon) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Coupon> getEntityClass() {
        return Coupon.class;
    }

    public void deleteByMemberId(Long memberId) {
        String sql = "delete from jc_shop_member_coupon where member_id = " + memberId;
        getSession().createSQLQuery(sql).executeUpdate();
    }
}

