package com.mint.cms.dao.impl;

import com.mint.cms.dao.ShopMoneyDao;
import com.mint.cms.entity.ShopMoney;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ShopMoneyDaoImpl extends HibernateBaseDao<ShopMoney, Long>
        implements ShopMoneyDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public Pagination getPage(Long memberId, Boolean status, Date startTime, Date endTime, Integer pageSize, Integer pageNo) {
        Finder f = Finder.create("select bean from ShopMoney bean where 1=1 ");
        if (memberId != null) {
            f.append(" and bean.member.id=:memberId").setParam("memberId", memberId);
        }
        if (status != null) {
            f.append(" and bean.status=:status").setParam("status", status);
        }
        if (startTime != null) {
            f.append(" and bean.createTime>:startTime");
            f.setParam("startTime", startTime);
        }
        if (endTime != null) {
            f.append(" and bean.createTime<:endTime");
            f.setParam("endTime", endTime);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo.intValue(), pageSize.intValue());
    }

    public ShopMoney findById(Long id) {
        ShopMoney entity = (ShopMoney) get(id);
        return entity;
    }

    public ShopMoney save(ShopMoney bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopMoney deleteById(Long id) {
        ShopMoney entity = (ShopMoney) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopMoney> getEntityClass() {
        return ShopMoney.class;
    }

    public void deleteByMemberId(Long memberId) {
        String sql = "delete from jc_shop_money where member_id=" + memberId;
        getSession().createSQLQuery(sql).executeUpdate();
    }
}

