package com.mint.cms.dao.impl;

import com.mint.cms.dao.GiftExchangeDao;
import com.mint.cms.entity.GiftExchange;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class GiftExchangeDaoImpl extends HibernateBaseDao<GiftExchange, Long>
        implements GiftExchangeDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public List<GiftExchange> getlist(Long memberId) {
        Finder f = Finder.create("from GiftExchange bean where bean.member.id=:memberId");
        f.setParam("memberId", memberId);
        return find(f);
    }

    public GiftExchange findById(Long id) {
        GiftExchange entity = (GiftExchange) get(id);
        return entity;
    }

    public GiftExchange save(GiftExchange bean) {
        getSession().save(bean);
        return bean;
    }

    public GiftExchange deleteById(Long id) {
        GiftExchange entity = (GiftExchange) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public void deleteByMemberId(Long memberId) {
        String sql = "delete from jc_shop_gift_exchange where member_id=" + memberId;
        getSession().createSQLQuery(sql).executeUpdate();
    }

    protected Class<GiftExchange> getEntityClass() {
        return GiftExchange.class;
    }
}

