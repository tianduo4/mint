package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ShopScoreDao;
import com.jspgou.cms.entity.ShopScore;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ShopScoreDaoImpl extends HibernateBaseDao<ShopScore, Long>
        implements ShopScoreDao {
    public Pagination getPage(Long memberId, Boolean status, Boolean useStatus, Date startTime, Date endTime, Integer pageSize, Integer pageNo) {
        Finder f = Finder.create("select bean from ShopScore bean where 1=1 ");
        if (memberId != null) {
            f.append(" and bean.member.id=:memberId").setParam("memberId", memberId);
        }
        if (status != null) {
            f.append(" and bean.status=:status").setParam("status", status);
        }
        if (useStatus != null) {
            f.append(" and bean.useStatus=:useStatus").setParam("useStatus", useStatus);
        }
        if (startTime != null) {
            f.append(" and bean.scoreTime>:startTime");
            f.setParam("startTime", startTime);
        }
        if (endTime != null) {
            f.append(" and bean.scoreTime<:endTime");
            f.setParam("endTime", endTime);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo.intValue(), pageSize.intValue());
    }

    public List<ShopScore> getlist(String code) {
        Finder f = Finder.create("select bean from ShopScore bean where 1=1 ");
        if (!StringUtils.isBlank(code)) {
            f.append(" and bean.code=:code").setParam("code", code);
        }
        return find(f);
    }

    public ShopScore findById(Long id) {
        ShopScore entity = (ShopScore) get(id);
        return entity;
    }

    public ShopScore save(ShopScore bean) {
        getSession().save(bean);
        return bean;
    }

    public ShopScore deleteById(Long id) {
        ShopScore entity = (ShopScore) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ShopScore> getEntityClass() {
        return ShopScore.class;
    }

    public void deleteByMemberId(Long memberId) {
        String sql = "delete from jc_shop_score where member_id = " + memberId;
        getSession().createSQLQuery(sql).executeUpdate();
    }
}

